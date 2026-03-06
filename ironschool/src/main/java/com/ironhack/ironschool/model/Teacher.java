package com.ironhack.ironschool.model;
import java.util.Random;
import java.util.UUID;

public class Teacher {

    private String teacherId;
    private String name;
    private double salary;

    public Teacher(String name, double salary) {
        teacherId=generateTeacherId();
        this.name = name;
        setSalary(salary);
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
    public String generateTeacherId() {
        Random random = new Random();
        String coursePrefix = "TEA";
        int number = 1000 + random.nextInt(9000);
        return coursePrefix + "-" + number;
    }
}