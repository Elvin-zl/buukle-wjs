/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: ZkUtil
 * Author:   zhanglei1102
 * Date:     2019/9/9 22:52
 * Description: zk节点操作工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import top.buukle.util.log.BaseLogger;
import top.buukle.wjs.plugin.zk.listener.ZkAbstractListener;

import java.util.List;

/**
 * @description 〈zk节点操作工具〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public class ZkOperator {

    private static BaseLogger LOGGER = BaseLogger.getLogger(ZkOperator.class);

    /**
     * @description 创建节点
     * @param curatorFramework
     * @param path
     * @param bytes
     * @return void
     * @Author zhanglei1102
     * @Date 2019/9/11
     */
    public static void createAndInitParentsIfNeeded(CuratorFramework curatorFramework, String path, byte[] bytes) throws Exception {
        curatorFramework.create().creatingParentsIfNeeded().forPath(path, "0".getBytes());
    }

    /**
     * @description 订阅节点
     * @param curatorFramework
     * @param zkListener
     * @return void
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    public static void subscribe(CuratorFramework curatorFramework, ZkAbstractListener zkListener) throws Exception {
        LOGGER.info("准备订阅节点 : {}",zkListener.getPath());
       try{
           TreeCache treeCache = new TreeCache(curatorFramework, zkListener.getPath());
           treeCache.start();
           treeCache.getListenable().addListener(zkListener);
           LOGGER.info("订阅节点成功 : {}",zkListener.getPath());
       }catch (Exception e){
           e.printStackTrace();
           LOGGER.info("订阅节点失败 : {},原因 :{}",zkListener.getPath(),e.getMessage());
       }
    }

    /**
     * @description 获取子节点
     * @param curatorFramework
     * @param path
     * @return java.util.List<java.lang.String>
     * @Author zhanglei1102
     * @Date 2019/9/11
     */
    public static List<String> getChildren(CuratorFramework curatorFramework, String path) throws Exception {
        return curatorFramework.getChildren().forPath(path);
    }

    /**
     * @description 开启选举
     * @param
     * @param curatorFramework
     * @param path
     * @param leaderLatchList
     * @return void
     * @Author zhanglei1102
     * @Date 2019/9/11
     */
    public static void leaderLatch(CuratorFramework curatorFramework, String path, List<LeaderLatch> leaderLatchList) throws Exception {
        LeaderLatch leaderLatch = new LeaderLatch(curatorFramework,path);
        leaderLatch.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {
                LOGGER.info("当前应用当选为主节点!路径 :{}",path);
            }
            @Override
            public void notLeader() {
            }
        });
        leaderLatchList.add(leaderLatch);
        leaderLatch.start();
    }
}