/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: QuartzJobRunable
 * Author:   zhanglei1102
 * Date:     2019/9/10 0:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.quartzJob.threadpool;

import org.quartz.Scheduler;
import org.springframework.core.env.Environment;
import top.buukle.common.call.CommonRequest;
import top.buukle.util.SpringContextUtil;
import top.buukle.util.StringUtil;
import top.buukle.common.log.BaseLogger;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.entity.constants.WorkerJobEnums;
import top.buukle.wjs.plugin.invoker.WorkerJobInvoker;
import top.buukle.wjs.plugin.quartzJob.quartz.JobOperator;
import top.buukle.wjs.plugin.quartzJob.service.JobExecuteService;
import top.buukle.wjs.plugin.quartzJob.monitor.JobMonitor;


/**
 * @description 〈〉
 * @author zhanglei1102
 * @create 2019/9/10
 * @since 1.0.0
 */
public  class JobRunnable extends WorkerJob implements Runnable{

    private static final BaseLogger LOGGER = BaseLogger.getLogger(JobRunnable.class);

    /** 开始时间*/
    private long startTime;

    public JobRunnable(long startTime) {
        super();
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    @Override
    public void run() {
        WorkerJobInvoker invoker = SpringContextUtil.getBean(WorkerJobInvoker.class);
        Environment env = SpringContextUtil.getBean(Environment.class);
        if(StringUtil.isEmpty(this.getFailStrategy()) || (!this.getFailStrategy().equals(WorkerJobEnums.FailStrategy.QUICK_FAIL.getStrategy().toString()) && !this.getFailStrategy().equals(WorkerJobEnums.FailStrategy.RETRY_CONTINUE.getStrategy().toString()))){
            LOGGER.error("定时任务执行失败!,任务名称 : {},开始执行时间 :{},失败策略异常!" ,this.getBeanReferenceName(),this.getStartTime());
            this.fail(invoker,env,"失败,原因 : 失败策略异常!");
        }
        // 执行任务
        try {
            ((JobExecuteService) SpringContextUtil.getBean(Class.forName(this.getBeanReferenceName()))).execute(this.getParams());
        } catch (Exception e) {
            e.printStackTrace();
            // 快速失败
            if(this.getFailStrategy().equals(WorkerJobEnums.FailStrategy.QUICK_FAIL.getStrategy().toString())){
                LOGGER.error("定时任务执行失败!任务名称 : {},开始执行时间 :{},失败策略 : 快速失败!" ,this.getBeanReferenceName(),this.getStartTime());
                this.fail(invoker,env,"快速失败,原因 : " +e.getMessage());
            }else if(this.getFailStrategy().equals(WorkerJobEnums.FailStrategy.RETRY_CONTINUE.getStrategy().toString())){
                LOGGER.error("定时任务执行失败!任务名称 : {},开始执行时间 :{},失败策略 : 将进入重试!" ,this.getBeanReferenceName(),this.getStartTime());
            }
        }finally {
            // 任务记录的执行次数++
            CommonRequest<WorkerJob> commonRequest = new CommonRequest.Builder().build(env.getProperty("spring.application.name"));
            WorkerJob workerJob = new WorkerJob();
            workerJob.setId(this.getId());
            commonRequest.setBody(workerJob);
            invoker.increaseRetryCountWorkerJob(commonRequest);
        }
    }

    private void fail(WorkerJobInvoker invoker, Environment env,String resultMsg) {
        // 修改为失败
        WorkerJob workerJob = new WorkerJob();
        workerJob.setId(this.getId());
        workerJob.setStatus(WorkerJobEnums.status.FAILED.value());
        workerJob.setResultMsg(resultMsg);
        CommonRequest<WorkerJob> commonRequest = new CommonRequest.Builder().build(env.getProperty("spring.application.name"));
        commonRequest.setBody(workerJob);
        invoker.updateWorkerJob(commonRequest);
        // 停止任务执行粒度 线程
        if(JobMonitor.instance.exsits(this)){
            JobMonitor.instance.get(this).cancel(true);
        }
        // 删除定时任务调度
        JobOperator.deleteJob(this);
    }
}