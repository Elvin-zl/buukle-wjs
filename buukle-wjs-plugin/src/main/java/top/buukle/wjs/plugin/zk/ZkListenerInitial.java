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
import org.springframework.util.CollectionUtils;
import top.buukle.util.StringUtil;
import top.buukle.util.SystemUtil;
import top.buukle.util.log.BaseLogger;
import top.buukle.wjs.plugin.zk.listener.impl.ApplicationListener;
import top.buukle.wjs.plugin.zk.listener.impl.WorkerJobListener;

import java.util.List;

/**
 * @description 〈初始化zk相关〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
@Component
public class ZkListenerInitial {

    private static final BaseLogger LOGGER =  BaseLogger.getLogger(ZkListenerInitial.class);
    /** 应用父节点*/
    private static final String BUUKLE_WJS_APP_PARENT_NODE = "/buukle.wjs.app.parent";
    /** 任务父节点*/
    private static final String BUUKLE_WJS_JOB_PARENT_NODE = "/buukle.wjs.job.parent";
    @Autowired
    CuratorFramework curatorFramework;

    @Autowired
    private Environment env;

    public void init() throws Exception {

        // 创建应用子节点
        String appChildNode = BUUKLE_WJS_APP_PARENT_NODE + StringUtil.BACKSLASH + env.getProperty("spring.application.name")+ StringUtil.UNDERLINE + SystemUtil.ipPid();
        curatorFramework.create().creatingParentsIfNeeded().forPath(appChildNode, "0".getBytes());
        // 查询应用子节点
        List<String> appChildrenNode = curatorFramework.getChildren().forPath(BUUKLE_WJS_APP_PARENT_NODE);
        if(!CollectionUtils.isEmpty(appChildrenNode)){
            LOGGER.info("当前有以下节点连接到zk : ");
            for (String appNode: appChildrenNode) {
                LOGGER.info("buukle.wjs.plugin.zk.app.child ----->  :{}",appNode);
            }
        }
        // 订阅应用父节点
        ZkOperator.subscribe(curatorFramework,new ApplicationListener(BUUKLE_WJS_APP_PARENT_NODE));

        // 创建任务父节点
        String applicationJobNode = BUUKLE_WJS_JOB_PARENT_NODE;

        // 订阅任务父节点
        String jobPath = "/buukle.zk.wjs" + "/" + env.getProperty("spring.application.name") + ".job" + "/" + SystemUtil.ipPid();
        ZkOperator.subscribe(curatorFramework,new WorkerJobListener(jobPath));
    }
}