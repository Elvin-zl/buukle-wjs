package top.buukle.wjs.plugin.zk;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import top.buukle.wjs.plugin.zk.listener.impl.ApplicationListener;
import top.buukle.wjs.plugin.zk.listener.impl.TestListener;

import java.util.List;

public class ZkTest {
    static  int CLINET_COUNT = 6;
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

    /**
     * @description 测试多次调用
     * @param
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/29
     */
    @Test
    public void testMultiCall() throws Exception {
        CuratorFramework zkClient = getZkClient();
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/elvin.buukle.test","222".getBytes());
        zkClient.delete().deletingChildrenIfNeeded().forPath("/elvin.buukle.test");
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/elvin.buukle.test","1".getBytes());
        System.out.println("111");
    }


    /**
     * @description 测试订阅父节点,更改子节点的效果
     * @param
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/29
     */
    @Test
    public void testSubscr() throws Exception {
        String  parentPath = "/elvin.buukle.testp";
        CuratorFramework zkc = getZkClient();
        // 不存在则创建
        if(!ZkOperator.checkExists(zkc,parentPath)){
            try{
                ZkOperator.createAndInitParentsIfNeededPersistent(zkc,parentPath,"INIT_ZK_VALUE".getBytes());
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("创建应用父节点时出现异常");
            }
        }
        // 订阅应用父节点
        ZkOperator.subscribe(zkc,new TestListener(parentPath,"test"));

        for (int i = 0; i < CLINET_COUNT; i++) {
            CuratorFramework zkClient = getZkClient();
            ZkOperator.persistEphemeral(zkClient,parentPath + "/ss" + i,"222".getBytes());
        }

        // 由于订阅是异步的,有延时,故需睡5秒
        Thread.sleep(5000);
        // 查询应用子节点
        List<String> appChildrenNode = ZkOperator.getChildren(zkc,parentPath);
            for (String child: appChildrenNode) {
                System.out.println("######################################" +ZkOperator.getData(zkc, parentPath + "/" + child));
            }
        System.out.println("================");
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
                .sessionTimeoutMs(50000000)
                .connectionTimeoutMs(50000000)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        return zkClient;
    }
}