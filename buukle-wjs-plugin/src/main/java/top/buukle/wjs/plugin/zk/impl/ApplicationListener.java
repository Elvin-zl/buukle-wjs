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
package top.buukle.wjs.plugin.zk.impl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import top.buukle.wjs.plugin.zk.abstracts.ZkAbstractListener;

/**
 * @description 〈应用订阅监听〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public class ApplicationListener extends ZkAbstractListener {


    public ApplicationListener(String appPath) {
        super();
        super.setAppPath(appPath);
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
        // TODO
    }
}