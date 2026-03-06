package com.ironhack.ironschool.model;

import java.util.Random;

public class Teacher {
    private String teacherId;
    private String name;
    private double salary;

    public Teacher(String name, double salary) {
        this.teacherId = generateTeacherId();
        this.name = name;
        this.salary = salary;
    }

    public String generateTeacherId() {
        Random random = new Random();
        String coursePrefix = "TEA";
        int number = 1000 + random.nextInt(9000);
        return coursePrefix + "-" + number;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
