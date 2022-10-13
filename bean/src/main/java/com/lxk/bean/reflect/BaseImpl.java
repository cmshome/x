package com.lxk.bean.reflect;

import java.lang.reflect.ParameterizedType;

/**
 * @author LiXuekai on 2021/10/28
 */
public class BaseImpl<T> implements CrudRepository<T> {

    @Override
    public Iterable<T> findAll() {
        Class<T> c = getTClass();
        System.out.println(c);

        common(c);
        return null;
    }

    public Iterable<T> findAll(Class<T> c) {
        System.out.println(c);

        common(c);
        return null;
    }

    /**
     * 通用的方法使用泛型的class去干一些事情。
     *
     * @param c 泛型类
     */
    private void common(Class<T> c) {

    }


    /**
     * 获取 T.class
     *
     * @return T.class
     */
    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
