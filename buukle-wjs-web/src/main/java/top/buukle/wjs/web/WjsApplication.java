package top.buukle.wjs.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan({"top.buukle.wjs.dao","top.buukle.common.mvc"})
@SpringBootApplication(scanBasePackages={"top.buukle.*"})
@EnableFeignClients(basePackages = {"top.buukle.*"})
@EnableRedisHttpSession
@EnableTransactionManagement
public class WjsApplication {
    private static volatile boolean RUNNING = true;
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(WjsApplication.class, args);
        System.out.println("启动成功");
        synchronized (WjsApplication.class) {
            while (RUNNING) {
                try {
                    WjsApplication.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SpringApplication.exit(context);
                }
            }
        }
    }
}
