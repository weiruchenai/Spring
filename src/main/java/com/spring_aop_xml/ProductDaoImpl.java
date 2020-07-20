package com.spring_aop_xml;

public class ProductDaoImpl implements ProductDao{
    @Override
    public void save() {
        System.out.println("商品被保存");
    }

    @Override
    public void delete() {
        System.out.println("商品被删除");
    }

    @Override
    public void update() {
        System.out.println("商品被添加");
        //int i = 1 / 0;
    }

    @Override
    public String search() {
        System.out.println("商品被查询");
        return "gdx111";
    }
}
