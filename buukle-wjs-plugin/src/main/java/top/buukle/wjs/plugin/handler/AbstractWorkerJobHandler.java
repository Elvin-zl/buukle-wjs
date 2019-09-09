/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: AbstractWjsHandler
 * Author:   zhanglei1102
 * Date:     2019/9/9 21:30
 * Description: 抽象wjs的handler
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.handler;

import top.buukle.common.call.CommonResponse;

/**
 * @description 〈抽象wjs的handler〉
 * @author zhanglei1102
 * @create 2019/9/9
 * @since 1.0.0
 */
public abstract class AbstractWorkerJobHandler {
    abstract CommonResponse handle();
}