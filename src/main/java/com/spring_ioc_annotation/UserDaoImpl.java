package com.spring_ioc_annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "userDao")
public class UserDaoImpl implements UserDao{

    @Value("gdx3")
    private String name;

    @Override
    public void save() {
        System.out.println("UserDaoImpl的save()方法被调用了..." + name);
    }
}
