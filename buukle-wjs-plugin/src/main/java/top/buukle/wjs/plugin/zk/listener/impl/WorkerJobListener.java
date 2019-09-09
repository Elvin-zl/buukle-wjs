/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: WorkerJobListener
 * Author:   zhanglei1102
 * Date:     2019/9/9 23:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.zk.listener.impl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import top.buukle.wjs.plugin.zk.listener.ZkAbstractListener;

/**
 * @author zhanglei1102
 * @description 〈〉
 * @create 2019/9/9
 * @since 1.0.0
 */
public class WorkerJobListener extends ZkAbstractListener {

    public WorkerJobListener(String appPath) {
        super();
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
        // TODO
    }
}