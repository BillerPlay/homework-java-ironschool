package com.ironhack.ironschool.service;

import com.ironhack.ironschool.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherService {

    private List<Teacher> teachers = new ArrayList<>();

    public void addTeacher(Teacher teacher){
        teachers.add(teacher);
    }

    public Teacher findTeacherById(String teacherId){
        for(Teacher t : teachers){
            if(t.getTeacherId().equals(teacherId)){
                return t;
            }
        }
        return null;
    }

    public List<Teacher> getAllTeachers(){
        return teachers;
    }

    public void showTeachers(){
        for(Teacher t : teachers){
            System.out.println(t);
        }
    }
}