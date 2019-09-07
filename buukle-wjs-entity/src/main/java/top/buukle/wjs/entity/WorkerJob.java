package top.buukle.wjs.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created By MBG-GUI-EXTENSION https:// github.com/spawpaw/mybatis-generator-gui-extension
 * Description:
 * 定时任务表
 *
 * @author 
 */
public class WorkerJob implements Serializable {
    /**
     * 作业id
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private Integer id;

    /**
     * 作业类型
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private Integer type;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String description;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String beanReferenceName;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String method;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String params;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String cronExpression;

    /**
     * 结果状态(0 未锁定,1 锁定中)
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private Byte lockStatus;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String resultMsg;

    /**
     * 执行类型(1 单机执行,2 集群执行)
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private Byte executeType;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String ipGroup;

    /**
     * 超时
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private Integer timeOut;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String failStrategy;

    /**
     * 重试次数
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private Integer retryCount;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String remark;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private Date gmtCreated;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String creator;

    /**
     * 创建者的角色id
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private Integer creatorRoleId;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String creatorCode;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private Date gmtModified;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String modifier;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String modifierCode;

    /**
     * 任务状态
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private Integer status;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String bak01;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String bak02;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String bak03;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String bak04;

    /**
     *
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private String bak05;

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.id
     *
     * @return the value of buukle-wjs..worker_job.id
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.id
     *
     * @param id the value for buukle-wjs..worker_job.id
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.type
     *
     * @return the value of buukle-wjs..worker_job.type
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public Integer getType() {
        return type;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withType(Integer type) {
        this.setType(type);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.type
     *
     * @param type the value for buukle-wjs..worker_job.type
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.description
     *
     * @return the value of buukle-wjs..worker_job.description
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withDescription(String description) {
        this.setDescription(description);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.description
     *
     * @param description the value for buukle-wjs..worker_job.description
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.bean_reference_name
     *
     * @return the value of buukle-wjs..worker_job.bean_reference_name
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getBeanReferenceName() {
        return beanReferenceName;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withBeanReferenceName(String beanReferenceName) {
        this.setBeanReferenceName(beanReferenceName);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.bean_reference_name
     *
     * @param beanReferenceName the value for buukle-wjs..worker_job.bean_reference_name
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setBeanReferenceName(String beanReferenceName) {
        this.beanReferenceName = beanReferenceName;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.method
     *
     * @return the value of buukle-wjs..worker_job.method
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getMethod() {
        return method;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withMethod(String method) {
        this.setMethod(method);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.method
     *
     * @param method the value for buukle-wjs..worker_job.method
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.params
     *
     * @return the value of buukle-wjs..worker_job.params
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getParams() {
        return params;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withParams(String params) {
        this.setParams(params);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.params
     *
     * @param params the value for buukle-wjs..worker_job.params
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.cron_expression
     *
     * @return the value of buukle-wjs..worker_job.cron_expression
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withCronExpression(String cronExpression) {
        this.setCronExpression(cronExpression);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.cron_expression
     *
     * @param cronExpression the value for buukle-wjs..worker_job.cron_expression
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.lock_status
     *
     * @return the value of buukle-wjs..worker_job.lock_status
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public Byte getLockStatus() {
        return lockStatus;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withLockStatus(Byte lockStatus) {
        this.setLockStatus(lockStatus);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.lock_status
     *
     * @param lockStatus the value for buukle-wjs..worker_job.lock_status
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setLockStatus(Byte lockStatus) {
        this.lockStatus = lockStatus;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.result_msg
     *
     * @return the value of buukle-wjs..worker_job.result_msg
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getResultMsg() {
        return resultMsg;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withResultMsg(String resultMsg) {
        this.setResultMsg(resultMsg);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.result_msg
     *
     * @param resultMsg the value for buukle-wjs..worker_job.result_msg
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.execute_type
     *
     * @return the value of buukle-wjs..worker_job.execute_type
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public Byte getExecuteType() {
        return executeType;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withExecuteType(Byte executeType) {
        this.setExecuteType(executeType);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.execute_type
     *
     * @param executeType the value for buukle-wjs..worker_job.execute_type
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setExecuteType(Byte executeType) {
        this.executeType = executeType;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.ip_group
     *
     * @return the value of buukle-wjs..worker_job.ip_group
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getIpGroup() {
        return ipGroup;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withIpGroup(String ipGroup) {
        this.setIpGroup(ipGroup);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.ip_group
     *
     * @param ipGroup the value for buukle-wjs..worker_job.ip_group
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setIpGroup(String ipGroup) {
        this.ipGroup = ipGroup;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.time_out
     *
     * @return the value of buukle-wjs..worker_job.time_out
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public Integer getTimeOut() {
        return timeOut;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withTimeOut(Integer timeOut) {
        this.setTimeOut(timeOut);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.time_out
     *
     * @param timeOut the value for buukle-wjs..worker_job.time_out
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.fail_strategy
     *
     * @return the value of buukle-wjs..worker_job.fail_strategy
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getFailStrategy() {
        return failStrategy;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withFailStrategy(String failStrategy) {
        this.setFailStrategy(failStrategy);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.fail_strategy
     *
     * @param failStrategy the value for buukle-wjs..worker_job.fail_strategy
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setFailStrategy(String failStrategy) {
        this.failStrategy = failStrategy;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.retry_count
     *
     * @return the value of buukle-wjs..worker_job.retry_count
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public Integer getRetryCount() {
        return retryCount;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withRetryCount(Integer retryCount) {
        this.setRetryCount(retryCount);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.retry_count
     *
     * @param retryCount the value for buukle-wjs..worker_job.retry_count
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.remark
     *
     * @return the value of buukle-wjs..worker_job.remark
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withRemark(String remark) {
        this.setRemark(remark);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.remark
     *
     * @param remark the value for buukle-wjs..worker_job.remark
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.gmt_created
     *
     * @return the value of buukle-wjs..worker_job.gmt_created
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withGmtCreated(Date gmtCreated) {
        this.setGmtCreated(gmtCreated);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.gmt_created
     *
     * @param gmtCreated the value for buukle-wjs..worker_job.gmt_created
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.creator
     *
     * @return the value of buukle-wjs..worker_job.creator
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getCreator() {
        return creator;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withCreator(String creator) {
        this.setCreator(creator);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.creator
     *
     * @param creator the value for buukle-wjs..worker_job.creator
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.creator_role_id
     *
     * @return the value of buukle-wjs..worker_job.creator_role_id
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public Integer getCreatorRoleId() {
        return creatorRoleId;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withCreatorRoleId(Integer creatorRoleId) {
        this.setCreatorRoleId(creatorRoleId);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.creator_role_id
     *
     * @param creatorRoleId the value for buukle-wjs..worker_job.creator_role_id
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setCreatorRoleId(Integer creatorRoleId) {
        this.creatorRoleId = creatorRoleId;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.creator_code
     *
     * @return the value of buukle-wjs..worker_job.creator_code
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getCreatorCode() {
        return creatorCode;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withCreatorCode(String creatorCode) {
        this.setCreatorCode(creatorCode);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.creator_code
     *
     * @param creatorCode the value for buukle-wjs..worker_job.creator_code
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.gmt_modified
     *
     * @return the value of buukle-wjs..worker_job.gmt_modified
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withGmtModified(Date gmtModified) {
        this.setGmtModified(gmtModified);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.gmt_modified
     *
     * @param gmtModified the value for buukle-wjs..worker_job.gmt_modified
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.modifier
     *
     * @return the value of buukle-wjs..worker_job.modifier
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getModifier() {
        return modifier;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withModifier(String modifier) {
        this.setModifier(modifier);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.modifier
     *
     * @param modifier the value for buukle-wjs..worker_job.modifier
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.modifier_code
     *
     * @return the value of buukle-wjs..worker_job.modifier_code
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getModifierCode() {
        return modifierCode;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withModifierCode(String modifierCode) {
        this.setModifierCode(modifierCode);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.modifier_code
     *
     * @param modifierCode the value for buukle-wjs..worker_job.modifier_code
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setModifierCode(String modifierCode) {
        this.modifierCode = modifierCode;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.status
     *
     * @return the value of buukle-wjs..worker_job.status
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withStatus(Integer status) {
        this.setStatus(status);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.status
     *
     * @param status the value for buukle-wjs..worker_job.status
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.bak01
     *
     * @return the value of buukle-wjs..worker_job.bak01
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getBak01() {
        return bak01;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withBak01(String bak01) {
        this.setBak01(bak01);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.bak01
     *
     * @param bak01 the value for buukle-wjs..worker_job.bak01
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setBak01(String bak01) {
        this.bak01 = bak01;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.bak02
     *
     * @return the value of buukle-wjs..worker_job.bak02
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getBak02() {
        return bak02;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withBak02(String bak02) {
        this.setBak02(bak02);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.bak02
     *
     * @param bak02 the value for buukle-wjs..worker_job.bak02
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setBak02(String bak02) {
        this.bak02 = bak02;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.bak03
     *
     * @return the value of buukle-wjs..worker_job.bak03
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getBak03() {
        return bak03;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withBak03(String bak03) {
        this.setBak03(bak03);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.bak03
     *
     * @param bak03 the value for buukle-wjs..worker_job.bak03
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setBak03(String bak03) {
        this.bak03 = bak03;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.bak04
     *
     * @return the value of buukle-wjs..worker_job.bak04
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getBak04() {
        return bak04;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withBak04(String bak04) {
        this.setBak04(bak04);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.bak04
     *
     * @param bak04 the value for buukle-wjs..worker_job.bak04
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setBak04(String bak04) {
        this.bak04 = bak04;
    }

    /**
     * This method returns the value of the database column buukle-wjs..worker_job.bak05
     *
     * @return the value of buukle-wjs..worker_job.bak05
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public String getBak05() {
        return bak05;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public WorkerJob withBak05(String bak05) {
        this.setBak05(bak05);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-wjs..worker_job.bak05
     *
     * @param bak05 the value for buukle-wjs..worker_job.bak05
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    public void setBak05(String bak05) {
        this.bak05 = bak05;
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", description=").append(description);
        sb.append(", beanReferenceName=").append(beanReferenceName);
        sb.append(", method=").append(method);
        sb.append(", params=").append(params);
        sb.append(", cronExpression=").append(cronExpression);
        sb.append(", lockStatus=").append(lockStatus);
        sb.append(", resultMsg=").append(resultMsg);
        sb.append(", executeType=").append(executeType);
        sb.append(", ipGroup=").append(ipGroup);
        sb.append(", timeOut=").append(timeOut);
        sb.append(", failStrategy=").append(failStrategy);
        sb.append(", retryCount=").append(retryCount);
        sb.append(", remark=").append(remark);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", creator=").append(creator);
        sb.append(", creatorRoleId=").append(creatorRoleId);
        sb.append(", creatorCode=").append(creatorCode);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", modifier=").append(modifier);
        sb.append(", modifierCode=").append(modifierCode);
        sb.append(", status=").append(status);
        sb.append(", bak01=").append(bak01);
        sb.append(", bak02=").append(bak02);
        sb.append(", bak03=").append(bak03);
        sb.append(", bak04=").append(bak04);
        sb.append(", bak05=").append(bak05);
        sb.append("]");
        return sb.toString();
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        WorkerJob other = (WorkerJob) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getBeanReferenceName() == null ? other.getBeanReferenceName() == null : this.getBeanReferenceName().equals(other.getBeanReferenceName()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
            && (this.getCronExpression() == null ? other.getCronExpression() == null : this.getCronExpression().equals(other.getCronExpression()))
            && (this.getLockStatus() == null ? other.getLockStatus() == null : this.getLockStatus().equals(other.getLockStatus()))
            && (this.getResultMsg() == null ? other.getResultMsg() == null : this.getResultMsg().equals(other.getResultMsg()))
            && (this.getExecuteType() == null ? other.getExecuteType() == null : this.getExecuteType().equals(other.getExecuteType()))
            && (this.getIpGroup() == null ? other.getIpGroup() == null : this.getIpGroup().equals(other.getIpGroup()))
            && (this.getTimeOut() == null ? other.getTimeOut() == null : this.getTimeOut().equals(other.getTimeOut()))
            && (this.getFailStrategy() == null ? other.getFailStrategy() == null : this.getFailStrategy().equals(other.getFailStrategy()))
            && (this.getRetryCount() == null ? other.getRetryCount() == null : this.getRetryCount().equals(other.getRetryCount()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreatorRoleId() == null ? other.getCreatorRoleId() == null : this.getCreatorRoleId().equals(other.getCreatorRoleId()))
            && (this.getCreatorCode() == null ? other.getCreatorCode() == null : this.getCreatorCode().equals(other.getCreatorCode()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getModifierCode() == null ? other.getModifierCode() == null : this.getModifierCode().equals(other.getModifierCode()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getBak01() == null ? other.getBak01() == null : this.getBak01().equals(other.getBak01()))
            && (this.getBak02() == null ? other.getBak02() == null : this.getBak02().equals(other.getBak02()))
            && (this.getBak03() == null ? other.getBak03() == null : this.getBak03().equals(other.getBak03()))
            && (this.getBak04() == null ? other.getBak04() == null : this.getBak04().equals(other.getBak04()))
            && (this.getBak05() == null ? other.getBak05() == null : this.getBak05().equals(other.getBak05()));
    }

    /**
     *
     * @mbg.generated Sat Sep 07 14:34:03 CST 2019
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getBeanReferenceName() == null) ? 0 : getBeanReferenceName().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getCronExpression() == null) ? 0 : getCronExpression().hashCode());
        result = prime * result + ((getLockStatus() == null) ? 0 : getLockStatus().hashCode());
        result = prime * result + ((getResultMsg() == null) ? 0 : getResultMsg().hashCode());
        result = prime * result + ((getExecuteType() == null) ? 0 : getExecuteType().hashCode());
        result = prime * result + ((getIpGroup() == null) ? 0 : getIpGroup().hashCode());
        result = prime * result + ((getTimeOut() == null) ? 0 : getTimeOut().hashCode());
        result = prime * result + ((getFailStrategy() == null) ? 0 : getFailStrategy().hashCode());
        result = prime * result + ((getRetryCount() == null) ? 0 : getRetryCount().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getGmtCreated() == null) ? 0 : getGmtCreated().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreatorRoleId() == null) ? 0 : getCreatorRoleId().hashCode());
        result = prime * result + ((getCreatorCode() == null) ? 0 : getCreatorCode().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getModifierCode() == null) ? 0 : getModifierCode().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getBak01() == null) ? 0 : getBak01().hashCode());
        result = prime * result + ((getBak02() == null) ? 0 : getBak02().hashCode());
        result = prime * result + ((getBak03() == null) ? 0 : getBak03().hashCode());
        result = prime * result + ((getBak04() == null) ? 0 : getBak04().hashCode());
        result = prime * result + ((getBak05() == null) ? 0 : getBak05().hashCode());
        return result;
    }
}