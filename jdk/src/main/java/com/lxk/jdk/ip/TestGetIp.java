package com.lxk.jdk.ip;

import org.junit.Test;

import java.net.InetAddress;

/**
 * @author LiXuekai on 2023/10/18
 */
public class TestGetIp {

    @Test
    public void ip() {
        String localIp = getLocalIp();
        System.out.println(localIp);
    }

    /**
     * 本机IP
     */
    private static String getLocalIp() {
        InetAddress localHost;
        try {
            localHost = InetAddress.getLocalHost();
            return localHost == null ? null : localHost.getHostAddress();
        } catch (Exception ignore) {
        }
        return null;
    }
}
