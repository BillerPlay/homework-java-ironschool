package com.ironhack.ironschool.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.Random;

public class Teacher {

    private String teacherId;

    @NotBlank(message = "Teacher name cannot be blank")
    private String name;

    @Min(value = 0, message = "Salary cannot be negative")
    private double salary;

    public Teacher(String name, double salary) {
        this.teacherId = generateTeacherId();
        this.name = name;
        this.salary = salary;
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
        String teacherPrefix = "TEA";
        int number = 1000 + random.nextInt(9000);
        return teacherPrefix + "-" + number;
    }
}