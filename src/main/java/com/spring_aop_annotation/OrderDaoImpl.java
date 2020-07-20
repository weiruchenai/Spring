package com.spring_aop_annotation;

public class OrderDaoImpl implements OrderDao{

    @Override
    public void add() {
        System.out.println("增加商品...");
    }

    @Override
    public void delete() {
        System.out.println("删除商品...");
    }

    @Override
    public void update() {
        System.out.println("更新商品...");
    }

    @Override
    public void search() {
        System.out.println("修改商品...");
    }
}
