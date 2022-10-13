package com.lxk.bean.model;

/**
 * @author LiXuekai on 2021/4/29
 */
public class OutClass {

    private int outClassAge;

    public void test() {
        StaticInnerClass staticInnerClass = new StaticInnerClass();

        //staticInnerClass.clone();

    }

    /**
     * 静态内部类
     */
    static class StaticInnerClass {
        private int staticInnerClassAge;


    }


    /**
     * 内部类
     */
    private class InnerClass {
        private int innerClassAge;



    }
}
