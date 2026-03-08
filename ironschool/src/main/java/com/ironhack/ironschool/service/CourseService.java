package com.ironhack.ironschool.service;

import com.ironhack.ironschool.model.Course;
import com.ironhack.ironschool.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class CourseService {
    public final TeacherService teacherService;
    public CourseService(TeacherService teacherService){
        this.teacherService = teacherService;
    }
    private Map<String, Course> courses = new HashMap<>();

    public void addCourse(Course course){
        courses.put(course.getCourseId(), course);
    }

    public void assignTeacher(String courseId, String teacherId){
        Course course = courses.get(courseId);

        if(course != null){
            course.setTeacher(teacherService.getTeacher(teacherId));
        }
    }

    public void addMoney(String courseId){
        Course course = courses.get(courseId);

        if(course != null){
            course.setMoneyEarned(
                    course.getMoneyEarned() + course.getPrice()
            );
        }
    }

    public void showCourses(){
        for(Course course : courses.values()){
            System.out.println(
                    course.getCourseId() + " " +
                            course.getName() + " " +
                            course.getPrice()
            );
        }
    }

    public void lookupCourse(String courseId){

        Course course = courses.get(courseId);

        if(course == null){
            System.out.println("Course not found");
            return;
        }

        System.out.println("Course ID: " + course.getCourseId());
        System.out.println("Name: " + course.getName());
        System.out.println("Price: " + course.getPrice());
        System.out.println("Money Earned: " + course.getMoneyEarned());

        if(course.getTeacher() != null){
            System.out.println("Teacher: " + course.getTeacher().getName());
        } else {
            System.out.println("Teacher: None");
        }
    }

    public double getTotalEarned(){

        double total = 0;

        for(Course course : courses.values()){
            total += course.getMoneyEarned();
        }

        return total;
    }

    public Map<String, Course> getCourses(){
        return courses;
    }
    public Course getCourse(String courseId){
        return courses.get(courseId);
    }
}