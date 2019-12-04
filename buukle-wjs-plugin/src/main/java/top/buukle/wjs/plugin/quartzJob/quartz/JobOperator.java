/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: QuartzOperator
 * Author:   zhanglei1102
 * Date:     2019/9/9 23:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.quartzJob.quartz;

import org.apache.curator.framework.CuratorFramework;
import org.quartz.*;
import top.buukle.util.JsonUtil;
import top.buukle.common.log.BaseLogger;
import top.buukle.util.SpringContextUtil;
import top.buukle.util.StringUtil;
import top.buukle.util.SystemUtil;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.plugin.zk.ZkOperator;
import top.buukle.wjs.plugin.zk.constants.ZkConstants;

/**
 * @description 〈〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public class JobOperator {

    private static final BaseLogger LOGGER = BaseLogger.getLogger(JobOperator.class);

    public static final String JOB_PARAM_KEY = "_param";

    /**
     * @description 创建任务
     * @param workerJob
     * @return void
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    public static void createJob( WorkerJob workerJob){
        LOGGER.info("创建定时任务参数开始, workerJob:{}", JsonUtil.toJSONString(workerJob));
        String ip = SystemUtil.getIp();
        if(!handleLock(SpringContextUtil.getBean(CuratorFramework.class),ip,workerJob)){
            return;
        }
        // 创建或更新本地任务实例
        if( null == JobOperator.getCronTrigger(SpringContextUtil.getBean(Scheduler.class),workerJob.getId())){
            JobDetail jobDetail = JobBuilder.newJob(JobBean.class).withIdentity(workerJob.getId()+"").build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(workerJob.getCronExpression()).withMisfireHandlingInstructionFireAndProceed();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(workerJob.getId()+"").withSchedule(scheduleBuilder).build();
            jobDetail.getJobDataMap().put(JOB_PARAM_KEY, workerJob);
            try {
                SpringContextUtil.getBean(Scheduler.class).scheduleJob(jobDetail,trigger);
                LOGGER.info("创建定时任务成功 !参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("创建定时任务失败 !参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
            }
        }else{
            JobOperator.updateJob(SpringContextUtil.getBean(CuratorFramework.class),workerJob,SpringContextUtil.getBean(Scheduler.class));
        }
    }

    /**
     * @description 更新任务
     * @param curatorFramework
     * @param workerJob
     * @param scheduler
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/29
     */
    public static void updateJob( CuratorFramework curatorFramework, WorkerJob workerJob, Scheduler scheduler) {
        LOGGER.info("更新定时任务开始,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        CronTrigger cronTrigger = getCronTrigger(scheduler, workerJob.getId());
        TriggerKey triggerKey = TriggerKey.triggerKey(workerJob.getId() + "");
        // 集群执行的话,需要创建新的任务
        if(cronTrigger == null){
            if(workerJob.getExecuteType() == null || workerJob.getExecuteType() == 2){
                createJob( workerJob);
            }
        }
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(workerJob.getCronExpression()).withMisfireHandlingInstructionFireAndProceed();
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(workerJob.getId()+"").withSchedule(scheduleBuilder).build();
        try {
            scheduler.rescheduleJob(triggerKey,trigger);
            LOGGER.info("更新定时任务成功,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGER.info("更新定时任务失败,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        }
    }

    /**
     * @description 删除任务
     * @param workerJob
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/3
     */
    public static void deleteJob(WorkerJob workerJob )  {
        try {
            LOGGER.info("删除定时任务开始,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
            SpringContextUtil.getBean(Scheduler.class).deleteJob(new JobKey(workerJob.getId() + ""));
            LOGGER.info("删除定时任务成功,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGER.info("删除定时任务失败,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        }
    }

    /**
     * @description 暂停任务
     * @param workerJob
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/3
     */
    public static void pauseJob(WorkerJob workerJob ) {
        try {
            LOGGER.info("暂停定时任务开始,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
            SpringContextUtil.getBean(Scheduler.class).pauseJob(new JobKey(workerJob.getId() + ""));
            LOGGER.info("暂停定时任务成功,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGER.info("暂停定时任务失败,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        }
    }

    /**
     * @description 恢复任务
     * @param workerJob
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/3
     */
    public static void resumeJob(WorkerJob workerJob ) {
        try {
            LOGGER.info("暂停定时任务开始,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
            SpringContextUtil.getBean(Scheduler.class).resumeJob(new JobKey(workerJob.getId() + ""));
            LOGGER.info("暂停定时任务成功,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGER.info("暂停定时任务失败,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        }
    }


    /**
     * @description 获取表达式触发器
     * @param scheduler
     * @param jobId
     * @return org.quartz.CronTrigger
     * @Author zhanglei1102
     * @Date 2019/11/29
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Integer jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(jobId + ""));
        } catch (SchedulerException e) {
            LOGGER.info("获取定时任务CronTrigger出现异常,jobId:{}",jobId);
            return null;
        }
    }
    /**
     * @description 处理一个任务
     * @param curatorFramework
     * @param ip
     * @param workerJob
     * @return boolean
     * @Author zhanglei1102
     * @Date 2019/11/29
     */
    private static boolean handleLock(CuratorFramework curatorFramework, String ip, WorkerJob workerJob) {
        // 初始化任务锁节点路径
        String lockPath = ZkConstants.BUUKLE_WJS_JOB_LOCK_PARENT_NODE + StringUtil.BACKSLASH + workerJob.getId();
        // ip组未指定 或者 ip组已经指定,本机ip不在ip组
        if(StringUtil.isEmpty(workerJob.getIpGroup()) || workerJob.getIpGroup().contains(ip)){
            // 单机执行
            if(workerJob.getExecuteType() != null && workerJob.getExecuteType() == 1){
                // 直接在zk上抢占资源
                try{
                    LOGGER.info("开始抢占zk节点资源,任务id : {}",workerJob.getId());
                    ZkOperator.createAndInitParentsIfNeededEphemeral(curatorFramework,lockPath, SystemUtil.ipPid().getBytes());
                    LOGGER.info("抢占zk节点资源成功,任务id : {}",workerJob.getId());
                    return true;
                }
                // 抢占失败,过程中已经创建过了
                catch (Exception e){
                    LOGGER.info("抢占zk节点资源失败,任务id : {},原因 :{}",workerJob.getId(),e.getMessage());
                    String data = null;
                    try {
                        data = ZkOperator.getData(curatorFramework, lockPath);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        LOGGER.info("抢占zk节点资源失败后查询节点数据异常,任务id : {},原因 :{}",workerJob.getId(),e.getMessage());
                    }
                    // 再次确认下是否为本实例创建的
                    if(StringUtil.isNotEmpty(data) && data.equals(SystemUtil.ipPid())){
                        LOGGER.info("单机任务zk节点已经存在,id : {},是在本实例执行,将创建或更新任务!",workerJob.getId());
                        return true;
                    }else{
                        LOGGER.info("单机任务zk节点已经存在,id : {},不是在本实例执行,将不在创建或更新任务!",workerJob.getId());
                        return false;
                    }
                }
            }
            // 分布执行
            else if(workerJob.getExecuteType() != null && workerJob.getExecuteType() == 2){
                return true;
            }
            // 执行方式不正确
            else{
                LOGGER.info("该任务的执行方式错误, id :{}",workerJob.getId());
                return false;
            }
        }
        // ip组已经指定,本机ip不在ip组,直接gg思密达
        return false;
    }
}