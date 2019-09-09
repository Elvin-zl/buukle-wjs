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
import top.buukle.util.log.BaseLogger;
import top.buukle.wjs.plugin.zk.abstracts.ZkAbstractListener;

/**
 * @description 〈zk节点操作工具〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public class ZkOperator {

    private static BaseLogger LOGGER = BaseLogger.getLogger(ZkOperator.class);

    /**
     * @description 订阅节点
     * @param curatorFramework
     * @param zkListener
     * @return void
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    public static void subscribe(CuratorFramework curatorFramework, ZkAbstractListener zkListener) throws Exception {
        LOGGER.info("准备订阅节点 : {}",zkListener.getAppPath());
       try{
           TreeCache treeCache = new TreeCache(curatorFramework, zkListener.getAppPath());
           treeCache.start();
           treeCache.getListenable().addListener(zkListener);
           LOGGER.info("订阅节点成功 : {}",zkListener.getAppPath());
       }catch (Exception e){
           e.printStackTrace();
           LOGGER.info("订阅节点失败 : {},原因 :{}",zkListener.getAppPath(),e.getMessage());
       }
    }
}