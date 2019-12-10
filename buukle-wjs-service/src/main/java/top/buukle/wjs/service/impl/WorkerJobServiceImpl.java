package top.buukle.wjs .service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.FuzzyResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.common.call.code.BaseReturnEnum;
import top.buukle.common.call.vo.FuzzyVo;
import top.buukle.common.exception.CommonException;
import top.buukle.common.message.MessageActivityEnum;
import top.buukle.common.status.StatusConstants;
import top.buukle.common.mvc.CommonMapper;
import top.buukle.util.SpringContextUtil;
import top.buukle.wjs .dao.WorkerJobMapper;
import top.buukle.security.entity.User;
import top.buukle.wjs .entity.WorkerJob;
import top.buukle.wjs .entity.WorkerJobExample;
import top.buukle.common.mvc.BaseQuery;
import top.buukle.wjs .entity.vo.WorkerJobQuery;
import top.buukle.security.plugin.util.SessionUtil;
import top.buukle.wjs.plugin.client.WorkerJobClient;
import top.buukle.wjs.service.WorkerJobLogsService;
import top.buukle.wjs .service.WorkerJobService;
import top.buukle.wjs .service.constants.SystemReturnEnum;
import top.buukle.wjs.entity.constants.WorkerJobEnums;
import top.buukle.wjs .service.exception.SystemException;
import top.buukle.wjs .service.util.ConvertHumpUtil;
import top.buukle.util.DateUtil;
import top.buukle.util.JsonUtil;
import top.buukle.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* @author elvin
* @description WorkerJobService实现类
*/
@Service("workerJobService")
public class WorkerJobServiceImpl implements WorkerJobService{

    @Autowired
    private WorkerJobMapper workerJobMapper;

    @Autowired
    private WorkerJobLogsService workerJobLogsService;

    @Autowired
    private CommonMapper commonMapper;

    /**
     * 分页获取列表
     * @param query 查询对象
     * @return PageResponse
     */
    @Override
    public PageResponse getPage(BaseQuery query) {
        PageHelper.startPage(((WorkerJobQuery)query).getPage(),((WorkerJobQuery)query).getPageSize());
        List<WorkerJob> list = workerJobMapper.selectByExample(this.assExampleForList(((WorkerJobQuery)query)));
        PageInfo<WorkerJob> pageInfo = new PageInfo<>(list);
        return new PageResponse.Builder().build(list,pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal());
    }

    /**
     * @description 批量删除
     * @param ids
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author elvin
     * @Date 2019/8/4
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT ,rollbackFor = Exception.class)
    public CommonResponse deleteBatch(String ids, HttpServletRequest request, HttpServletResponse response) {
        String trimIds = ids.trim();
        String[] split = trimIds.split(",");
        if(StringUtil.isEmpty(trimIds) || split.length<1){
            throw new SystemException(SystemReturnEnum.USER_BATCH_DELETE_IDS_NULL);
        }
        List<Integer> idList = JsonUtil.parseArray(JsonUtil.toJSONString(Arrays.asList(split)), Integer.class);
        WorkerJobExample workerJobExample = new WorkerJobExample();
        WorkerJobExample.Criteria criteria = workerJobExample.createCriteria();
        criteria.andIdIn(idList);
        WorkerJob workerJob = new WorkerJob();

        User operator = SessionUtil. getOperator(request, response);
        workerJob.setGmtModified(new Date());
        workerJob.setModifier(operator.getUsername());
        workerJob.setModifierCode(operator.getUserId());

        workerJob.setStatus(WorkerJobEnums.status.DELETED.value());
        workerJobMapper.updateByExampleSelective(workerJob,workerJobExample);
        return new CommonResponse.Builder().buildSuccess();
    }

    /**
     * @description 根据id查询
     * @param id
     * @return top.top.buukle.wjs .entity.WorkerJob
     * @Author elvin
     * @Date 2019/8/4
     */
    @Override
    public WorkerJob selectByPrimaryKeyForCrud(HttpServletRequest httpServletRequest, Integer id) {
        if(id == null){
            return new WorkerJob();
        }
        WorkerJob workerJob = workerJobMapper.selectByPrimaryKey(id);
        return workerJob == null ? new WorkerJob() : workerJob;
    }

    /**
     * 根据id删除记录状态数据
     * @param id 删除数据实例
     * @param request httpServletRequest
     * @param response
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT ,rollbackFor = Exception.class)
    public CommonResponse delete(Integer id, HttpServletRequest request, HttpServletResponse response){

        User operator = SessionUtil.getOperator(request, response);
        WorkerJob workerJobDB = this.selectByPrimaryKeyForCrud(request, id);
        if(null != workerJobDB.getId()){
            WorkerJobQuery workerJobForUpdate = new WorkerJobQuery();
            workerJobForUpdate.setId(id);
            workerJobForUpdate.setStatus(workerJobDB.getStatus().equals(WorkerJobEnums.status.PAUSING.value()) ? WorkerJobEnums.status.EXECUTING.value():WorkerJobEnums.status.PAUSING.value());
            if(workerJobMapper.updateByPrimaryKeySelective(this.assQueryForUpdateStatus(id, WorkerJobEnums.status.DELETED.value(),request,response)) != 1){
                throw new SystemException(SystemReturnEnum.DELETE_INFO_EXCEPTION);
            }
            try {
                WorkerJobClient.operateJob(operator.getUserId(),workerJobDB,MessageActivityEnum.DELETE);
            } catch (Exception e) {
                throw new CommonException(BaseReturnEnum.FAILED,"zk删除任务失败,id: " + id);
            }
            // 记录操作日志
            WorkerJobQuery workerJobQuery = new WorkerJobQuery();
            BeanUtils.copyProperties(workerJobDB,workerJobQuery);
            workerJobLogsService.log(operator,MessageActivityEnum.DELETE,workerJobQuery);
            return new CommonResponse.Builder().buildSuccess();
        }else{
            throw new CommonException(BaseReturnEnum.FAILED,"操作任务失败,查询不到该任务,id:" + StringUtil.EMPTY);
        }
    }

    /**
     * @description 新增或者修改
     * @param query
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author elvin
     * @Date 2019/8/5
     */
    @Override
    @Transactional (propagation = Propagation.REQUIRED,isolation= Isolation.READ_UNCOMMITTED ,rollbackFor = Exception.class)
    public CommonResponse saveOrEdit(WorkerJobQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        validateParamForSaveOrEdit(query);
        User operator = SessionUtil.getOperator(request, response);
        query.setCreatorRoleId(SessionUtil.getUserTopRoleLevel(request, SpringContextUtil.getBean(Environment.class).getProperty("spring.application.name")));
        query.setCreator(operator.getUsername());
        query.setCreatorCode(operator.getUserId());
        // 新增
        if(query.getId() == null){
            this.save(query, request, response);
            // 更新任务节点
            WorkerJob workerJobDB = this.selectByPrimaryKeyForCrud(request,query.getId());
            WorkerJobClient.operateJob(operator.getUserId(),workerJobDB,MessageActivityEnum.INIT);

        }
        // 更新
        else{
            int i = workerJobMapper.updateByPrimaryKeySelective(query);
            if(i == 1){
                WorkerJob workerJobDB = this.selectByPrimaryKeyForCrud(request,query.getId());
                WorkerJobClient.operateJob(operator.getUserId(),workerJobDB,MessageActivityEnum.UPDATE);
            }else{
                throw new CommonException(BaseReturnEnum.FAILED,"操作任务失败,任务更新结果异常,id:" + query.getId() + StringUtil.EMPTY + " 条数 " + i);
            }
        }
        // 记录操作日志
        workerJobLogsService.log(operator,MessageActivityEnum.UPDATE,query);
        return new CommonResponse.Builder().buildSuccess();
    }

    /**
     * @description 暂停或启动任务
     * @param query
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/12/5
     */
    @Override
    @Transactional (propagation = Propagation.REQUIRED,isolation= Isolation.READ_UNCOMMITTED ,rollbackFor = Exception.class)
    public CommonResponse pauseOrResume(WorkerJobQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User operator = SessionUtil.getOperator(request, response);
        WorkerJob workerJobDB = this.selectByPrimaryKeyForCrud(request, query.getId());
        MessageActivityEnum messageActivityEnum;
        if(null != workerJobDB.getId()){
            WorkerJobQuery workerJobForUpdate = new WorkerJobQuery();
            workerJobForUpdate.setId(query.getId());
            workerJobForUpdate.setStatus(workerJobDB.getStatus().equals(WorkerJobEnums.status.PAUSING.value()) ? WorkerJobEnums.status.EXECUTING.value():WorkerJobEnums.status.PAUSING.value());
            messageActivityEnum = workerJobDB.getStatus().equals(WorkerJobEnums.status.PAUSING.value()) ? MessageActivityEnum.RESUME : MessageActivityEnum.PAUSE;
            this.update(workerJobForUpdate,request,response);
            // 隔离级别为 READ_UNCOMMITTED ,所以这次查询回来记录状态应该是 最新的状态
            workerJobDB = this.selectByPrimaryKeyForCrud(request, query.getId());
            WorkerJobClient.operateJob(operator.getUserId(),workerJobDB,messageActivityEnum);
            // 记录操作日志
            workerJobLogsService.log(operator,messageActivityEnum,query);
            return new CommonResponse.Builder().buildSuccess();
        }else{
            throw new CommonException(BaseReturnEnum.FAILED,"操作任务失败,查询不到该任务,id:" + query.getId() + StringUtil.EMPTY);
        }
    }

    /**
     * @description 开启任务
     * @param query
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/12/5
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT ,rollbackFor = Exception.class)
    public CommonResponse init(WorkerJobQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User operator = SessionUtil.getOperator(request, response);
        WorkerJob workerJobDB = this.selectByPrimaryKeyForCrud(request, query.getId());
        if(null != workerJobDB.getId()){
            WorkerJobQuery workerJobForUpdate = new WorkerJobQuery();
            workerJobForUpdate.setId(query.getId());
            workerJobForUpdate.setStatus(WorkerJobEnums.status.EXECUTING.value());
            this.update(workerJobForUpdate,request,response);
            WorkerJobClient.operateJob(operator.getUserId(),workerJobDB,MessageActivityEnum.INIT);
            // 记录操作日志
            workerJobLogsService.log(operator,MessageActivityEnum.INIT,query);
            return new CommonResponse.Builder().buildSuccess();
        }else{
            throw new CommonException(BaseReturnEnum.FAILED,"操作任务失败,查询不到该任务,id:" + query.getId() + StringUtil.EMPTY);
        }
    }

    /**
     * 模糊搜素
     * @param text 模糊的字符
     * @param fieldName 模糊字段名
     * @return FuzzyResponse
     */
    @Override
    public FuzzyResponse fuzzySearch(String text, String fieldName) {
        FuzzyVo fuzzyVo = new FuzzyVo();
        fuzzyVo.setText(text);
        fuzzyVo.setFieldName(fieldName);
        fuzzyVo.setTableName(ConvertHumpUtil.humpToLine("WorkerJob"));
        return new FuzzyResponse.Builder().build(commonMapper.fuzzySearch(fuzzyVo));
    }

    /**
     * 保存记录
     * @param query  查询实体
     * @param request httpServletRequest
     * @param response
     * @return
     */
    @Override
    @Transactional (propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT ,rollbackFor = Exception.class)
    public CommonResponse save(BaseQuery query, HttpServletRequest request, HttpServletResponse response) {
        WorkerJob workerJob = this.assQueryForInsert((WorkerJobQuery) query, request, response);
        workerJobMapper.insert(workerJob);
        CommonResponse commonResponse = new CommonResponse.Builder().buildSuccess();
        commonResponse.setBody(workerJob);
        return commonResponse;
    }

    /**
     * 更新记录
     * @param query
     * @param request
     * @param response
     * @return
     */
    @Override
    @Transactional (propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public CommonResponse update(BaseQuery query, HttpServletRequest request, HttpServletResponse response) {
        WorkerJobQuery workerJobQuery = ((WorkerJobQuery)query);

        WorkerJobExample example = new WorkerJobExample();
        WorkerJobExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(workerJobQuery.getId());
        User operator = SessionUtil. getOperator(request, response);
        workerJobQuery.setGmtModified(new Date());
        workerJobQuery.setModifier(operator.getUsername());
        workerJobQuery.setModifierCode(operator.getUserId());
        workerJobMapper.updateByExampleSelective(workerJobQuery,example);
        return new CommonResponse.Builder().buildSuccess();
    }

    /**
     * @description 校验参数 saveOrEdit
     * @param query
     * @return void
     * @Author elvin
     * @Date 2019/8/5
     */
    private void validateParamForSaveOrEdit(WorkerJobQuery query) {
        // TODO
    }

    /**
     * 组装新增实体
     * @param query
     * @param request
     * @param response
     * @return
     */
    private WorkerJob assQueryForInsert(WorkerJobQuery query, HttpServletRequest request, HttpServletResponse response) {
        this.validateParamForSaveOrEdit(query);
        query.setStatus(WorkerJobEnums.status.INIT.value());
        query.setGmtCreated(new Date());
        User operator = SessionUtil.getOperator(request, response);
        query.setCreator(operator.getUsername());
        query.setCreatorCode(operator.getUserId());
        query.setGmtModified(new Date());
        query.setModifier(operator.getUsername());
        query.setModifierCode(operator.getUserId());

        return query;
    }

    /**
     * 组装更新状态实体
     * @param id
     * @param status
     * @param request
     * @param response
     * @return
     */
    private WorkerJob assQueryForUpdateStatus(Integer id, Integer status, HttpServletRequest request, HttpServletResponse response) {
        WorkerJobQuery infoQuery = new WorkerJobQuery();
        User operator = SessionUtil. getOperator(request, response);
        infoQuery.setId(id);
        infoQuery.setStatus(status);
        infoQuery.setGmtModified(new Date());
        infoQuery.setModifier(operator.getUsername());
        infoQuery.setModifierCode(operator.getUserId());
        return infoQuery;
    }

    /**
     * 组装分页参数
     * @param query
     * @return
     */
    private WorkerJobExample assExampleForList(WorkerJobQuery query) {
        WorkerJobExample example = new WorkerJobExample();
        WorkerJobExample.Criteria criteria = example.createCriteria();
        if(StringUtil.isNotEmpty(query.getStartTime())){
            criteria.andGmtCreatedGreaterThanOrEqualTo(DateUtil.parse(query.getStartTime()));
        }
        if(StringUtil.isNotEmpty(query.getEndTime())){
            criteria.andGmtCreatedLessThanOrEqualTo(DateUtil.parse(query.getEndTime()));
        }
        if(StringUtil.isNotEmpty(query.getBak01())){
            criteria.andBak01EqualTo(query.getBak01());
        }
        if(null != query.getApplicationId()){
            criteria.andApplicationIdEqualTo(query.getApplicationId());
        }
        if(null != query.getStatus()){
            criteria.andStatusEqualTo(query.getStatus());
        }
        if(query.getId() != null){
            criteria.andIdEqualTo(query.getId());
        }
        if(StringUtil.isNotEmpty(query.getStates())){
            List list = new ArrayList();
            for (String state : query.getStates().split(",")) {
                list.add(Integer.parseInt(state));
            }
            criteria.andStatusIn(list);
        }
        criteria.andStatusNotEqualTo(StatusConstants.DELETED);
        example.setOrderByClause("gmt_modified desc");
        return example;
    }
}