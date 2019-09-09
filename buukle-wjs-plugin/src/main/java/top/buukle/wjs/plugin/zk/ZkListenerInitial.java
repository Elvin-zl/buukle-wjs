/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: ZkInit
 * Author:   zhanglei1102
 * Date:     2019/9/9 22:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.zk;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import top.buukle.wjs.plugin.util.SystemUtil;
import top.buukle.wjs.plugin.zk.listener.impl.ApplicationListener;
import top.buukle.wjs.plugin.zk.listener.impl.WorkerJobListener;

/**
 * @description 〈初始化zk相关〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
@Component
public class ZkListenerInitial {

    @Autowired
    CuratorFramework curatorFramework;

    @Autowired
    private Environment env;

    public void init() throws Exception {

        // 创建应用节点
        String applicationSessionNode = "/buukle.session";

        // 链接应用节点

        // 创建任务节点

        // 链接任务节点

        // 订阅应用节点
        String appPath = "/buukle.zk.wjs/" + env.getProperty("spring.application.name") + "/" + SystemUtil.ipPid();
        ZkOperator.subscribe(curatorFramework,new ApplicationListener(appPath));
        // 订阅任务节点
        String jobPath = "/buukle.zk.wjs/" + env.getProperty("spring.application.name") + ".job" + "/" + SystemUtil.ipPid();
        ZkOperator.subscribe(curatorFramework,new WorkerJobListener(jobPath));
    }
}