package com.ironhack.ironschool.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Course {
    private String courseId;
    private String name;
    private double price;
    private double moneyEarned;
    private Teacher teacher;
    private List<Student> students = new ArrayList<>();
    public Course(String name, double price) {
        this.courseId = generateCourseId();
        this.name = name;
        this.price = price;
        this.moneyEarned = 0;
    }
    public List<Student> getStudents() {
        return students;
    }
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMoneyEarned() {
        return moneyEarned;
    }

    public void setMoneyEarned(double moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String generateCourseId() {
        Random random = new Random();
        String coursePrefix = "COU";
        int number = 1000 + random.nextInt(9000);
        return coursePrefix + "-" + number;
    }


}
