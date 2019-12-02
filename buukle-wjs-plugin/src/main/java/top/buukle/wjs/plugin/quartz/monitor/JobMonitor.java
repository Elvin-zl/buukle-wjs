package top.buukle.wjs.plugin.quartz.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.buukle.util.JsonUtil;
import top.buukle.wjs.plugin.quartz.quartzJobBean.threadpool.JobRunnable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class JobMonitor {

    private JobMonitor(){}

    public static final JobMonitor instance = new JobMonitor();

    private static final Logger logger = LoggerFactory.getLogger(JobMonitor.class);

    private static final ConcurrentHashMap<JobRunnable,Future> map = new ConcurrentHashMap<>();

    static {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            int size = map.size();
            if(size>0){
                for(ConcurrentHashMap.Entry<JobRunnable,Future> entry:map.entrySet()){
                    JobRunnable runnable = entry.getKey();
                    long interval = (System.currentTimeMillis() - runnable.getStartTime());
                    long timeOut = runnable.getTimeOut().equals(0) ? 10 : runnable.getTimeOut();
                    if(interval > timeOut * 1_000){
                        if(!entry.getValue().isDone()){
                            logger.error("线程超时将被取消，任务为:{}", JsonUtil.toJSONString(runnable));
                            try {
                                entry.getValue().cancel(true);
                            }catch (Exception e){
                                logger.error("线程超时将被取消异常，任务为:{}",JsonUtil.toJSONString(runnable));
                                Thread.currentThread().interrupt();
                            }
                        }
                        map.remove(runnable);
                    }
                }
            }
        },3,1, TimeUnit.SECONDS);
    }

    public void put(JobRunnable jobRunnable, Future future){
        map.put(jobRunnable,future);
    }

    public Future  get(JobRunnable jobRunnable){
        return map.get(jobRunnable);
    }

    public boolean exsits(JobRunnable jobRunnable){
        return map.contains(jobRunnable);
    }

}
