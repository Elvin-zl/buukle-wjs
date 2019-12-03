package top.buukle.wjs.plugin.quartzJob.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @description 任务执行线程池满后，默认的拒绝策略, 重写此类
 * @Author zhanglei1102
 * @Date 2019/9/9
 */
public class JobThreadPoolDefaultRejectPolicy extends ThreadPoolExecutor.DiscardPolicy  {

    private final Logger log = LoggerFactory.getLogger(getClass());

    static final AtomicInteger rejectedLogTaskCount = new AtomicInteger(0);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        int counts = rejectedLogTaskCount.incrementAndGet();
        String msg = String.format("定时任务服务处理线程池耗尽,已经拒绝%s个：Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d), Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s), in %s!" ,
                 counts,e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
                e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating(), "");
        log.info(msg);
        //每6次打印一下
        if(counts%6==0){
            System.out.println("定时任务服务处理线程池耗尽!");
        }
        super.rejectedExecution(r, e);
    }
}
