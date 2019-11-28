/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: ApplicationNodeListener
 * Author:   zhanglei1102
 * Date:     2019/9/9 22:49
 * Description: 应用订阅监听
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.zk.listener.impl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import top.buukle.util.StringUtil;
import top.buukle.util.log.BaseLogger;
import top.buukle.wjs.plugin.zk.ZkOperator;
import top.buukle.wjs.plugin.zk.constants.ZkConstants;
import top.buukle.wjs.plugin.zk.listener.ZkAbstractListener;

/**
 * @description 〈应用订阅监听〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public class ApplicationListener extends ZkAbstractListener {

    private static final BaseLogger LOGGER =  BaseLogger.getLogger(ApplicationListener.class);
    public ApplicationListener(String appPath, String applicationName) {
        super(appPath,applicationName);
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
        if(null == treeCacheEvent){
            LOGGER.info("treeCacheEvent为null");
            return;
        }
        TreeCacheEvent.Type eventType = treeCacheEvent.getType();
        switch (eventType) {
            case INITIALIZED:
                ZkOperator.reSharded(curatorFramework, ZkConstants.BUUKLE_WJS_APP_PARENT_NODE + StringUtil.BACKSLASH + super.getApplicationName());
                break;
            case NODE_ADDED:
                LOGGER.info("子节点 : {} 新增...",treeCacheEvent.getData().getPath());
                ZkOperator.reSharded(curatorFramework, ZkConstants.BUUKLE_WJS_APP_PARENT_NODE + StringUtil.BACKSLASH + super.getApplicationName());
                break;
            case NODE_REMOVED:
                LOGGER.info("子节点 : {} 移除...",treeCacheEvent.getData().getPath());
                ZkOperator.reSharded(curatorFramework, ZkConstants.BUUKLE_WJS_APP_PARENT_NODE + StringUtil.BACKSLASH + super.getApplicationName());
                break;
            case CONNECTION_SUSPENDED:
            case CONNECTION_LOST:
                LOGGER.info("子节点 : {} 失去连接...",treeCacheEvent.getData().getPath());
                ZkOperator.reSharded(curatorFramework, ZkConstants.BUUKLE_WJS_APP_PARENT_NODE + StringUtil.BACKSLASH + super.getApplicationName());
                break;
            default:
                break;
        }
    }
}