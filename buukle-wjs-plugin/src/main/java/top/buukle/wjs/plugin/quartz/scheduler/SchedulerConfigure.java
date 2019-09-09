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
package top.buukle.wjs.plugin.quartz.scheduler;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import top.buukle.util.StringUtil;

import java.util.Properties;

/**
 * @description 〈调度器〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
@Configuration
public class SchedulerConfigure {

    /** 调度器使用线程池的类型 配置key*/
    private static final String SCHEDULER_POOL_CLASS_KEY = "wjs.scheduler.poolClass";
    /** 调度器线程池大小 key*/
    private static final String SCHEDULER_POOL_SIZE_KEY = "wjs.scheduler.poolSize";
    /** 调度器使用线程池的类型 配置key*/
    private static final String SCHEDULER_PRIORITY_KEY = "wjs.scheduler.priority";

    @Autowired
    private Environment env;

    /**
     * @description 初始化调度器
     * @param
     * @return org.quartz.Scheduler
     * @Author elvin
     * @Date 2019/9/10
     */
    @Bean(name = "buukle.wjs.plugin.scheduler")
    Scheduler getScheduler(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("org.quartz.threadPool.class", StringUtil.isEmpty(env.getProperty(SCHEDULER_POOL_CLASS_KEY))? "org.quartz.simpl.SimpleThreadPool" : env.getProperty(SCHEDULER_POOL_CLASS_KEY));
        properties.setProperty("org.quartz.threadPool.threadCount", StringUtil.isEmpty(env.getProperty(SCHEDULER_POOL_SIZE_KEY))? "4" : env.getProperty(SCHEDULER_POOL_SIZE_KEY));
        properties.setProperty("org.quartz.threadPool.threadPriority",StringUtil.isEmpty(env.getProperty(""))? "5" : env.getProperty(SCHEDULER_PRIORITY_KEY));
        schedulerFactoryBean.setQuartzProperties(properties);
        schedulerFactoryBean.setSchedulerName("buukle-wjs-plugin-scheduler");
        schedulerFactoryBean.setStartupDelay(30);
        schedulerFactoryBean.setAutoStartup(true);
        return schedulerFactoryBean.getScheduler();
    }

}