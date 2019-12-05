/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: JobListener
 * Author:   zhanglei1102
 * Date:     2019/11/29 15:56
 * Description: 任务监听器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.plugin.zk.listener.impl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.springframework.core.env.Environment;
import top.buukle.common.log.BaseLogger;
import top.buukle.common.message.MessageDTO;
import top.buukle.util.JsonUtil;
import top.buukle.util.SpringContextUtil;
import top.buukle.util.StringUtil;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.plugin.quartzJob.quartz.JobOperator;
import top.buukle.wjs.plugin.zk.listener.ZkAbstractListener;

/**
 * @description 〈任务监听器〉
 * @author zhanglei1102
 * @create 2019/11/29
 * @since 1.0.0
 */
public class JobListener extends ZkAbstractListener {

    private static final BaseLogger LOGGER =  BaseLogger.getLogger(JobListener.class);

    public JobListener( String path, String applicationName) {
        super(path, applicationName);
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) {
        if(null == treeCacheEvent){
            LOGGER.info("treeCacheEvent 为 null");
            return;
        }
        if(null == treeCacheEvent.getData()){
            LOGGER.info("treeCacheEvent.getData 为 null");
            return;
        }
        String path = treeCacheEvent.getData().getPath();
        String[] pathArr = path.split("/");
        int length =  pathArr.length;
        // /buukle.wjs.job.parent
        if(length == 2 ){
            LOGGER.info("任务父节点 : {} 发生变化...",treeCacheEvent.getData().getPath());
            return;
        }
        // /buukle.wjs.job.parent/buukle-security
        if(length == 3 ){
            LOGGER.info("任务应用目录 : {} 发生变化...",treeCacheEvent.getData().getPath());
            return;
        }
        // /buukle.wjs.job.parent/buukle-security/0
        if(length == 4 ){
            LOGGER.info("任务应用目录下类型目录 : {} 发生变化...",treeCacheEvent.getData().getPath());
            return;
        }
        // /buukle.wjs.job.parent/buukle-security/0/44   -------> 任务
        String applicationPath ;
        if(length == 5 ){
            LOGGER.info("任务应用目录下类型目录下任务节点 : {} 发生变化...",treeCacheEvent.getData().getPath());
        }else{
            LOGGER.error("节点路径 : {} 非法!",treeCacheEvent.getData().getPath());
            return;
        }
        TreeCacheEvent.Type eventType = treeCacheEvent.getType();
        switch (eventType) {
            case CONNECTION_SUSPENDED:
                break;
            case CONNECTION_RECONNECTED:
                break;
            case CONNECTION_LOST:
                break;
            case NODE_ADDED:
                LOGGER.info("节点 : {} 数据新增!",treeCacheEvent.getData().getPath());
                this.handleChange(treeCacheEvent);
                break;
            case NODE_UPDATED:
                LOGGER.info("节点 : {} 数据更新!",treeCacheEvent.getData().getPath());
                this.handleChange(treeCacheEvent);
                break;
            case NODE_REMOVED:
                LOGGER.info("节点 : {} 数据移除!",treeCacheEvent.getData().getPath());
                this.handleChange(treeCacheEvent);
                break;
            case INITIALIZED:
                LOGGER.info("节点 : {} 数据初始化!",treeCacheEvent.getData().getPath());
                this.handleChange(treeCacheEvent);
                break;
            default:

        }
    }

    private void handleChange(TreeCacheEvent treeCacheEvent) {
        if(treeCacheEvent.getData()!=null){
            byte[] message = treeCacheEvent.getData().getData();
            LOGGER.info("任务 : {} 数据有变化，任务数据：{}",treeCacheEvent.getData().getPath(), new String(message));
            MessageDTO<WorkerJob> messageDTO = JsonUtil.parseObject(new String(message), MessageDTO.class);
            WorkerJob workerJob = JsonUtil.parseObject(JsonUtil.toJSONString( messageDTO.getBody()),WorkerJob.class);
            if(null == workerJob){
                LOGGER.info("任务 : {} 数据有变化，任务数据为空!");
                return;
            }
            if(StringUtil.isNotEmpty(workerJob.getBak01()) && workerJob.getBak01().equals(SpringContextUtil.getBean(Environment.class).getProperty("spring.application.name"))){
                LOGGER.info("任务更新后还属于本应用:{}",workerJob.getBak01());
                switch(messageDTO.getActivityEnum()){
                    case INIT:
                        LOGGER.info("任务开始创建,id :{}",workerJob.getId());
                        JobOperator.createJob(workerJob);
                        LOGGER.info("任务创建完成,id :{}",workerJob.getId());
                        break;
                    case PAUSE:
                        LOGGER.info("任务暂停创建,id :{}",workerJob.getId());
                        JobOperator.pauseJob(workerJob);
                        LOGGER.info("任务暂停完成,id :{}",workerJob.getId());
                        break;
                    case RESUME:
                        LOGGER.info("任务开始恢复,id :{}",workerJob.getId());
                        JobOperator.resumeJob(workerJob);
                        LOGGER.info("任务恢复完成,id :{}",workerJob.getId());
                        break;
                    case DELETE:
                        LOGGER.info("任务开始删除,id :{}",workerJob.getId());
                        JobOperator.deleteJob(workerJob);
                        LOGGER.info("任务删除完成,id :{}",workerJob.getId());
                        break;
                    case UPDATE:
                        LOGGER.info("任务开始更新,id :{}",workerJob.getId());
                        JobOperator.updateJob(workerJob);
                        LOGGER.info("任务更新完成,id :{}",workerJob.getId());
                        break;
                }
            }
            else{
                LOGGER.info("任务更新后不属于本应用了:{},开始删除本地任务:{}",workerJob.getBak01(),workerJob.getId());
                JobOperator.deleteJob(workerJob);
                LOGGER.info("任务更新后不属于本应用了:{},删除本地任务成功:{}",workerJob.getBak01(),workerJob.getId());
            }
        }
    }
}