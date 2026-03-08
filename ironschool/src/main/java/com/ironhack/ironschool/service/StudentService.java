package com.ironhack.ironschool.service;

import com.ironhack.ironschool.exception.ResourceNotFoundException;
import com.ironhack.ironschool.model.Course;
import com.ironhack.ironschool.model.Student;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    public final CourseService courseService;
    public StudentService(CourseService courseService){
        this.courseService = courseService;
    }
    private Map<String, Student> students = new HashMap<>();

    public void addStudent(Student student) {
        students.put(student.getStudentId(), student);
    }

    public Student findStudentById(String studentId) {
        Student student = students.get(studentId);
        if (student == null) {
            throw new ResourceNotFoundException("Student not found");
        }
        return student;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public void showStudents() {
        for (Student s : students.values()) {
            System.out.println(s);
        }
    }

    public void enrollStudentToCourse(String studentId, String courseId){
        Student student = students.get(studentId);
        student.setCourse(courseService.getCourse(courseId));

        courseService.addMoney(courseId);
    }

    public void lookupStudent(String studentId){

        Student student = students.get(studentId);

        if(student == null){
            System.out.println("Student not found");
            return;
        }

        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Name: " + student.getName());
        System.out.println("Address: " + student.getAddress());
        System.out.println("Email: " + student.getEmail());
        if(student.getCourse() != null){
            System.out.println("Course: " + student.getCourse().getName());
        } else {
            System.out.println("Course: None");
        }
    }
}