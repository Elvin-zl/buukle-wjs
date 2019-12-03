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
import top.buukle.common.log.BaseLogger;
import top.buukle.util.StringUtil;
import top.buukle.wjs.plugin.zk.ZkOperator;
import top.buukle.wjs.plugin.zk.constants.ZkConstants;
import top.buukle.wjs.plugin.zk.listener.ZkAbstractListener;

/**
 * @description 〈应用监听器〉
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
        if(null == treeCacheEvent.getData()){
            return;
        }
        String path = treeCacheEvent.getData().getPath();
        String[] pathArr = path.split("/");
        int length =  pathArr.length;
        // /buukle.wjs.app.parent
        if(length == 2 ){
            LOGGER.info("引用父节点 : {} 发生变化...",treeCacheEvent.getData().getPath());
            return;
        }
        // /buukle.wjs.app.parent/buukle-security
        if(length == 3 ){
            LOGGER.info("应用目录 : {} 发生变化...",treeCacheEvent.getData().getPath());
            return;
        }
        // /buukle.wjs.app.parent/buukle-security/127.0.0.1_9527   -------> 实例新增需要重新分片
        String applicationPath ;
        if(length == 4 ){
            LOGGER.info("应用实例 : {} 发生变化...",treeCacheEvent.getData().getPath());
            applicationPath = pathArr[0] + StringUtil.BACKSLASH + pathArr[1] + StringUtil.BACKSLASH + pathArr[2];
        }else{
            LOGGER.error("节点路径 : {} 非法!",treeCacheEvent.getData().getPath());
            return;
        }
        switch (eventType) {
            case INITIALIZED:
                LOGGER.info("应用实例 : {} 初始化...",path);
                ZkOperator.reSharded( applicationPath);
                break;
            case NODE_ADDED:
                LOGGER.info("应用实例 : {} 新增...",path);
                ZkOperator.reSharded(applicationPath);
                break;
            case NODE_UPDATED:
                LOGGER.info("应用实例 : {} 更新.(由分片引起不用再次分片)..",path);
                break;
            case NODE_REMOVED:
                LOGGER.info("应用实例 : {} 移除...",path);
                ZkOperator.reSharded(applicationPath);
                break;
            case CONNECTION_SUSPENDED:
            case CONNECTION_LOST:
                LOGGER.info("应用实例 : {} 失去连接...",path);
                ZkOperator.reSharded(applicationPath);
                break;
            default:
                break;
        }
    }
}