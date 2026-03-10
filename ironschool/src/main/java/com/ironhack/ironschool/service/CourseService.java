package com.ironhack.ironschool.service;

import com.ironhack.ironschool.exception.ResourceNotFoundException;
import com.ironhack.ironschool.model.Course;
import com.ironhack.ironschool.model.Student;
import com.ironhack.ironschool.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {
    public final TeacherService teacherService;
    public CourseService(TeacherService teacherService){
        this.teacherService = teacherService;
    }
    private Map<String, Course> courses = new HashMap<>();
    private final TeacherService teacherService;
    private final StudentService studentService;

    public CourseService(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    public Course addCourse(Course course) {
        courses.put(course.getCourseId(), course);
        return courses.get(course.getCourseId());
    }

    public Course findCourseById(String courseId) {
        Course course = courses.get(courseId);
        if (course == null) {
            throw new ResourceNotFoundException("Course not found");
        }
        return course;
    }

    public void enrollStudent(String courseId, String studentId) {
        Student student = studentService.findStudentById(studentId);
        Course course = findCourseById(courseId);
        course.getStudents().add(student);
        addMoney(course.getCourseId());
    }

    public void assignTeacher(String courseId, String teacherId) {
        Teacher teacher = teacherService.findTeacherById(teacherId);
        Course course = findCourseById(courseId);
        course.setTeacher(teacher);
    }

    public void addMoney(String courseId) {
        Course course = findCourseById(courseId);
        course.setMoneyEarned(course.getMoneyEarned() + course.getPrice());
    }

    public void showCourses() {
        for (Course course : courses.values()) {
            System.out.println(course.getCourseId() + " - " + course.getName() + " - $" + course.getPrice());
        }
    }
    public void lookupCourse(String courseId) {
        Course course = findCourseById(courseId);
        System.out.println("Course ID: " + course.getCourseId());
        System.out.println("Name: " + course.getName());
        System.out.println("Price: $" + course.getPrice());
        System.out.println("Money Earned: $" + course.getMoneyEarned());
        System.out.println("Teacher: " + (course.getTeacher() != null ? course.getTeacher().getName() : "None"));
    }

    public double getTotalEarned() {
        double total = 0;
        for (Course course : courses.values()) {
            total += course.getMoneyEarned();
        }
        return total;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }
}