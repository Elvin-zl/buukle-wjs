/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: WjsExecutor
 * Author:   zhanglei1102
 * Date:     2019/9/9 21:32
 * Description: 执行器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.executor;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import top.buukle.util.SpringContextUtil;
import top.buukle.wjs.plugin.QuartzJobOperator;

/**
 * @description 〈执行器〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public class QuartzJobExecutor extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ThreadPoolTaskExecutor poolTaskExecutor = (ThreadPoolTaskExecutor)SpringContextUtil.getBean("buukle.wjs.plugin.threadPoolTaskExecutor");
        QuartzJobRunable runable = new QuartzJobRunable();
        BeanUtils.copyProperties(jobExecutionContext.getJobDetail().getJobDataMap().get(QuartzJobOperator.JOB_PARAM_KEY),runable);
        poolTaskExecutor.submit(runable);
    }
}