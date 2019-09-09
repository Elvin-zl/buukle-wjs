/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: ScheduleInit
 * Author:   zhanglei1102
 * Date:     2019/9/9 21:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import top.buukle.common.call.CommonRequest;
import top.buukle.common.call.PageResponse;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.plugin.invoker.SchedulerInvoker;
import top.buukle.wjs.plugin.quartz.QuartzOperator;
import top.buukle.wjs.plugin.zk.ZkListenerInitial;

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

    @Autowired
    private ZkListenerInitial listenerInitial;
    @Autowired
    private SchedulerInvoker schedulerInvoker;
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
        PageResponse response = schedulerInvoker.getApplicationJob(commonRequest);
        PageResponse.PageBody body = response.getBody();
        List<WorkerJob> list = (List<WorkerJob>) body.getList();
        // 遍历创建定时任务,并加上监控
        for (WorkerJob workerJob : list) {
            QuartzOperator.createJob(workerJob,scheduler);
        }
    }
}