/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: ZkCache
 * Author:   elvin
 * Date:     2019/9/11 23:30
 * Description: zk本地缓存
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.zk.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 〈zk本地缓存〉
 * @author elvin
 * @create 2019/9/11
 * @since 1.0.0
 */
public class ZkCache {
    /** 领导节点缓存*/
    public static Boolean LEADER_CACHE = false;
    /** 分片缓存*/
    public static Map<String,Integer> SHARDED_CACHE = new HashMap<>();

}