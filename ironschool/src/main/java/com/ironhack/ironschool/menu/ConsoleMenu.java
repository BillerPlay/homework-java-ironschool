package com.ironhack.ironschool.menu;

import com.ironhack.ironschool.exception.InvalidCommandException;
import com.ironhack.ironschool.exception.InvalidInputException;
import com.ironhack.ironschool.exception.TeacherAlreadyAssignedException;
import com.ironhack.ironschool.model.Course;
import com.ironhack.ironschool.model.Student;
import com.ironhack.ironschool.model.Teacher;
import com.ironhack.ironschool.service.CourseService;
import com.ironhack.ironschool.service.StudentService;
import com.ironhack.ironschool.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private Map<String, Teacher> teachers = new HashMap<>();
    private Map<String, Course> courses = new HashMap<>();
    private Map<String, Student> students = new HashMap<>();
    @Override
    public void run (String... args){
        boolean isRunning = true;
        System.out.println("==================================================");
        System.out.println("                    WELCOME                       ");
        System.out.println("                      TO                          ");
        System.out.println("                    SYSTEM                        ");
        System.out.println("==================================================");
//========================== Assigning school name ==========================
        System.out.print("Enter school name: ");
        String schoolName = scanner.nextLine();
// ========================== Initialize teachers ==========================
        System.out.print("\n How many teachers?: ");
        int teachersCount = scanner.nextInt();
        scanner.nextLine();
        for(int i=0; i<teachersCount; i++) {
            Teacher teacher = null;
            while (teacher == null) {
                try {
                    System.out.print("Enter teacher's name: ");
                    String teacherName = scanner.nextLine();

                    System.out.print("Enter teacher's salary: ");
                    double teacherSalary = scanner.nextDouble();
                    scanner.nextLine();

                     teacher = new Teacher(teacherName, teacherSalary);
                    if (teacher.getSalary() < 300.00) {
                        throw new InvalidInputException("Teacher's salary can not be less than $300 because of laws about minimal salary!");
                    }
                    if (teacher.getName().length() <= 3) {
                        throw new InvalidInputException("Teacher's name can not be less 3 letters.");
                    }
                    teachers.put(teacher.getTeacherId(), teacher);
                    System.out.println("Teacher created: " + teacher.getTeacherId() + " - " + teacher.getName());
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    teacher = null;
                } catch (NumberFormatException e) {
                    System.out.println("Salary must be a number. Please try again.");
                    teacher = null;
                }
            }
        }
//  ========================== Initialize courses ==========================
        System.out.print("\n How many courses?: ");
        int coursesCount = scanner.nextInt();
        scanner.nextLine();
        for(int i=0; i<coursesCount; i++) {
            Course course = null;
            while (course == null) {
                try {
                    System.out.print("Enter course's name: ");
                    String courseName = scanner.nextLine();

                    System.out.print("Enter course's price: ");
                    double coursePrice = scanner.nextDouble();
                    scanner.nextLine();

                    course = new Course(courseName, coursePrice);
                    if (course.getName().length() <= 3) {
                        throw new InvalidInputException("Course's name can not be less 3 letters.");
                    }
                    if (course.getPrice() < 0.00) {
                        throw new InvalidInputException("Course's price can not be negative.");
                    }
                    courses.put(course.getCourseId(), course);
                    System.out.println("Course created: " + course.getCourseId() + " - " + course.getName());
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    course=null;
                } catch (NumberFormatException e) {
                    System.out.println("Price must be a number. Please try again.");
                    course=null;
                }
            }
        }
// ========================== Initialize students ==========================
        System.out.print("\n How many students?: ");
        int studentsCount = scanner.nextInt();
        scanner.nextLine();
        for(int i=0; i<studentsCount; i++) {
            Student student = null;
            while (student == null) {
                try {
                    System.out.print("Enter student's name: ");
                    String studentName = scanner.nextLine();

                    System.out.print("Enter student's address: ");
                    String studentAddress = scanner.nextLine();

                    System.out.print("Enter student's email: ");
                    String studentEmail = scanner.nextLine();

                    student = new Student(studentName, studentAddress, studentEmail);
                    if (student.getName().length() <= 3) {
                        throw new InvalidInputException("Student's name can not be less 3 letters.");
                    }
                    if (student.getAddress().length() <= 3) {
                        throw new InvalidInputException("Student's name can not be less 3 letters.");
                    }
                    if (!student.getEmail().contains("@")) {
                        throw new InvalidInputException("Student's email is probably invalid.");
                    }
                    students.put(student.getStudentId(), student);
                    System.out.println("Student created: " + student.getStudentId() + " - " + student.getName());
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    student = null;
                }
            }
        }
        System.out.println("Setup is done! Open the console!");
//=======================  Main Console ==========================
        while (isRunning) {
            System.out.print("Console:");
            String command = scanner.nextLine().trim();
            System.out.println();
            if (command.isEmpty()) continue;
            String[] partsOfCommand = command.split(" ");
            String mainCommand = partsOfCommand[0].toUpperCase();
            try {
                switch (mainCommand) {
                    case "ENROLL":
                        if (partsOfCommand.length != 3) {
                            throw new InvalidCommandException("Usage: ENROLL [STUDENT_ID] [COURSE_ID]");
                        }
                        if (!students.containsKey(partsOfCommand[1])) {
                            throw new InvalidCommandException("Student with ID " + partsOfCommand[1] + " does not exist.");
                        }
                        if (!courses.containsKey(partsOfCommand[2])) {
                            throw new InvalidCommandException("Course with ID " + partsOfCommand[2] + " does not exist.");
                        }
                    case "EXIT":
                        isRunning = false;
                        System.out.println("Closing console...");
                        break;
                    default:
                        System.out.println("Invalid command!");
                }
            }
            catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("\n CONSOLE IS CLOSED!");
    }
}
