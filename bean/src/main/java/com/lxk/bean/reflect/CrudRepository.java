package com.lxk.bean.reflect;

/**
 * @author LiXuekai on 2021/10/28
 */
public interface CrudRepository<T> {
    Iterable<T> findAll();
}
