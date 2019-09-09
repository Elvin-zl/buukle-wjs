/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: Scheduler
 * Author:   zhanglei1102
 * Date:     2019/9/9 20:28
 * Description: 调度器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import top.buukle.util.StringUtil;
import top.buukle.wjs.plugin.reject.DefaultThreadPoolRejectPolicy;

import java.util.Properties;

/**
 * @description 〈调度器〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
@Configuration
public class QuartzJobConfigure {

    /** 调度器使用线程池的类型 配置key*/
    private static final String SCHEDULER_POOL_CLASS_KEY = "wjs.scheduler.poolClass";
    /** 调度器线程池大小 key*/
    private static final String SCHEDULER_POOL_SIZE_KEY = "wjs.scheduler.poolSize";
    /** 调度器使用线程池的类型 配置key*/
    private static final String SCHEDULER_PRIORITY_KEY = "wjs.scheduler.priority";

    /** 任务执行线程池的核心线程数 配置key*/
    private static final String TASK_EXECUTOR_CORE_POOL_SIZE_KEY = "wjs.pool.coreSize";
    /** 任务执行线程池的size 配置key*/
    private static final String TASK_EXECUTOR_MAX_POOL_SIZE_KEY = "wjs.pool.maxSize";
    /** 任务执行线程池的队列容量 配置key*/
    private static final String TASK_EXECUTOR_QUEUE_CAPACITY_KEY = "wjs.pool.queueCapacity";
    /** 任务执行线程池的空闲时长 配置key*/
    private static final String TASK_EXECUTOR_KEEP_ALIVE_SECONDS_KEY = "wjs.pool.keepAliveSeconds";

    @Autowired
    private Environment env;

    @Autowired
    private DefaultThreadPoolRejectPolicy defaultThreadPoolRejectPolicy ;

    /**
     * @description 初始化调度器
     * @param
     * @return org.springframework.scheduling.quartz.SchedulerFactoryBean
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    @Bean(name = "buukle.wjs.plugin.SchedulerFactoryBean")
    SchedulerFactoryBean getSchedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("org.quartz.threadPool.class", StringUtil.isEmpty(env.getProperty(SCHEDULER_POOL_CLASS_KEY))? "org.quartz.simpl.SimpleThreadPool" : env.getProperty(SCHEDULER_POOL_CLASS_KEY));
        properties.setProperty("org.quartz.threadPool.threadCount", StringUtil.isEmpty(env.getProperty(SCHEDULER_POOL_SIZE_KEY))? "4" : env.getProperty(SCHEDULER_POOL_SIZE_KEY));
        properties.setProperty("org.quartz.threadPool.threadPriority",StringUtil.isEmpty(env.getProperty(""))? "5" : env.getProperty(SCHEDULER_PRIORITY_KEY));
        schedulerFactoryBean.setQuartzProperties(properties);
        schedulerFactoryBean.setSchedulerName("buukle-wjs-plugin-scheduler");
        schedulerFactoryBean.setStartupDelay(30);
        schedulerFactoryBean.setAutoStartup(true);
        return schedulerFactoryBean;
    }

    /**
     * @description 初始化处理任务所用线程池
     * @param
     * @return org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    @Bean(name = "buukle.wjs.plugin.threadPoolTaskExecutor")
    ThreadPoolTaskExecutor getThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(Integer.parseInt(StringUtil.isEmpty(env.getProperty(TASK_EXECUTOR_CORE_POOL_SIZE_KEY))? "" : "5"));
        threadPoolTaskExecutor.setMaxPoolSize(Integer.parseInt(StringUtil.isEmpty(env.getProperty(TASK_EXECUTOR_MAX_POOL_SIZE_KEY))? "" : "20"));
        threadPoolTaskExecutor.setQueueCapacity(Integer.parseInt(StringUtil.isEmpty(env.getProperty(TASK_EXECUTOR_QUEUE_CAPACITY_KEY))? "" : "500"));
        threadPoolTaskExecutor.setKeepAliveSeconds(Integer.parseInt(StringUtil.isEmpty(env.getProperty(TASK_EXECUTOR_KEEP_ALIVE_SECONDS_KEY))? "" : "30"));
        threadPoolTaskExecutor.setRejectedExecutionHandler(null == defaultThreadPoolRejectPolicy ?  new DefaultThreadPoolRejectPolicy() :defaultThreadPoolRejectPolicy);
        return threadPoolTaskExecutor;
    }
}