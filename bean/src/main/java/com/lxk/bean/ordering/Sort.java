package com.lxk.bean.ordering;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.List;
import java.util.Locale;

/**
 * 还能继续优化
 *
 * @author LiXuekai on 2021/12/30
 */
public class Sort<T> {
    Collator COLLATOR = Collator.getInstance(Locale.CHINA);

    /**
     * 反射 + 泛型 实现的通用比较器
     *
     * @param fieldName 排序的属性名称
     * @param type      升序还是降序
     * @return 比较器
     */
    public Ordering<T> orderingByStringField(String fieldName, int type) {
        return new Ordering<T>() {
            @SneakyThrows
            @Override
            public int compare(T left, T right) {
                if (left == null && right == null) {
                    return 0;
                }
                if (left == null) {
                    return 1;
                }
                if (right == null) {
                    return -1;
                }
                String lv = String.valueOf(valueByMethod(left, fieldName));
                String rv = String.valueOf(valueByField(right, fieldName));
                return type > 0 ? COLLATOR.compare(lv, rv) : COLLATOR.compare(rv, lv);
            }
        };
    }

    /**
     * 通过属性直接获取实例对象的属性值
     *
     * @param t         实例对象
     * @param fieldName 排序的属性名称
     */
    private Object valueByField(T t, String fieldName) {
        Class<?> clazz = t.getClass();
        // 获取当前对象的所有属性字段
        // clazz.getFields()：获取public修饰的字段
        // clazz.getDeclaredFields()： 获取所有的字段包括private修饰的字段
        List<Field> allFields = Lists.newArrayList(clazz.getDeclaredFields());

        // 获取所有父类的字段， 父类中的字段需要逐级获取
        Class clazzSuper = clazz.getSuperclass();

        // 如果父类不是object，表明其继承的有其他类。 逐级获取所有父类的字段
        while (clazzSuper != Object.class) {
            allFields.addAll(Lists.newArrayList(clazzSuper.getDeclaredFields()));
            clazzSuper = clazzSuper.getSuperclass();
        }

        for (Field field : allFields) {
            // 设置字段可访问， 否则无法访问private修饰的变量值
            field.setAccessible(true);
            try {
                // 获取字段名称
                if (field.getName().equals(fieldName)) {
                    // 获取指定对象的当前字段的值
                    return field.get(t);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过属性直接获取实例对象的属性值
     *
     * @param t         实例对象
     * @param fieldName 排序的属性名称
     */
    private Object valueByMethod(T t, String fieldName) {
        Method[] methods = t.getClass().getMethods();
        String s = "get" + fieldName;
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase(s)) {
                try {
                    return method.invoke(t);
                } catch (Exception e) {
                }
            }
        }
        return null;
    }


}
