package com.ironhack.ironschool;

import com.ironhack.ironschool.exception.ResourceNotFoundException;
import com.ironhack.ironschool.model.Teacher;
import com.ironhack.ironschool.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherServiceTest {

    private TeacherService teacherService;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacherService = new TeacherService();
        teacher = new Teacher("Nurlana Huseynova", 2000.0);
    }

    @Test
    void addTeacher_validData_addsTeacherToMap() {
        teacherService.addTeacher(teacher);
        assertEquals(teacher, teacherService.findTeacherById(teacher.getTeacherId()));
    }

    @Test
    void findTeacherById_teacherExists_returnsTeacher() {
        teacherService.addTeacher(teacher);
        assertEquals(teacher, teacherService.findTeacherById(teacher.getTeacherId()));
    }

    @Test
    void findTeacherById_teacherNotExists_throwsException() {
        assertThrows(ResourceNotFoundException.class, () ->
                teacherService.findTeacherById("non-existing-id")
        );
    }

    @Test
    void getAllTeachers_returnsAllTeachers() {
        Teacher t1 = new Teacher("Alice", 78);
        Teacher t2 = new Teacher("Bob", 31);

        teacherService.addTeacher(t1);
        teacherService.addTeacher(t2);

        List<Teacher> all = teacherService.getAllTeachers();
        assertEquals(2, all.size());
        assertTrue(all.contains(t1));
        assertTrue(all.contains(t2));
    }

    // Optional: showTeachers prints to console; test using System.out capture if needed
}