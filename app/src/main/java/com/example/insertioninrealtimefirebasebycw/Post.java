package com.example.insertioninrealtimefirebasebycw;

public class Post {
    String age;
    String name;
    String sal;

    public Post() {
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        return "Post{" +
                "age='" + age + '\'' +
                ", name='" + name + '\'' +
                ", sal='" + sal + '\'' +
                '}';
    }
}
