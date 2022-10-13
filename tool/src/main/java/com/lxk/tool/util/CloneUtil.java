package com.lxk.tool.util;

import java.io.*;

/**
 * 深克隆util、使用序列化来实现深拷贝简单。
 * 注意：
 * 【所涉及到的所有对象都必须实现序列化接口】。
 *
 * @author LiXuekai on 2021/4/20
 */
public final class CloneUtil {

    /**
     * 对象的深度克隆，此处的对象涉及Collection接口和Map接口下对象的深度克隆
     * 利用序列化和反序列化的方式进行深度克隆对象
     *
     * @param object 待克隆的对象
     * @param <T>    待克隆对象的数据类型
     * @return 已经深度克隆过的对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepCloneObject(T object) {
        T deepClone = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);

            oos.writeObject(object);

            // 反序列化
            bais = new ByteArrayInputStream(baos.toByteArray());
            ois = new ObjectInputStream(bais);
            deepClone = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return deepClone;
    }
}
