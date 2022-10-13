package com.lxk.jdk.ip;

import com.lxk.tool.util.Tools;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 * @author LiXuekai on 2021/4/28
 */
public class IP4Test {
    private static final String ip = "255.255.255.255";

    @Test
    public void test() throws UnknownHostException {
        long ipLong = Tools.getIpLong(ip);
        System.out.println(ipLong);
        System.out.println((Long.toBinaryString(ipLong)));
        //11111111 11111111 11111111 11111111
        System.out.println(Tools.longToIP(ipLong));
    }
}
