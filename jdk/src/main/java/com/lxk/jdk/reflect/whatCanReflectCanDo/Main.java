package com.lxk.jdk.reflect.whatCanReflectCanDo;


import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.lxk.bean.model.Car;
import com.lxk.bean.model.Person;
import com.lxk.bean.ordering.Sort;
import org.junit.Test;

import java.util.List;

/**
 * 反射具体能干些什么的测试
 *
 * @author lxk on 2017/7/19
 */
public class Main {

    @Test
    public void sort() {
        List<Person> persons = Lists.newArrayList();
        persons.add(new Person(11, "周星驰"));
        persons.add(new Person(99, "陈世美"));
        persons.add(new Person(21, "潘金莲"));
        Ordering<Person> ordering = new Sort<Person>().orderingByStringField("name", 1);
        persons.sort(ordering);
        persons.forEach(person -> System.out.println(person.getName()));
    }

    /**
     * 通过Class实例化其他类的对象
     */
    @Test
    public void getNewInstance() {
        // 类的全路径
        String path = "com.lxk.model.Car";
        Class<?> demo = null;
        try {
            demo = Class.forName(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (demo == null) {
            return;
        }
        Car car = null;
        try {
            car = (Car) demo.newInstance();
        } catch (Exception ignore) {
        }
        if (car == null) {
            return;
        }
        car.setPrice(100);
        car.setSign("qq");
        System.out.println(car);
    }

    /**
     * 实例化Class类对象
     */
    @Test
    public void getClassObject() {
        String path = "com.lxk.model.Car";

        Class<?> demo1 = null;
        Class<?> demo2;
        Class<?> demo3;
        try {
            //一般尽量采用这种形式
            demo1 = Class.forName(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        demo2 = new Car().getClass();
        demo3 = Car.class;
        System.out.println("类名称   " + demo1.getName());
        System.out.println("类名称   " + demo2.getName());
        System.out.println("类名称   " + demo3.getName());
    }

    /**
     * 通过一个对象获得完整的包名和类名
     */
    @Test
    public void getWholePackageAndClassNameByObject() {
        Car car = new Car();
        System.out.println(car.getClass().getName());
    }


}
