package com.ironhack.ironschool.service;

import com.ironhack.ironschool.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student){
        students.add(student);
    }

    public Student findStudentById(String studentId){
        for(Student s : students){
            if(s.getStudentId().equals(studentId)){
                return s;
            }
        }
        return null;
    }

    public List<Student> getAllStudents(){
        return students;
    }

    public void showStudents(){
        for(Student s : students){
            System.out.println(s);
        }
    }
}