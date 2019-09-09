package top.buukle.wjs.plugin.invoker;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import top.buukle.common.call.CommonRequest;
import top.buukle.common.call.PageResponse;
import top.buukle.wjs.entity.WorkerJob;

@FeignClient(name = "${wjs.server.name}")
public interface SchedulerInvoker {

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

}
