package com.spring_test;

public class NameDaoImpl implements NameDao{
    public String name;
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void save() {
        System.out.println("NameDao方法被调用了:" + name);
    }

    @Override
    public void setup() {
        System.out.println("NameDao初始化...");
    }

    @Override
    public void destroy() {
        System.out.println("NameDao被销毁");
    }

    @Override
    public String toString() {
        return "NameDaoImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
