package top.buukle.wjs .service;

import top.buukle.common.call.CommonResponse;
import top.buukle.common.mvc.BaseService;
import top.buukle.wjs .entity.vo.WorkerJobQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description WorkerJobService 接口类
*/
public interface WorkerJobService extends BaseService{

    CommonResponse saveOrEdit(WorkerJobQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception;

    CommonResponse pauseOrResume(WorkerJobQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception;

    CommonResponse init(WorkerJobQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception;
}