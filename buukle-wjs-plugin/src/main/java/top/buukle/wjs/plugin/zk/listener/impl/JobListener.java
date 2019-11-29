/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: JobListener
 * Author:   zhanglei1102
 * Date:     2019/11/29 15:56
 * Description: 任务监听器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.zk.listener.impl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import top.buukle.util.log.BaseLogger;
import top.buukle.wjs.plugin.zk.listener.ZkAbstractListener;

/**
 * @description 〈任务监听器〉
 * @author zhanglei1102
 * @create 2019/11/29
 * @since 1.0.0
 */
public class JobListener extends ZkAbstractListener {

    private static final BaseLogger LOGGER =  BaseLogger.getLogger(JobListener.class);

    public JobListener(String path, String applicationName) {
        super(path, applicationName);
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
            case NODE_ADDED:
                break;
            case CONNECTION_SUSPENDED:
            case CONNECTION_LOST:
                LOGGER.info("临时节点 : {} 失去连接。。。",treeCacheEvent.getData().getPath());
                break;
            default:
                if(treeCacheEvent.getData()!=null){
                    byte[] message = treeCacheEvent.getData().getData();
                    LOGGER.info("节点 : {} 数据有变化，节点数据：{}",treeCacheEvent.getData().getPath(),treeCacheEvent.getData().getPath(), message);
                    //切换为集群模式，删除zk上的锁定节点
                    //删除本地所有quartz实例
                    //立即执行
                    //暂停
                    //恢复
                }
        }
    }
}