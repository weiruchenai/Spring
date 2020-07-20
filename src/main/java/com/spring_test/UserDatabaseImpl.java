package com.spring_test;

public class UserDatabaseImpl implements UserService{
    @Override
    public void save() {
        System.out.println("UserDatabaseImpl调用了...");
    }

}
