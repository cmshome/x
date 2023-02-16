package com.lxk.jdk.clazz.load;

import org.junit.Test;

/**
 * @author lxk on 2018/3/15
 */
public class TestMyClassLoader {

    @Test
    public void load() {
        MyClassLoader classLoader = new MyClassLoader();
        classLoader.setRoot("d:Student.class");

        Class<?> testClass;
        try {
            testClass = classLoader.loadClass("com/lxk/model/Student");
            Object object = testClass.newInstance();
            System.out.println(object.getClass().getClassLoader());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
