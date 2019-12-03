package top.buukle.wjs .service;

import top.buukle.common.call.CommonResponse;
import top.buukle.common.mvc.BaseService;
import top.buukle.wjs .entity.vo.WorkerTaskLogsQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description WorkerTaskLogsService 接口类
*/
public interface WorkerTaskLogsService extends BaseService{

    CommonResponse saveOrEdit(WorkerTaskLogsQuery query, HttpServletRequest request, HttpServletResponse response);

}