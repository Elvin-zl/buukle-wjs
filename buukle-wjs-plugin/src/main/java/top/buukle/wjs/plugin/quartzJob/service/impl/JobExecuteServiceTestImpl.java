/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: QuartzTest
 * Author:   zhanglei1102
 * Date:     2019/12/2 10:45
 * Description: 测试定时任务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.quartzJob.service.impl;

import org.springframework.stereotype.Service;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.log.BaseLogger;
import top.buukle.wjs.plugin.quartzJob.service.JobExecuteService;

/**
 * @description 〈测试定时任务〉
 * @author zhanglei1102
 * @create 2019/12/2
 * @since 1.0.0
 */
@Service
public class JobExecuteServiceTestImpl implements JobExecuteService {

    private static final BaseLogger LOGGER = BaseLogger.getLogger(JobExecuteServiceTestImpl.class);

    @Override
    public CommonResponse execute(String params) {
        LOGGER.info("==================BUUKLE-WJS-PLUGIN-TEST=======================");
        LOGGER.info("测试任务执行开始...");
        LOGGER.info("测试任务执行参数 : {}",params);
        LOGGER.info("测试任务执行结束!");
        LOGGER.info("==================BUUKLE-WJS-PLUGIN-TEST=======================");
        return new CommonResponse.Builder().buildSuccess();
    }
}