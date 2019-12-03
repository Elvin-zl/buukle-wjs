/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: quartzHandler
 * Author:   zhanglei1102
 * Date:     2019/12/2 11:15
 * Description: 执行器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.quartzJob.service;

import top.buukle.common.call.CommonResponse;

/**
 * @description 〈执行器〉
 * @author zhanglei1102
 * @create 2019/12/2
 * @since 1.0.0
 */
public interface JobExecuteService {

    public CommonResponse execute(String params) ;

}