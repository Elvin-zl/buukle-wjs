/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: QuartzOperator
 * Author:   zhanglei1102
 * Date:     2019/9/9 23:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin;

import jdk.management.resource.internal.inst.StaticInstrumentation;
import org.quartz.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import top.buukle.util.JsonUtil;
import top.buukle.util.log.BaseLogger;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.plugin.executor.QuartzJobExecutor;

/**
 * @description 〈〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public class QuartzJobOperator {

    private static final BaseLogger LOGGER = BaseLogger.getLogger(QuartzJobOperator.class);
    public static final String JOB_PARAM_KEY = "_param";

    /**
     * @description 创建本地任务
     * @param workerJob
     * @param scheduler
     * @return void
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    public static void createJob(WorkerJob workerJob, SchedulerFactoryBean scheduler) throws SchedulerException {

        LOGGER.info("创建定时任务参数 workerJob:{}", JsonUtil.toJSONString(workerJob));
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(QuartzJobExecutor.class).withIdentity(workerJob.getId()+"").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(workerJob.getCronExpression()).withMisfireHandlingInstructionFireAndProceed();
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(workerJob.getId()+"").withSchedule(scheduleBuilder).build();
        jobDetail.getJobDataMap().put(JOB_PARAM_KEY, workerJob);
        scheduler.getScheduler().scheduleJob(jobDetail,trigger);
        //暂停任务
//        if(job.getStatus() != null && job.getStatus() == WorkerJobEnums.Status.PAUSE.getValue()){
//            pauseJob(scheduler, job.getJobId());
//        }
    }
}