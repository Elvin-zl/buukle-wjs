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
import top.buukle.wjs.plugin.zk.ZkOperator;
import top.buukle.wjs.plugin.zk.listener.ZkAbstractListener;

/**
 * @description 〈应用订阅监听〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public class TestListener extends ZkAbstractListener {

    private static final BaseLogger LOGGER =  BaseLogger.getLogger(TestListener.class);
    public TestListener(String appPath, String applicationName) {
        super(appPath,applicationName);
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
        ZkOperator.reSharded("/elvin.buukle.testp");
    }
}