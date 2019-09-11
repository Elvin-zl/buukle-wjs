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
    /** 应用领导节点*/
    private static final String BUUKLE_WJS_LEADER_NODE = "/buukle.wjs.leader";
    @Autowired
    CuratorFramework curatorFramework;

    @Autowired
    private Environment env;

    public void init() throws Exception {
        String appParentNode = BUUKLE_WJS_APP_PARENT_NODE + StringUtil.BACKSLASH + env.getProperty("spring.application.name");
        ZkOperator.createAndInitParentsIfNeeded(curatorFramework,appParentNode + StringUtil.BACKSLASH + SystemUtil.ipPid(),"0".getBytes());
        List<String> appChildrenNode = ZkOperator.getChildren(curatorFramework,appParentNode);
        if(!CollectionUtils.isEmpty(appChildrenNode)){
            LOGGER.info("当前应用有以下子节点连接到 zk 父节点 :{} : ",appParentNode);
            for (String child: appChildrenNode) {
                LOGGER.info("父节点: {} 下的子节点 : {}",appParentNode,child);
            }
        }
        ZkOperator.subscribe(curatorFramework,new ApplicationListener(appParentNode));
        ZkOperator.createAndInitParentsIfNeeded(curatorFramework,BUUKLE_WJS_JOB_PARENT_NODE,"0".getBytes());
        ZkOperator.subscribe(curatorFramework,new WorkerJobListener(BUUKLE_WJS_JOB_PARENT_NODE));
        ZkOperator.leaderLatch(curatorFramework,BUUKLE_WJS_LEADER_NODE, null);
    }
}