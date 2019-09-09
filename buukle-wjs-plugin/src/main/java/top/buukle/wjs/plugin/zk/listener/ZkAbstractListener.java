/**
 * Copyright (C), 2015-2019  http://www.jd.com
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

    private String appPath;

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }
}