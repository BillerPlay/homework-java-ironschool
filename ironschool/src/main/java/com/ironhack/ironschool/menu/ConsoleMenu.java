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
                    double teacherSalary = scanner.nextDouble();
                    scanner.nextLine();

                     teacher = new Teacher(teacherName, teacherSalary);
                    if (teacher.getSalary() < 300.00) {
                        throw new InvalidInputException("Teacher's salary can not be less than $300 because of laws about minimal salary!");
                    }
                    if (teacher.getName().length() <= 3) {
                        throw new InvalidInputException("Teacher's name can not be less 3 letters.");
                    }
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
                    double coursePrice = scanner.nextDouble();
                    scanner.nextLine();

                    course = new Course(courseName, coursePrice);
                    if (course.getName().length() <= 3) {
                        throw new InvalidInputException("Course's name can not be less 3 letters.");
                    }
                    if (course.getPrice() < 0.00) {
                        throw new InvalidInputException("Course's price can not be negative.");
                    }
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
                    studentService.addStudent(student);
                    System.out.println("Student created: " + student.getStudentId() + " - " + student.getName());
                    break;
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("Setup is done! Open the console!");
//=======================  Main Console ==========================
        while (isRunning) {
            System.out.print("Console: ");
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
                        studentService.enrollStudentToCourse(partsOfCommand[1], partsOfCommand[2]);
                        break;
                    case "ASSIGN":
                        if (partsOfCommand.length != 3) {
                            throw new InvalidCommandException("Usage: ASSIGN [TEACHER_ID] [COURSE_ID]");
                        }
                        if (!teachers.containsKey(partsOfCommand[1])) {
                            throw new InvalidCommandException("Teacher with ID " + partsOfCommand[1] + " does not exist.");
                        }
                        if (!courses.containsKey(partsOfCommand[2])) {
                            throw new InvalidCommandException("Course with ID " + partsOfCommand[2] + " does not exist.");
                        }
                        courseService.assignTeacher(partsOfCommand[1], partsOfCommand[2]);
                        break;
                    case "SHOW":
                        if (partsOfCommand.length != 2) {
                            throw new InvalidCommandException("Usage: SHOW [COURSES|STUDENTS|TEACHERS|PROFIT]");
                        }

                        String whatToShow = partsOfCommand[1];

                        switch (whatToShow) {
                            case "COURSES":
                                courseService.showCourses();
                                break;

                            case "STUDENTS":
                                studentService.showStudents();
                                break;

                            case "TEACHERS":
                                teacherService.showTeachers();
                                break;

                            case "PROFIT":
                                double total = courseService.getTotalEarned();
                                System.out.println("Total profit: " + total);
                                break;

                            default:
                                throw new InvalidCommandException("Usage: SHOW [COURSES|STUDENTS|TEACHERS|PROFIT]");
                        }
                        break;
                    case "LOOKUP":

                        if (partsOfCommand.length != 3) {
                            throw new InvalidCommandException("Usage: LOOKUP [COURSE|STUDENT|TEACHER] [ID]");
                        }

                        String type = partsOfCommand[1];
                        String id = partsOfCommand[2];

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
                                throw new InvalidCommandException("Unknown LOOKUP command");
                        }

                        break;
                    case "EXIT":
                        isRunning = false;
                        System.out.println("Closing console...");
                        break;
                    default:
                        System.out.println("Invalid command!");
                }
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