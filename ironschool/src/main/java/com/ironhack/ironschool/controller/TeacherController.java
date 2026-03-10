package com.ironhack.ironschool.controller;

import com.ironhack.ironschool.model.Teacher;
import com.ironhack.ironschool.model.Teacher;
import com.ironhack.ironschool.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<Teacher> addCourse(@RequestBody Teacher teacher) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.addTeacher(teacher));
    }
    @GetMapping
    public List<Teacher> getTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{teacherId}")
    public Teacher getTeacher(@PathVariable String teacherId) {
        return teacherService.findTeacherById(teacherId);
    }
}