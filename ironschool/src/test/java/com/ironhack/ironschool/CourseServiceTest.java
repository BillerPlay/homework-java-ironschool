package com.ironhack.ironschool;

import com.ironhack.ironschool.exception.ResourceNotFoundException;
import com.ironhack.ironschool.model.Course;
import com.ironhack.ironschool.model.Student;
import com.ironhack.ironschool.model.Teacher;
import com.ironhack.ironschool.service.CourseService;
import com.ironhack.ironschool.service.StudentService;
import com.ironhack.ironschool.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    private CourseService courseService;
    private TeacherService teacherService;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        teacherService = mock(TeacherService.class);
        studentService = mock(StudentService.class);
        courseService = new CourseService(teacherService, studentService);
    }

    @Test
    void addCourse_shouldAddCourse() {
        Course course = new Course("Java", 100);

        courseService.addCourse(course);

        assertEquals(course, courseService.getCourses().get(course.getCourseId()));
    }

    @Test
    void findCourseById_shouldReturnCourse() {
        Course course = new Course("Java", 100);
        courseService.addCourse(course);

        Course result = courseService.findCourseById(course.getCourseId());

        assertEquals("Java", result.getName());
    }

    @Test
    void findCourseById_shouldThrowExceptionIfNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            courseService.findCourseById("wrong-id");
        });
    }

    @Test
    void enrollStudent_shouldIncreaseMoneyEarned() {

        Course course = new Course("Java", 100);
        Student student = new Student("Mike","sdjkfn","miko@gmail.com");

        courseService.addCourse(course);

        when(studentService.findStudentById(student.getStudentId())).thenReturn(student);

        courseService.enrollStudent(course.getCourseId(), student.getStudentId());

        assertEquals(100, course.getMoneyEarned());
    }

    @Test
    void assignTeacher_shouldAssignTeacher() {

        Course course = new Course("Java", 100);
        Teacher teacher = new Teacher("John",500);

        courseService.addCourse(course);

        when(teacherService.findTeacherById(teacher.getTeacherId())).thenReturn(teacher);

        courseService.assignTeacher(course.getCourseId(), teacher.getTeacherId());

        assertEquals("John", course.getTeacher().getName());
    }

    @Test
    void addMoney_shouldIncreaseMoneyEarned() {

        Course course = new Course("Java", 100);
        courseService.addCourse(course);

        courseService.addMoney(course.getCourseId());

        assertEquals(100, course.getMoneyEarned());
    }

    @Test
    void getTotalEarned_shouldReturnCorrectTotal() {

        Course c1 = new Course("Java", 100);
        Course c2 = new Course("Spring", 200);

        courseService.addCourse(c1);
        courseService.addCourse(c2);

        courseService.addMoney(c1.getCourseId());
        courseService.addMoney(c2.getCourseId());

        double total = courseService.getTotalEarned();

        assertEquals(300, total);
    }

    @Test
    void getAllCourses_shouldReturnAllCourses() {

        Course c1 = new Course("Java", 100);
        Course c2 = new Course("Spring", 200);

        courseService.addCourse(c1);
        courseService.addCourse(c2);

        List<Course> courses = courseService.getAllCourses();

        assertEquals(2, courses.size());
    }
}