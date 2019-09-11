/**
 * Copyright (C), 2015-2019  http://www.jd.com
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
import top.buukle.wjs.plugin.zk.listener.ZkAbstractListener;

import java.util.List;

/**
 * @description 〈应用订阅监听〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public class ApplicationListener extends ZkAbstractListener {


    public ApplicationListener(String appPath) {
        super(appPath);
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
//        if(null == treeCacheEvent){
//            LOGGER.info("treeCacheEvent为null");
//            return;
//        }
//        TreeCacheEvent.Type eventType = treeCacheEvent.getType();
//        switch (eventType) {
//            case INITIALIZED:
//            case NODE_ADDED:
//                break;
//            case NODE_REMOVED:
//                //移除的节点不是领导节点，有当前领导节点执行重新分片
//                if("1".equals(LocalCacheUtils.shardCache.get(Constants.IS_LEADER))){
//                    shardService.resetShards();
//                }
//                break;
//            case CONNECTION_SUSPENDED:
//            case CONNECTION_LOST:
//                log.info("临时节点【{}】失去连接。。。",treeCacheEvent.getData().getPath());
//                LocalCacheUtils.shardCache.put(Constants.CACHE_SHARD_NO,"0");
//                LocalCacheUtils.shardCache.put(Constants.CACHE_TOTAL_SHARD,"0");
//                break;
//            default:
//                if(treeCacheEvent.getData().getPath().indexOf(SystemUtil.ip_pid())>0){
//                    List<String> ips = zookeeperClient.getChildren(Constants.ZK_SESSION_NODE);
//                    int totalShard = ips!=null ?ips.size():0;
//                    LocalCacheUtils.shardCache.put(Constants.CACHE_TOTAL_SHARD, String.valueOf(totalShard));
//                    LocalCacheUtils.shardCache.put(Constants.CACHE_SHARD_NO,new String(treeCacheEvent.getData().getData()));
//                    log.info("分片序号发生变化，变化后的值为：{}",LocalCacheUtils.shardCache.get(Constants.CACHE_SHARD_NO));
//                }else{
//                    log.info("别的服务分片序号发生改变，path:{}",treeCacheEvent.getData().getPath());
//                }
//        }
    }
}