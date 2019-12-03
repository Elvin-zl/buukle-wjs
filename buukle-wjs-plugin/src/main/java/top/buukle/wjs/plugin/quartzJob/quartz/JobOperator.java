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
import top.buukle.util.log.BaseLogger;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.plugin.zk.ZkOperator;

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
     * @param curatorFramework
     * @param workerJob
     * @param scheduler
     * @return void
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    public static void createJob(CuratorFramework curatorFramework, WorkerJob workerJob, Scheduler scheduler)  {
        LOGGER.info("创建定时任务参数开始, workerJob:{}", JsonUtil.toJSONString(workerJob));
        JobDetail jobDetail = JobBuilder.newJob(JobBean.class).withIdentity(workerJob.getId()+"").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(workerJob.getCronExpression()).withMisfireHandlingInstructionFireAndProceed();
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(workerJob.getId()+"").withSchedule(scheduleBuilder).build();
        jobDetail.getJobDataMap().put(JOB_PARAM_KEY, workerJob);
        try {
            scheduler.scheduleJob(jobDetail,trigger);
            LOGGER.info("创建定时任务成功 !参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("创建定时任务失败 !参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
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
                createJob(curatorFramework, workerJob,scheduler);
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
     * @param scheduler
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/3
     */
    public static void deleteJob(WorkerJob workerJob, Scheduler scheduler)  {
        try {
            LOGGER.info("删除定时任务开始,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
            scheduler.deleteJob(new JobKey(workerJob.getId() + ""));
            LOGGER.info("删除定时任务成功,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGER.info("删除定时任务失败,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        }
    }

    /**
     * @description 暂停任务
     * @param workerJob
     * @param scheduler
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/3
     */
    public static void pauseJob(WorkerJob workerJob, Scheduler scheduler) {
        try {
            LOGGER.info("暂停定时任务开始,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
            scheduler.pauseJob(new JobKey(workerJob.getId() + ""));
            LOGGER.info("暂停定时任务成功,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGER.info("暂停定时任务失败,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        }
    }

    /**
     * @description 恢复任务
     * @param workerJob
     * @param scheduler
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/3
     */
    public static void resumeJob(WorkerJob workerJob, Scheduler scheduler) {
        try {
            LOGGER.info("暂停定时任务开始,参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
            scheduler.resumeJob(new JobKey(workerJob.getId() + ""));
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
}