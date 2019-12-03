package top.buukle.wjs .service;

import top.buukle.common.call.CommonResponse;
import top.buukle.common.mvc.BaseService;
import top.buukle.wjs .entity.vo.WorkerJobLogsQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description WorkerJobLogsService 接口类
*/
public interface WorkerJobLogsService extends BaseService{

    CommonResponse saveOrEdit(WorkerJobLogsQuery query, HttpServletRequest request, HttpServletResponse response);

}