package top.buukle.wjs .controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.buukle.common.call.CommonResponse;
import top.buukle.wjs .entity.vo.WorkerJobQuery;
import top.buukle.wjs .service.WorkerJobService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description  WorkerJob controller
*/
@Controller
@RequestMapping("/workerJob")
public class WorkerJobController {

    @Autowired
    private WorkerJobService workerJobService;

    /**
    * 获取列表
    * @return
    * @throws Exception
    */
    @RequestMapping("/{pageName}")
    public ModelAndView getPage(@PathVariable("pageName") String pageName, WorkerJobQuery query, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("response", workerJobService.getPage(query));
        modelAndView.setViewName("workerJob/" + pageName);
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
    public CommonResponse saveOrEdit(WorkerJobQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return workerJobService.saveOrEdit(query,request,response);
    }
    /**
    * @description 暂停或恢复
    * @param query
    * @param request
    * @return top.buukle.common.call.CommonResponse
    * @Author elvin
    * @Date 2019/8/5
    */
    @RequestMapping("/pauseOrResume")
    @ResponseBody
    public CommonResponse pauseOrResume(WorkerJobQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return workerJobService.pauseOrResume(query,request,response);
    }
    /**
    * @description 开启任务
    * @param query
    * @param request
    * @return top.buukle.common.call.CommonResponse
    * @Author elvin
    * @Date 2019/8/5
    */
    @RequestMapping("/init")
    @ResponseBody
    public CommonResponse init(WorkerJobQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return workerJobService.init(query,request,response);
    }
}