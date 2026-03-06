package com.ironhack.ironschool.service;

import com.ironhack.ironschool.exception.ResourceNotFoundException;
import com.ironhack.ironschool.model.Student;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

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
}