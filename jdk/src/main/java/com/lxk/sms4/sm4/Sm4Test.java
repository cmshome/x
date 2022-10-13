package com.lxk.sms4.sm4;

import com.lxk.sms4.utils.sm4.SM4Utils;
import org.junit.Test;

/**
 * @author LiXuekai on 2020/9/4
 */
public class Sm4Test {

    private static final String data = "";

    @Test
    public void sm4() {
        System.out.println("CBC模式加密");

        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = "E76E9B4E0245BC56FCE4E29B208C6A50";
        sm4.hexString = true;

        String plainText = data;
        sm4.iv = "30303030303030303030303030303030";
        String cipherText = sm4.encryptData_CBC(plainText);
        System.out.println("加密密文: \n" + cipherText);
        System.out.println("");

        plainText = sm4.decryptData_CBC(cipherText);
        System.out.println("解密明文: \n" + plainText);

        System.out.println("\n" + plainText.equals(data));
        System.out.println();


        int length = data.getBytes().length;
        int length1 = cipherText.getBytes().length;
        System.out.println(length + "  " +length1 );
    }
}
