package com.ironhack.ironschool.menu;

import com.ironhack.ironschool.exception.InvalidCommandException;
import com.ironhack.ironschool.exception.InvalidInputException;
import com.ironhack.ironschool.model.Course;
import com.ironhack.ironschool.model.Student;
import com.ironhack.ironschool.model.Teacher;
import com.ironhack.ironschool.service.CourseService;
import com.ironhack.ironschool.service.StudentService;
import com.ironhack.ironschool.service.TeacherService;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleMenu implements CommandLineRunner {

    private final TeacherService teacherService;
    private final CourseService courseService;
    private final StudentService studentService;

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(TeacherService teacherService,
                       CourseService courseService,
                       StudentService studentService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Override
    public void run(String @NonNull ... args) {
        boolean isRunning = true;

        System.out.println("==================================================");
        System.out.println("                    WELCOME                       ");
        System.out.println("                      TO                          ");
        System.out.println("                    SYSTEM                        ");
        System.out.println("==================================================");

        System.out.print("Enter school name: ");
        String schoolName = scanner.nextLine();

        setupTeachers();
        setupCourses();
        setupStudents();

        System.out.println("Setup is done! Open the console!");

        // ====== Main Console ======
        while (isRunning) {
            System.out.print("Console: ");
            String command = scanner.nextLine().trim();
            if (command.isEmpty()) continue;

            String[] parts = command.split(" ");
            String mainCommand = parts[0].toUpperCase();

            try {
                switch (mainCommand) {
                    case "ENROLL":
                        if (parts.length != 3)
                            throw new InvalidCommandException("Usage: ENROLL [STUDENT_ID] [COURSE_ID]");
                        enrollStudent(parts[1], parts[2]);
                        break;

                    case "ASSIGN":
                        if (parts.length != 3)
                            throw new InvalidCommandException("Usage: ASSIGN [TEACHER_ID] [COURSE_ID]");
                        assignTeacher(parts[1], parts[2]);
                        break;

                    case "SHOW":
                        if (parts.length != 2)
                            throw new InvalidCommandException("Usage: SHOW [COURSES/STUDENTS/TEACHERS/PROFIT]");
                        showCommand(parts[1].toUpperCase());
                        break;

                    case "LOOKUP":
                        if (parts.length != 3)
                            throw new InvalidCommandException("Usage: LOOKUP [COURSE/STUDENT/TEACHER] [ID]");
                        lookupCommand(parts[1].toUpperCase(), parts[2]);
                        break;

                    case "EXIT":
                        isRunning = false;
                        System.out.println("Closing console...");
                        break;

                    default:
                        System.out.println("Invalid command!");
                }
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\nCONSOLE IS CLOSED!");
    }

    private void enrollStudent(String studentId, String courseId) {
        courseService.enrollStudent(courseId, studentId);
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);
        System.out.println("Student " + student.getName() + " enrolled in course " + course.getName());
    }

    private void assignTeacher(String teacherId, String courseId) {
        courseService.assignTeacher(courseId, teacherId);
        Teacher teacher = teacherService.findTeacherById(teacherId);
        Course course = courseService.findCourseById(courseId);
        System.out.println("Teacher " + teacher.getName() + " assigned to course " + course.getName());
    }

    private void showCommand(String type) {
        switch (type) {
            case "COURSES":
                courseService.getAllCourses().forEach(c ->
                        System.out.println(c.getCourseId() + " - " + c.getName() + " - $" + c.getPrice()));
                break;
            case "STUDENTS":
                studentService.getAllStudents().forEach(s ->
                        System.out.println(s.getStudentId() + " - " + s.getName()));
                break;
            case "TEACHERS":
                teacherService.getAllTeachers().forEach(t ->
                        System.out.println(t.getTeacherId() + " - " + t.getName()));
                break;
            case "PROFIT":
                double profit = courseService.getTotalEarned() -
                        teacherService.getAllTeachers().stream().mapToDouble(Teacher::getSalary).sum();
                System.out.println("Total profit: $" + profit);
                break;
            default:
                throw new InvalidCommandException("SHOW command not recognized");
        }
    }

    private void lookupCommand(String type, String id) {
        switch (type) {
            case "COURSE":
                courseService.lookupCourse(id);
                break;

            case "STUDENT":
                studentService.lookupStudent(id);
                break;

            case "TEACHER":
                teacherService.lookupTeacher(id);
                break;

            default:
                throw new InvalidCommandException("LOOKUP command not recognized");
        }
    }

    private void setupTeachers() {
        System.out.print("\nHow many teachers?: ");
        int count = readInt();
        for (int i = 0; i < count; i++) {
            while (true) {
                try {
                    System.out.print("Enter teacher's name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter teacher's salary: ");
                    double salary = readDouble();

                    if (name.length() <= 3) throw new InvalidInputException("Teacher's name must be >3 letters");
                    if (salary < 300) throw new InvalidInputException("Teacher's salary must be at least $300");

                    Teacher teacher = new Teacher(name, salary);
                    teacherService.addTeacher(teacher);
                    System.out.println("Teacher created: " + teacher.getTeacherId() + " - " + teacher.getName());
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void setupCourses() {
        System.out.print("\nHow many courses?: ");
        int count = readInt();
        for (int i = 0; i < count; i++) {
            while (true) {
                try {
                    System.out.print("Enter course's name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter course's price: ");
                    double price = readDouble();

                    if (name.length() <= 3) throw new InvalidInputException("Course name must be >3 letters");
                    if (price < 0) throw new InvalidInputException("Course price cannot be negative");

                    Course course = new Course(name, price);
                    courseService.addCourse(course);
                    System.out.println("Course created: " + course.getCourseId() + " - " + course.getName());
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void setupStudents() {
        System.out.print("\nHow many students?: ");
        int count = readInt();
        for (int i = 0; i < count; i++) {
            while (true) {
                try {
                    System.out.print("Enter student's name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student's address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter student's email: ");
                    String email = scanner.nextLine();

                    if (name.length() <= 3) throw new InvalidInputException("Student name must be >3 letters");
                    if (address.length() <= 3) throw new InvalidInputException("Student address must be >3 letters");
                    if (!email.contains("@")) throw new InvalidInputException("Invalid email");

                    Student student = new Student(name, address, email);
                    studentService.addStudent(student);
                    System.out.println("Student created: " + student.getStudentId() + " - " + student.getName());
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value < 0) throw new InvalidInputException("Value cannot be negative");
                return value;
            } catch (NumberFormatException | InvalidInputException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value < 0) throw new InvalidInputException("Value cannot be negative");
                return value;
            } catch (NumberFormatException | InvalidInputException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}