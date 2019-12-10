package top.buukle.wjs.plugin.invoker;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.buukle.common.call.CommonRequest;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.PageResponse;

@FeignClient(name = "${wjs.server.name}")
public interface WorkerJobInvoker {

    /**
     * @description 获取app所拥有的定时任务
     * @param request
     * @return top.buukle.security.entity.common.AppResourceResponse
     * @Author zhanglei1102
     * @Date 2019/9/9
     */
    @PostMapping(value = "/api/app/getApplicationWorkerJob")
    PageResponse getApplicationWorkerJob(@RequestBody CommonRequest request);

    /**
     * @description 获取app所拥有轮询任务
     * @param request
     * @return top.buukle.common.call.PageResponse
     * @Author zhanglei1102
     * @Date 2019/11/29
     */
    @PostMapping(value = "/api/app/getApplicationWorker")
    PageResponse getApplicationWorkerTask(@RequestBody CommonRequest request);

    /**
     * @description 更新执行次数 ++
     * @param request
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/app/increaseRetryCountWorkerJob")
    CommonResponse increaseRetryCountWorkerJob(@RequestBody CommonRequest request);

    /**
     * @description 更新实体
     * @param request
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/app/updateWorkerJob")
    CommonResponse updateWorkerJob(@RequestBody CommonRequest request);

}
