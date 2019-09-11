package top.buukle.wjs.plugin.zk;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import java.util.List;

public class LeaderLatchTest {
    static  int CLINET_COUNT = 10;
    static String LOCK_PATH = "/leader_latch";

    @Test
    public void dasd() throws Exception {
        List<CuratorFramework> clientsList = Lists.newArrayListWithCapacity(CLINET_COUNT);
        List<LeaderLatch> leaderLatchList = Lists.newArrayListWithCapacity(CLINET_COUNT);
        //创建10个zk客户端模拟leader选举
        for (int i = 0; i < CLINET_COUNT; i++) {
            CuratorFramework client = getZkClient();
            clientsList.add(client);
            ZkOperator.leaderLatch(client,LOCK_PATH,leaderLatchList);
        }
        //判断当前leader是哪个客户端
        checkLeader(leaderLatchList);
    }

    private static void checkLeader(List<LeaderLatch> leaderLatchList) throws Exception {
        //Leader选举需要时间 等待10秒
        Thread.sleep(3000);
        for (int i = 0; i < leaderLatchList.size(); i++) {
            LeaderLatch leaderLatch = leaderLatchList.get(i);
            //通过hasLeadership()方法判断当前节点是否是leader 
            if (leaderLatch.hasLeadership()) {
                System.out.println("当前leader:"+leaderLatch.getId());
                //释放leader权限 重新进行抢主
                leaderLatch.close();
                checkLeader(leaderLatchList);
            }
        }
    }

    private static CuratorFramework getZkClient() {
        String zkServerAddress = "39.105.74.47:2181";
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3, 5000);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString(zkServerAddress)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        return zkClient;
    }
}