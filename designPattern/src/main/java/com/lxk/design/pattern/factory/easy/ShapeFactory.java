package com.lxk.design.pattern.factory.easy;

/**
 * @author LiXuekai on 2020/7/23
 */
public class ShapeFactory {
    /**
     * 使用 getShape 方法获取形状类型的对象
     */
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        }
        if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        }
        if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }
}
