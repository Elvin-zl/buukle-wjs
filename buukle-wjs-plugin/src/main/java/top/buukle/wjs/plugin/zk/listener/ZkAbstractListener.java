/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: ZkAbstractListener
 * Author:   zhanglei1102
 * Date:     2019/9/9 23:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.zk.listener;

import org.apache.curator.framework.recipes.cache.TreeCacheListener;

/**
 * @description 〈〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public abstract class ZkAbstractListener implements TreeCacheListener {

    private String path;
    private String applicationName;

    public ZkAbstractListener(String path,String applicationName) {
        this.path = path;
        this.applicationName = applicationName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}