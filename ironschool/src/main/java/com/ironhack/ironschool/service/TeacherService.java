package com.ironhack.ironschool.service;

import com.ironhack.ironschool.model.Teacher;
import com.ironhack.ironschool.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherService {

    private Map<String, Teacher> teachers = new HashMap<>();
    public void addTeacher(Teacher teacher) {
        teachers.put(teacher.getTeacherId(), teacher);
    }

    public Teacher findTeacherById(String teacherId) {
        Teacher teacher = teachers.get(teacherId);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher not found");
        }
        return teacher;
    }
    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teachers.values());
    }
    public void showTeachers() {
        for (Teacher t : teachers.values()) {
            System.out.println(t);
        }
    }
}