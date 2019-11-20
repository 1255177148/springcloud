package com.hezhan.client.entity;

import java.util.List;

/**
 * @Author hezhan
 * @Date 2019/9/19 11:21
 */
public class User {
    private String name;
    private Integer age;
    private List<Hi> data;

    public List<Hi> getData() {
        return data;
    }

    public void setData(List<Hi> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
