package com.ironhack.ironschool.menu;

import com.ironhack.ironschool.service.CourseService;
import com.ironhack.ironschool.service.StudentService;
import com.ironhack.ironschool.service.TeacherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
// We implement CommandLineRunner because we need to work with console
public class ConsoleMenu implements CommandLineRunner {
    private Scanner scanner = new Scanner(System.in);
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final StudentService studentService;
    public ConsoleMenu (TeacherService teacherService,
                        CourseService courseService,
                        StudentService studentService){
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.studentService = studentService;
    }
    @Override
    public void run (String... args){
        boolean isRunning = true;
        System.out.println("==================================================");
        System.out.println("                    WELCOME                       ");
        System.out.println("                      TO                          ");
        System.out.println("                  IRONSCHOOL                      ");
        System.out.println("==================================================");
        while (isRunning){
            System.out.println("Choose option:");
            System.out.println("1. Course's commands");
            System.out.println("2. Teacher's commands");
            System.out.println("3. 's commands");
            System.out.println("4. Exit");
            System.out.print("Choosing (1-4): ");
            int choice = scanner.nextInt();
            System.out.println();
            switch (choice){
                case 1:
                    System.out.println("Creating course...");
                    break;
                case 2:
                    System.out.println("Creating teacher...");
                    break;
                case 3:
                    System.out.println("Creating student");
                    break;
                case 4:
                    System.out.println("Closing menu...");
                    isRunning=false;
                    break;
                default:
                    System.out.println("Invalid option. Choose 1-4!");
            }
        }
    }
}
