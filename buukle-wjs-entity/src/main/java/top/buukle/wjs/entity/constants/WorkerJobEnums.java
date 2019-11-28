package top.buukle.wjs.entity.constants;

/**
 * @Author: elvin
 * @Date: 2019/7/28/028 3:56
 */
public class WorkerJobEnums {

    public enum status {

        DELETED(-1,"已经删除"),
        INIT(0,"初始化"),
        HANDLING(1,"处理中"),
        REJECT(2,"审核不通过"),
        PUBLISED(3,"审核通过"),
        BAN(4,"已被封禁"),
        FAILED(5,"执行失败"),
        ;

        private Integer status;
        private String description;

        status(int status, String description) {
            this.description = description;
            this.status = status;
        }
        public String getDescription() {
            return description;
        }
        public Integer value() {
            return status;
        }
    }

    public enum FailStrategy {

        QUICK_FAIL(0,"快速失败"),
        RETRY_CONTINUE(1,"再次重试"),
        ;

        private Integer strategy;
        private String description;

        FailStrategy(int strategy, String description) {
            this.description = description;
            this.strategy = strategy;
        }

        public Integer getStrategy() {
            return strategy;
        }

        public void setStrategy(Integer strategy) {
            this.strategy = strategy;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
