/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: WorkerJobClient
 * Author:   zhanglei1102
 * Date:     2019/12/5 10:16
 * Description: 任务操作客户端
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.client;

import org.springframework.core.env.Environment;
import top.buukle.common.call.code.BaseReturnEnum;
import top.buukle.common.exception.CommonException;
import top.buukle.common.log.BaseLogger;
import top.buukle.common.message.MessageActivityEnum;
import top.buukle.common.message.MessageDTO;
import top.buukle.common.message.MessageHead;
import top.buukle.util.JsonUtil;
import top.buukle.util.SpringContextUtil;
import top.buukle.util.StringUtil;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.plugin.zk.ZkOperator;
import top.buukle.wjs.plugin.zk.constants.ZkConstants;

import java.util.Date;

/**
 * @description 〈任务操作客户端〉
 * @author zhanglei1102
 * @create 2019/12/5
 * @since 1.0.0
 */
public class WorkerJobClient {

    private final static BaseLogger LOGGER = BaseLogger.getLogger(WorkerJobClient.class);

    public static String operateJob(String operatorId, WorkerJob workerJob, MessageActivityEnum activityEnum) throws Exception {
        String path =
                // 任务总目录层
                ZkConstants.BUUKLE_WJS_JOB_PARENT_NODE +
                        // 应用目录
                        StringUtil.BACKSLASH + workerJob.getBak01() +
                        // 任务类型层
                        StringUtil.BACKSLASH + workerJob.getExecuteType() +
                        // 任务id目录层       -- 此层 data 为该任务的ipPid
                        StringUtil.BACKSLASH + workerJob.getId();

        // 组装任务变更消息dto
        MessageHead head = new MessageHead();
        head.setApplicationName(SpringContextUtil.getBean(Environment.class).getProperty("spring.application.name"));
        head.setOperationTime(new Date());
        head.setOperatorId(operatorId);
        MessageDTO messageDTO = new MessageDTO(head,activityEnum,workerJob);
        switch (activityEnum){
            case INIT:
                LOGGER.info("尝试在zk创建任务,id : {},path :{}",workerJob.getId(),path);
                ZkOperator.createAndInitParentsIfNeededEphemeral(path, JsonUtil.toJSONString(messageDTO).getBytes());
                LOGGER.info("在zk创建任务完成,id : {},path :{}",workerJob.getId(),path);
                break;

            case UPDATE:
                LOGGER.info("尝试在zk更新任务,id : {},path :{}",workerJob.getId(),path);
                ZkOperator.setData(path, JsonUtil.toJSONString(messageDTO).getBytes());
                LOGGER.info("在zk更新任务完成,id : {},path :{}",workerJob.getId(),path);
                break;

            case PAUSE:
                LOGGER.info("尝试在zk暂停任务,id : {},path :{}",workerJob.getId(),path);
                ZkOperator.setData(path, JsonUtil.toJSONString(messageDTO).getBytes());
                LOGGER.info("在zk暂停任务完成,id : {},path :{}",workerJob.getId(),path);
                break;

            case RESUME:
                LOGGER.info("尝试在zk恢复任务,id : {},path :{}",workerJob.getId(),path);
                ZkOperator.setData(path, JsonUtil.toJSONString(messageDTO).getBytes());
                LOGGER.info("在zk恢复任务完成,id : {},path :{}",workerJob.getId(),path);
                break;

            case DELETE:
                LOGGER.info("尝试在zk删除任务,id : {},path :{}",workerJob.getId(),path);
                ZkOperator.setData(path, JsonUtil.toJSONString(messageDTO).getBytes());
                LOGGER.info("在zk删除任务完成,id : {},path :{}",workerJob.getId(),path);
                break;
        }
        return path;
    }

}