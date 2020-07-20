package com.spring_test;

public class UserServiceImpl implements UserService{
    private String name;
    public UserServiceImpl(String name){
        this.name = name;
    }
    public UserServiceImpl(){}

    @Override
    public void save() {
        System.out.println("UserServiceImpl调用了...");
    }

    @Override
    public String toString() {
        return "UserServiceImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
