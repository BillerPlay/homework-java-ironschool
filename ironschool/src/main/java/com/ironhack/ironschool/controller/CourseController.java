package com.ironhack.ironschool.controller;

import com.ironhack.ironschool.model.Course;
import com.ironhack.ironschool.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(course));
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable String courseId) {
        return courseService.findCourseById(courseId);
    }

    @PostMapping("/{courseId}/students/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public void enrollStudent(@PathVariable String courseId,
                              @PathVariable String studentId) {
        courseService.enrollStudent(courseId, studentId);
    }

    @PostMapping("/{courseId}/teachers/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public void assignTeacher(@PathVariable String courseId,
                              @PathVariable String teacherId) {
        courseService.assignTeacher(courseId, teacherId);
    }

    @GetMapping("/total-earned")
    public double getTotalEarned() {
        return courseService.getTotalEarned();
    }
}