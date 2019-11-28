package top.buukle.wjs.plugin.invoker;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import top.buukle.common.call.CommonRequest;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.plugin.quartz.quartzJobBean.threadpool.JobRunnable;

@FeignClient(name = "${wjs.server.name}")
public interface WorkerJobInvoker {

    /**
     * @description 添加app所拥有的定时任务
     * @param request
     * @return top.buukle.common.call.AppResourceResponse
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    @PostMapping(value = "${wjs.server.addApplicationJobURI}")
    PageResponse addApplicationJob(CommonRequest request);

    /**
     * @description 获取app所拥有的定时任务
     * @param request
     * @return top.buukle.common.call.AppResourceResponse
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    @PostMapping(value = "${wjs.server.getApplicationJobURI}")
    PageResponse getApplicationJob(CommonRequest request);

    /**
     * @description 更新执行次数 ++
     * @param request
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/app/retryCountIncrease")
    void retryCountIncrease(CommonRequest request);

    /**
     * @description 更新实体
     * @param request
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/app/updateWorkerJob")
    void updateWorkerJob(CommonRequest request);

}
