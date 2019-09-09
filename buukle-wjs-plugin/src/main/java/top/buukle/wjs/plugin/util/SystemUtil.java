package top.buukle.wjs.plugin.util;


import org.jboss.netty.util.NetUtil;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @description 
 * @Author zhanglei1102
 * @Date 2019/9/9
 */
public class SystemUtil {


    public final static String PID = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

    public static String ipPid() {
        return getIp()+"_"+PID;
    }
    public static String getIp() {
        try {
            Enumeration netInterfaces= NetworkInterface.getNetworkInterfaces();
            InetAddress   ip   =   null;
            while(netInterfaces.hasMoreElements())
            {
                NetworkInterface   ni=(NetworkInterface)netInterfaces.nextElement();
                System.out.println(ni.getName());
                ip=(InetAddress)   ni.getInetAddresses().nextElement();
                if(   !ip.isSiteLocalAddress()
                        &&   !ip.isLoopbackAddress()
                        &&   ip.getHostAddress().indexOf( ": ")==-1)
                {
                    System.out.println( "本机的ip= "   +   ip.getHostAddress());
                    break;
                }
                else
                {
                    ip=null;
                }
            }
            return  ip.getHostAddress();
        } catch (Exception e) {
            return "127.0.0.1";
        }
    }
}
