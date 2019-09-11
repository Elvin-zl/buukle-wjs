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
import top.buukle.wjs.plugin.zk.constants.ZkConstants;
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

    private static final byte[] INIT_ZK_VALUE = "0".getBytes();

    @Autowired
    CuratorFramework curatorFramework;

    @Autowired
    private Environment env;

    public void init() throws Exception {
        // 声明应用父节点
        String appParentNode = ZkConstants.BUUKLE_WJS_APP_PARENT_NODE + StringUtil.BACKSLASH + env.getProperty("spring.application.name");
        // 尝试创建应用父节点
        ZkOperator.createAndInitParentsIfNeeded(curatorFramework,appParentNode,INIT_ZK_VALUE);
        // 开启自动选举
        ZkOperator.leaderLatch(curatorFramework,appParentNode, null);
        // 订阅应用父节点
        ZkOperator.subscribe(curatorFramework,new ApplicationListener(appParentNode,env.getProperty("spring.application.name")));
        // 创建应用子节点
        ZkOperator.createAndInitParentsIfNeeded(curatorFramework,appParentNode + StringUtil.BACKSLASH + SystemUtil.ipPid(),INIT_ZK_VALUE);
        // 查询应用子节点
        List<String> appChildrenNode = ZkOperator.getChildren(curatorFramework,appParentNode);
        if(!CollectionUtils.isEmpty(appChildrenNode)){
            LOGGER.info("当前应用有以下子节点连接到 zk 父节点 :{} : ",appParentNode);
            for (String child: appChildrenNode) {
                LOGGER.info("父节点: {} 下的子节点 : {}",appParentNode,child);
            }
        }
        // 尝试创建任务父节点
        ZkOperator.createAndInitParentsIfNeeded(curatorFramework,ZkConstants.BUUKLE_WJS_JOB_PARENT_NODE,INIT_ZK_VALUE);
        // 订阅任务父节点
        ZkOperator.subscribe(curatorFramework,new WorkerJobListener(ZkConstants.BUUKLE_WJS_JOB_PARENT_NODE,env.getProperty("spring.application.name")));
    }
}