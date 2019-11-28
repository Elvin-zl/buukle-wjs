package top.buukle.wjs .controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.buukle.common.call.CommonResponse;
import top.buukle.wjs .entity.vo.WorkerTaskLogsQuery;
import top.buukle.wjs .service.WorkerTaskLogsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description  WorkerTaskLogs controller
*/
@Controller
@RequestMapping("/workerTaskLogs")
public class WorkerTaskLogsController {

    @Autowired
    private WorkerTaskLogsService workerTaskLogsService;

    /**
    * 获取列表
    * @return
    * @throws Exception
    */
    @RequestMapping("/{pageName}")
    public ModelAndView getPage(@PathVariable("pageName") String pageName, WorkerTaskLogsQuery query, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("response", workerTaskLogsService.getPage(query));
        modelAndView.setViewName("workerTaskLogs/" + pageName);
        return modelAndView;
    }

    /**
    * @description 新增或者修改
    * @param query
    * @param request
    * @return top.buukle.common.call.CommonResponse
    * @Author elvin
    * @Date 2019/8/5
    */
    @RequestMapping("/saveOrEdit")
    @ResponseBody
    public CommonResponse saveOrEdit(WorkerTaskLogsQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return workerTaskLogsService.saveOrEdit(query,request,response);
    }
}