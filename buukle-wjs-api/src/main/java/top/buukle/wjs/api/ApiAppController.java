/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: AppRsourceController
 * Author:   elvin
 * Date:     2019/8/2 22:34
 * Description: app资源加载controller
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.wjs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.buukle.common.call.CommonRequest;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.wjs.entity.vo.WorkerJobQuery;
import top.buukle.wjs.entity.vo.WorkerTaskQuery;
import top.buukle.wjs.service.WorkerJobService;
import top.buukle.wjs.service.WorkerTaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 〈app资源加载controller〉
 * @author elvin
 * @create 2019/8/2
 * @since 1.0.0
 */
@Controller
@RequestMapping("/api/app")
public class ApiAppController {
    @Autowired
    private WorkerJobService workerJobService;
    @Autowired
    private WorkerTaskService workerTaskService;

    /**
     * @description 获取app所拥有的定时任务
     * @param request
     * @return top.buukle.common.call.AppResourceResponse
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    @RequestMapping("/getApplicationWorkerJob")
    @ResponseBody
    PageResponse getApplicationWorkerJob(@RequestBody CommonRequest<WorkerJobQuery> request){
        request.getBody().setBak01(request.getHead().getApplicationName());
        return workerJobService.getPage(request.getBody());
    }

    /**
     * @description 获取app所拥有轮询任务
     * @param request
     * @return top.buukle.common.call.PageResponse
     * @Author zhanglei1102
     * @Date 2019/11/29
     */
    @RequestMapping("/getApplicationWorker")
    @ResponseBody
    PageResponse getApplicationWorkerTask(@RequestBody CommonRequest<WorkerTaskQuery> request){
        return workerTaskService.getPage(request.getBody());
    }

    /**
     * @description 更新执行次数 ++
     * @param request
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @RequestMapping("/increaseRetryCountWorkerJob")
    @ResponseBody
    CommonResponse increaseRetryCountWorkerJob(@RequestBody CommonRequest request){

        return null;
    }

    /**
     * @description 更新WorkerJob实体
     * @param request
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @RequestMapping("/updateWorkerJob")
    @ResponseBody
    CommonResponse updateWorkerJob(@RequestBody CommonRequest<WorkerJobQuery> request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return workerJobService.update(request.getBody(),httpServletRequest,httpServletResponse);
    }
    /**
     * @description 更新WorkerTask实体
     * @param request
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @RequestMapping("/updateWorkerTask")
    @ResponseBody
    CommonResponse updateWorkerTask(@RequestBody CommonRequest<WorkerTaskQuery> request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return workerTaskService.update(request.getBody(),httpServletRequest,httpServletResponse);
    }

}