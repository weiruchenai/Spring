package com.spring_ioc_annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Repository(value = "userRepository")
@Scope(value = "singleton")
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    @Qualifier("userDao")
    //@Resource(name = "userDao")
    private UserDao userDao;

    @PostConstruct
    public void init(){
        System.out.println("userRepository被初始化了...");
    }

    @Override
    public void save() {
        System.out.println("UserRepository的save()方法被调用了...");
        userDao.save();
    }

    @PreDestroy
    public void destroy(){
        System.out.println("userRepository被销毁了...");
    }
}
