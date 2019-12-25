package top.buukle.wjs .controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.FuzzyResponse;
import top.buukle.util.JsonUtil;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs .entity.vo.WorkerJobQuery;
import top.buukle.wjs .service.WorkerJobService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     * @description 二级页面
     * @param modelAndView
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/workerJobHome")
    public ModelAndView workerJobHome(ModelAndView modelAndView) {
        modelAndView.setViewName("workerJob/workerJobHome");
        return modelAndView;
    }
    /**
     * @description 增改页面
     * @param id
     * @param request
     * @param modelAndView
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/workerJobCrudView")
    public ModelAndView workerJobCrudView( Integer id, HttpServletRequest request, ModelAndView modelAndView) {
        Object o = workerJobService.selectByPrimaryKeyForCrud(request, id);
        modelAndView.addObject("record",o);
        modelAndView.setViewName("workerJob/workerJobCrudView");
        return modelAndView;
    }



    /**
     * 获取列表
     * @return
     * @throws Exception
     */
    @RequestMapping("/workerJobPage")
    public ModelAndView workerJobPage(WorkerJobQuery query, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("response", workerJobService.getPage(query));
        modelAndView.setViewName("workerJob/workerJobPage");
        return modelAndView;
    }

    /**
     * @description 删除单条
     * @param id
     * @param request
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/workerJobCrudJson")
    public void workerJobCrudJson( Integer id, HttpServletRequest request,HttpServletResponse response) throws IOException {
        workerJobService.selectByPrimaryKeyForCrud(request,id);
        CommonResponse delete = workerJobService.delete(id, request, response);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtil.toJSONString(delete));
    }
    /**
     * @description 批量删除
     * @param request
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/workerJobBatchDeleteJson")
    public void workerJobBatchDeleteJson( String ids , HttpServletRequest request,HttpServletResponse response) throws IOException {
        CommonResponse commonResponse = workerJobService.deleteBatch(ids, request, response);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtil.toJSONString(commonResponse));
    }

    /**
     * @description 模糊搜索
     * @param text
     * @param fieldName
     * @param request
     * @return top.buukle.common.call.FuzzyResponse
     * @Author elvin
     * @Date 2019/8/4
     */
    @RequestMapping("/fuzzy/search")
    @ResponseBody
    public FuzzyResponse fuzzySearch(String text, String fieldName, HttpServletRequest request) {
        return workerJobService.fuzzySearch(text, fieldName);
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