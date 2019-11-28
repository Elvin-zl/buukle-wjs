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
import org.apache.zookeeper.CreateMode;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import top.buukle.common.call.CommonRequest;
import top.buukle.common.call.PageResponse;
import top.buukle.util.StringUtil;
import top.buukle.util.SystemUtil;
import top.buukle.util.log.BaseLogger;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.plugin.invoker.WorkerJobInvoker;
import top.buukle.wjs.plugin.quartz.QuartzOperator;
import top.buukle.wjs.plugin.zk.ZkListenerInitial;
import top.buukle.wjs.plugin.zk.ZkOperator;
import top.buukle.wjs.plugin.zk.constants.ZkConstants;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @description 〈〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
@Component
public class Initial {

    private final static BaseLogger LOGGER = BaseLogger.getLogger(Initial.class);

    @Autowired
    private ZkListenerInitial listenerInitial;
    @Autowired
    CuratorFramework curatorFramework;
    @Autowired
    private WorkerJobInvoker workerJobInvoker;
    @Autowired
    private Environment env;
    @Resource(name = "buukle.wjs.plugin.scheduler")
    private Scheduler scheduler;

    @PostConstruct
    void init() throws Exception {
        // 初始化并订阅节点
        listenerInitial.init();
        // 查询定时任务
        CommonRequest<WorkerJob> commonRequest = new CommonRequest.Builder().build(env.getProperty("spring.application.name"));
        PageResponse response = workerJobInvoker.getApplicationJob(commonRequest);
        List<WorkerJob> list = (List<WorkerJob>) response.getBody().getList();
        // 获取本机ip
        String ip = SystemUtil.getIp();
        // 遍历创建定时任务,并加上监控
        for (WorkerJob workerJob : list) {
            // ip不在本机,gg
            if(StringUtil.isEmpty(workerJob.getIpGroup()) || !workerJob.getIpGroup().contains(ip)){
                continue;
            }
            // 单机执行,需要在zk上确认资源
            if(workerJob.getExecuteType() == null || workerJob.getExecuteType() == 1){
                String path =
                        // 总目录层
                        ZkConstants.BUUKLE_WJS_APP_PARENT_NODE +
                        // 应用目录
                        StringUtil.BACKSLASH + env.getProperty("spring.application.name") +
                        // 应用ip_pid目录层   -- 此层 data 为该实例的分片索引
                        StringUtil.BACKSLASH + SystemUtil.ipPid() +
                        // 任务id目录层       -- 此层 data 为该任务的ipPid
                        StringUtil.BACKSLASH + workerJob.getId();

                try{
                    // 不存在
                    if(!ZkOperator.checkExists(curatorFramework,path)){
                        ZkOperator.createAndInitParentsIfNeededEphemeral(curatorFramework,path,SystemUtil.ipPid().getBytes());
                    }
                    // 存在
                    else{
                        String data = ZkOperator.getData(curatorFramework, path);
                        // 是在本ip,但是不是在本pid运行,gg
                        if(!StringUtil.isNotEmpty(data) && data.equals(SystemUtil.ipPid())){
                            continue;
                        }
                    }
                }catch (Exception e){
                    LOGGER.info("单机执行,zk确认资源失败,已有应用实例在运行该任务了!");
                    continue;
                }
            }
            if( null == QuartzOperator.getCronTrigger(scheduler,workerJob.getId())){
                QuartzOperator.createJob(workerJob,scheduler);
            }else{
                QuartzOperator.updateJob(workerJob,scheduler);
            }
        }
    }
}