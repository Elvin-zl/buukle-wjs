/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: ScheduleInit
 * Author:   zhanglei1102
 * Date:     2019/9/9 21:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin;

import org.apache.curator.framework.CuratorFramework;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import top.buukle.common.call.CommonRequest;
import top.buukle.common.call.PageResponse;
import top.buukle.common.call.code.BaseReturnEnum;
import top.buukle.common.exception.CommonException;
import top.buukle.common.message.MessageActivityEnum;
import top.buukle.common.message.MessageDTO;
import top.buukle.common.message.MessageHead;
import top.buukle.util.JsonUtil;
import top.buukle.util.StringUtil;
import top.buukle.common.log.BaseLogger;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.entity.constants.WorkerJobEnums;
import top.buukle.wjs.entity.vo.WorkerJobQuery;
import top.buukle.wjs.plugin.invoker.WorkerJobInvoker;
import top.buukle.wjs.plugin.zk.ZkInitial;
import top.buukle.wjs.plugin.zk.ZkOperator;
import top.buukle.wjs.plugin.zk.constants.ZkConstants;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description 〈〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
@Component
public class Initial implements ApplicationRunner {

    private final static BaseLogger LOGGER = BaseLogger.getLogger(Initial.class);

    @Autowired
    private ZkInitial listenerInitial;
    @Autowired
    CuratorFramework curatorFramework;
    @Autowired
    private WorkerJobInvoker workerJobInvoker;
    @Autowired
    private Environment env;

    @Override
    public void run(ApplicationArguments args){
        this.init();
    }

    void init()  {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(() -> {
            // 初始化并订阅节点异常重试
            listenerInitialWithoutException();
            // 查询定时任务
            CommonRequest<WorkerJobQuery> commonRequest = new CommonRequest.Builder().build(env.getProperty("spring.application.name"));
            WorkerJobQuery workerJobQuery =new WorkerJobQuery();
            workerJobQuery.setStates(WorkerJobEnums.status.PUBLISED.value().toString());
            workerJobQuery.setPageSize(500);
            commonRequest.setBody(workerJobQuery);
            // 异常加载重试
            PageResponse response = this.getJobWithoutException(commonRequest);
            LOGGER.info("加载应用定时任务接口通过!内容 : {}", JsonUtil.toJSONString(response));
            List<LinkedHashMap> list =  response.getBody() ==null? new ArrayList<>(): (List<LinkedHashMap>)response.getBody().getList();
            // 空加载重试
            if(CollectionUtils.isEmpty(list)){
                LOGGER.info("查询任务列表为空,将进入重试!");
                response = this.getJobWithoutNull(commonRequest);
                LOGGER.info("查询任务列表完成!");
            }else{
                LOGGER.info("查询任务列表完成!");
            }
            list = (List<LinkedHashMap>) response.getBody().getList();
            // 遍历创建定时任务节点
            this.createJobsNode(list);
        });
    }

    /**
     * @description 空加载重试
     * @param commonRequest
     * @return top.buukle.common.call.PageResponse
     * @Author zhanglei1102
     * @Date 2019/12/4
     */
    private PageResponse getJobWithoutNull(CommonRequest<WorkerJobQuery> commonRequest) {
        int[] times= {10_000,15_000,30_000,60_000};
        int count = 0;
        while(true){
            try {
                PageResponse response = workerJobInvoker.getApplicationWorkerJob(commonRequest);
                if(response.getBody() ==null || CollectionUtils.isEmpty(response.getBody().getList())){
                    throw new CommonException(BaseReturnEnum.FAILED,"查询任务列表为空!");
                }else{
                    return response;
                }
            }catch (Exception e){
                e.printStackTrace();
                count ++;
                int index =( count <= times.length - 1 ? count : (times.length - 1));
                LOGGER.info("查询任务列表为空或出现异常,具体原因 :{}, {} 毫秒后开始第:{}次重试!",e.getMessage(),times[index],count);
                try {
                    Thread.sleep(times[index]);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    /**
     * @description zk监听重试
     * @param
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/4
     */
    private void listenerInitialWithoutException() {
        int[] times= {10_000,15_000,30_000,60_000};
        int count = 0;
        while(true){
            try {
                 listenerInitial.init();
                 return;
            }catch (Exception e){
                count ++;
                int index =( count <= times.length - 1 ? count : (times.length - 1));
                LOGGER.info("初始化zk监听异常,原因:{}, 第 : {} 毫秒后开始第:{}次重试!",e.getMessage(),times[index],count);
                try {
                    Thread.sleep(times[index]);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    LOGGER.info("初始化zk监听异常休眠时出现异常,原因:{}",e.getMessage());
                }
            }
        }
    }

    private PageResponse getJobWithoutException(CommonRequest<WorkerJobQuery> commonRequest) {
        int[] times= {10_000,15_000,30_000,60_000};
        int count = 0;
        while(true){
            try {
                return workerJobInvoker.getApplicationWorkerJob(commonRequest);
            }catch (Exception e){
                count ++;
                int index =( count <= times.length - 1 ? count : (times.length - 1));
                LOGGER.info("加载任务列表异常,原因:{},将在 {} 毫秒后开始第:{}次重试!",e.getMessage(),times[index],count);
                try {
                    Thread.sleep(times[index]);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    /**
     * @description  遍历创建任务
     * @param list
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/29
     */
    private void createJobsNode(List<LinkedHashMap> list) {
        // 声明任务节点路径
        String path = null;
        for (LinkedHashMap map  : list) {
            WorkerJob workerJob = new WorkerJob();
           try{
               workerJob = JsonUtil.parseObject(JsonUtil.toJSONString(map),WorkerJob.class);
               // 初始化任务节点路径
               path =  // 任务总目录层
                       ZkConstants.BUUKLE_WJS_JOB_PARENT_NODE +
                       // 应用目录
                       StringUtil.BACKSLASH + env.getProperty("spring.application.name") +
                       // 任务类型层
                       StringUtil.BACKSLASH + workerJob.getExecuteType() +
                       // 任务id目录层       -- 此层 data 为该任务的ipPid
                       StringUtil.BACKSLASH + workerJob.getId();
               LOGGER.info("尝试在zk创建任务节点,id : {},path :{}",workerJob.getId(),path);
               MessageHead head = new MessageHead();
               head.setApplicationName(env.getProperty("spring.application.name"));
               head.setOperationTime(new Date());
               MessageDTO messageDTO = new MessageDTO(head, MessageActivityEnum.INIT,workerJob);
               ZkOperator.createAndInitParentsIfNeededEphemeral(curatorFramework,path,JsonUtil.toJSONString(messageDTO).getBytes());
               LOGGER.info("在zk创建任务节点完成,id : {},path :{}",workerJob.getId(),path);
           }catch (Exception e){
               // 此处可用 exist 检查一下 , 因为没有合适的触达插件, 这里按照已创建处理
               LOGGER.info("在zk创建任务节点异常,原因 :{} ,id : {},path :{}",e.getMessage(),workerJob.getId(),path);
           }
        }
    }
}