package com.spring_test;

public class NameDao2 {
    private NameDaoImpl nameDaoImpl;

    public void setNameDaoImpl(NameDaoImpl nameDaoImpl) {
        this.nameDaoImpl = nameDaoImpl;
    }

    @Override
    public String toString() {
        return "NameDao2{" +
                "nameDaoImpl=" + nameDaoImpl +
                '}';
    }
}
