/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: WjsExecutor
 * Author:   zhanglei1102
 * Date:     2019/9/9 21:32
 * Description: 执行器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.quartz.quartzJobBean;

import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import top.buukle.util.SpringContextUtil;
import top.buukle.util.log.BaseLogger;
import top.buukle.wjs.plugin.quartz.QuartzOperator;
import top.buukle.wjs.plugin.quartz.monitor.JobMonitor;
import top.buukle.wjs.plugin.quartz.quartzJobBean.threadpool.JobRunnable;

import java.util.concurrent.Future;

/**
 * @description 〈执行器〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public class JobBean extends QuartzJobBean {

    private final static BaseLogger LOGGER = BaseLogger.getLogger(JobBean.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        ThreadPoolTaskExecutor poolTaskExecutor = (ThreadPoolTaskExecutor)SpringContextUtil.getBean("buukle.wjs.plugin.threadPoolTaskExecutor");
        JobRunnable jobRunnable = new JobRunnable(System.currentTimeMillis());
        BeanUtils.copyProperties(jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzOperator.JOB_PARAM_KEY),jobRunnable);
        if(!JobMonitor.instance.exsits(jobRunnable)){
            LOGGER.info("任务描述 :{} ,id : {}进入执行池!",jobRunnable.getDescription(),jobRunnable.getId());
            Future<?> future = poolTaskExecutor.submit(jobRunnable);
            JobMonitor.instance.put(jobRunnable,future);
        }else{
            LOGGER.info("任务描述 :{} ,id: {}还在执行,不能再次执行!",jobRunnable.getDescription(),jobRunnable.getId());
        }
    }
}