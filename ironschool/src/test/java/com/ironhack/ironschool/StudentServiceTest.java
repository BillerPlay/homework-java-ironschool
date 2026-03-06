package com.ironhack.ironschool;

import com.ironhack.ironschool.exception.ResourceNotFoundException;
import com.ironhack.ironschool.model.Student;
import com.ironhack.ironschool.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class StudentServiceTest {
    private Student student;
    private StudentService studentService;
    @BeforeEach
    void setUp(){
        student=new Student("Miko","Khirdalan 125","miko@gmail.com");
        studentService=new StudentService();
    }
    @Test
    void addStudent_validData_addStudentToMap(){
        Student student=new Student("Mikayil","Khirdalan 129","miko44@gmail.com");
        studentService.addStudent(student);
        assertEquals(student,studentService.findStudentById(student.getStudentId()));
    }
    @Test
    void findStudentById_studentNotExists_throwsException(){
        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.findStudentById("non-existing-id");
        });
    }
    @Test
    void findStudentById_studentExists_returnsData(){
        studentService.addStudent(student);
        assertEquals(student,studentService.findStudentById(student.getStudentId()));
    }
    @Test
    void getAllStudents_returnsAllStudents() {
        Student s1 = new Student("Alice", "Street 1", "alice@gmail.com");
        Student s2 = new Student("Bob", "Street 2", "bob@gmail.com");

        studentService.addStudent(s1);
        studentService.addStudent(s2);

        List<Student> all = studentService.getAllStudents();
        assertEquals(2, all.size());
        assertTrue(all.contains(s1));
        assertTrue(all.contains(s2));
    }
}
