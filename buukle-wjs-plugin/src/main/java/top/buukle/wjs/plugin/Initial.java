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
import top.buukle.util.JsonUtil;
import top.buukle.util.StringUtil;
import top.buukle.util.SystemUtil;
import top.buukle.util.log.BaseLogger;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.entity.constants.WorkerJobEnums;
import top.buukle.wjs.entity.vo.WorkerJobQuery;
import top.buukle.wjs.plugin.invoker.WorkerJobInvoker;
import top.buukle.wjs.plugin.quartz.QuartzOperator;
import top.buukle.wjs.plugin.zk.ZkListenerInitial;
import top.buukle.wjs.plugin.zk.ZkOperator;
import top.buukle.wjs.plugin.zk.constants.ZkConstants;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private ZkListenerInitial listenerInitial;
    @Autowired
    CuratorFramework curatorFramework;
    @Autowired
    private WorkerJobInvoker workerJobInvoker;
    @Autowired
    private Environment env;
    @Resource
    private Scheduler scheduler;

    void init()  {

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(() -> {
            // 初始化并订阅节点
            listenerInitial();
            // 查询定时任务
            CommonRequest<WorkerJobQuery> commonRequest = new CommonRequest.Builder().build(env.getProperty("spring.application.name"));
            WorkerJobQuery workerJobQuery =new WorkerJobQuery();
            workerJobQuery.setStatus(WorkerJobEnums.status.PUBLISED.value());
            workerJobQuery.setPageSize(500);
            commonRequest.setBody(workerJobQuery);
            PageResponse response = this.getJobWithoutException(commonRequest);
            LOGGER.info("加载应用定时任务完成,内容 : {}", JsonUtil.toJSONString(response));
            List<WorkerJob> list =  response.getBody() ==null? new ArrayList<>(): (List<WorkerJob>)response.getBody().getList();
            if(CollectionUtils.isEmpty(list)){
                LOGGER.info("查询任务列表为空,将进入重试!");
                response = this.getJobWithoutNull(commonRequest);
            }
            list = (List<WorkerJob>) response.getBody();
            // 遍历创建定时任务,并加上监控
            try {
                this.createJobs(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private PageResponse getJobWithoutNull(CommonRequest<WorkerJobQuery> commonRequest) {
        int[] times= {10_000,15_000,30_000,60_000};
        int count = 0;
        while(true){
            try {
                PageResponse response = workerJobInvoker.getApplicationWorkerJob(commonRequest);
                if(response.getBody() ==null || CollectionUtils.isEmpty(response.getBody().getList())){
                    throw new CommonException(BaseReturnEnum.FAILED,"查询任务列表为空!");
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

    private void listenerInitial() {
        int[] times= {10_000,15_000,30_000,60_000};
        int count = 0;
        while(true){
            try {
                 listenerInitial.init();
                 return;
            }catch (Exception e){
                e.printStackTrace();
                count ++;
                int index =( count <= times.length - 1 ? count : (times.length - 1));
                LOGGER.info("初始化zk监听异常, {} 毫秒后开始第:{}次重试!",times[index],count);
                try {
                    Thread.sleep(times[index]);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
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
                e.printStackTrace();
                count ++;
                int index =( count <= times.length - 1 ? count : (times.length - 1));
                LOGGER.info("加载任务列表异常, {} 毫秒后开始第:{}次重试!",times[index],count);
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
     *
     * 主要流程 :
     *
     *                                                         WorkerJobList -> WorkerJob
     *                                                                               |
     *                                                                               |
     *                                                                       判断是否指定ip组 --------Y--------> 判断本机ip是否在ip组 ------N--------> continue;
     *                                                                               |                               |
     *                                                                               N                               Y
     *                                                                               |                               |
     *                                                                               |                               |
     *                                                                               ---------------------------------
     *                                                                                             |
     *                                                                                             |
     *                                                                                             |
     *                  continue;                            ----------Y(已存在)----初始化节点路径信息,并检查zk是否在该路径存在节点
     *                    |                                  |                                     |
     *                    |                                  |                                     |
     *                    N                                  |                                     N (不存在)
     *                    |                                  |                       (创建失败)     |
     *    判断已有节点 data是否符合本机ip_pid <---Y------ 判断该任务是否为单机执行  <----------N-----尝试创建节点
     *                    |                                  |                                     |
     *                    |                                  |                                     |
     *                    Y(符合本机执行)                     N (集群执行)                           Y(创建成功)
     *                    |                                  |                                     |
     *                    |                                  |                                     |
     *                    |                                  |                                     |
     *                    |                                  |                                     |
     *                    |                                  |                                     |
     *                    |                                  |                                     |
     *                    --------------------------------------------------------------------------
     *                                                       |
     *                                                       |
     *                                                       |
     *                                                 创建或更新任务
     *
     *
     *
     *
     */
    private void createJobs(List<WorkerJob> list) throws Exception {
        String ip = SystemUtil.getIp();
        for (WorkerJob workerJob : list) {
            // 处理单条
            if(!this.handleOne(ip,workerJob)){
                continue;
            }
            // 创建或更新本地任务实例
            if( null == QuartzOperator.getCronTrigger(scheduler,workerJob.getId())){
                QuartzOperator.createJob(workerJob,scheduler);
            }else{
                QuartzOperator.updateJob(workerJob,scheduler);
            }
        }
    }

    /**
     * @description 处理一个任务
     * @param ip
     * @param workerJob
     * @return boolean
     * @Author zhanglei1102
     * @Date 2019/11/29
     */
    private boolean handleOne(String ip, WorkerJob workerJob) throws Exception {
        // ip组已经指定,本机ip不在ip组,直接gg思密达
        if(StringUtil.isNotEmpty(workerJob.getIpGroup()) && !workerJob.getIpGroup().contains(ip)){
            return false;
        }
        // 初始化任务节点路径
        String path =
                // 任务总目录层
                ZkConstants.BUUKLE_WJS_JOB_PARENT_NODE +
                // 应用目录
                StringUtil.BACKSLASH + env.getProperty("spring.application.name") +
                // 任务类型层
                StringUtil.BACKSLASH + workerJob.getExecuteType() +
                // 任务id目录层       -- 此层 data 为该任务的ipPid
                StringUtil.BACKSLASH + workerJob.getId();

        // 查看节点是否存在，否，则尝试创建该节点
        if(!ZkOperator.checkExists(curatorFramework, path)){
            try{
                ZkOperator.createAndInitParentsIfNeededEphemeral(curatorFramework,path,SystemUtil.ipPid().getBytes());
                // 创建成功
                return true;
            }
            // 创建失败,过程中已经被别人创建了
            catch (Exception e){
                return this.handleExist(workerJob,path);
            }
        }
        // 存在
        else{
            return this.handleExist(workerJob,path);
        }
    }

    /**
     * @description 处理已经存在节点的任务
     * @param workerJob
     * @param path
     * @return boolean
     * @Author zhanglei1102
     * @Date 2019/11/29
     */
    private boolean handleExist(WorkerJob workerJob, String path) throws Exception {
        // 单机执行
        if(workerJob.getExecuteType() != null && workerJob.getExecuteType() == 1){
            String data = ZkOperator.getData(curatorFramework, path);
            // 是在本实例执行
            if(StringUtil.isNotEmpty(data) && data.equals(SystemUtil.ipPid())){
                return true;
            }else{
                return false;
            }
        }
        // 分布执行
         else if(workerJob.getExecuteType() != null && workerJob.getExecuteType() == 2){
            return true;
         }
         else{
             LOGGER.info("该任务的执行方式错误, id :{}",workerJob.getId());
            return false;
        }

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.init();
    }
}