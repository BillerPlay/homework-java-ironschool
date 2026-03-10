package com.ironhack.ironschool.model;


import java.util.Random;

public class Student {
    private String studentId;
    private String name;
    private String address;
    private String email;
    private Course course;

    public Student(String name, String address, String email) {
        studentId=generateStudentId();
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Course getCourse() {
        return course;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", course=" + (course != null ? course.getName() : "None") +
                '}';
    }
    public String generateStudentId() {
        Random random = new Random();
        String coursePrefix = "STU";
        int number = 1000 + random.nextInt(9000);
        return coursePrefix + "-" + number;
    }
}