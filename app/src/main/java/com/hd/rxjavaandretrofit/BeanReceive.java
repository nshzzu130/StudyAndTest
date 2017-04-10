package com.hd.rxjavaandretrofit;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by hudong on 2017/3/28.
 */
public class BeanReceive {
    /**
     * id : 1001
     * name : zhangsan
     * age : 18
     * sex : false
     * children : [{"id":0,"name":"","age":0,"sex":false,"children":null},{"id":0,"name":"","age":0,"sex":false,"children":null},{"id":0,"name":"","age":0,"sex":false,"children":null}]
     */
    private int id;
    private String name;
    private int age;
    private boolean sex;
    private List<BeanReceive> children;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setChildren(List<BeanReceive> children) {
        this.children = children;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean getSex() {
        return sex;
    }

    public List<BeanReceive> getChildren() {
        return children;
    }

    public String toString(){
        return new Gson().toJson(this);
    }
}
