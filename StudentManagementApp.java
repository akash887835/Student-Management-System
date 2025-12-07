// Student Management System - Full Java Console Project
// Features: Add, Remove, Search, Edit, Display Students + File Handling + Validation

import java.io.*;
import java.util.*;

// ---------------- Student Class ----------------
class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() { return name; }
    public int getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }

    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll No: " + rollNumber + ", Grade: " + grade;
    }
}

// ---------------- Student Management System Class ----------------
class StudentManagementSystem {
    private List<Student> students;
    private final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadFromFile();
    }

    // Add Student
    public void addStudent(Student s) {
        students.add(s);
        saveToFile();
    }

    // Remove Student
    public boolean removeStudent(int roll) {
        for (Student s : students) {
            if (s.getRollNumber() == roll) {
                students.remove(s);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // Search Student
    public Student searchStudent(int roll) {
        for (Student s : students) {
            if (s.getRollNumber() == roll) return s;
        }
        return null;
    }

    // Get all students
    public List<Student> getAllStudents() {
        return students;
    }

    // Save File
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    // Load File
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}

// ---------------- Main Class (Console UI) ----------------
public class StudentManagementApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        while (true) {
            System.out.println("\n----- Student Management System -----");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Edit Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine().trim();

                    System.out.print("Enter Roll Number: ");
                    int roll = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Grade: ");
                    String grade = sc.nextLine().trim();

                    if (name.isEmpty() || grade.isEmpty()) {
                        System.out.println("Invalid Input! Fields cannot be empty.");
                        break;
                    }

                    sms.addStudent(new Student(name, roll, grade));
                    System.out.println("Student Added Successfully!");
                    break;

                case 2:
                    System.out.print("Enter Roll Number to Remove: ");
                    roll = sc.nextInt();

                    if (sms.removeStudent(roll))
                        System.out.println("Student Removed!");
                    else
                        System.out.println("Student Not Found!");
                    break;

                case 3:
                    System.out.print("Enter Roll Number to Search: ");
                    roll = sc.nextInt();

                    Student s = sms.searchStudent(roll);
                    if (s != null)
                        System.out.println("Found: " + s);
                    else
                        System.out.println("Student Not Found!");
                    break;

                case 4:
                    System.out.print("Enter Roll Number to Edit: ");
                    roll = sc.nextInt();
                    sc.nextLine();

                    Student st = sms.searchStudent(roll);
                    if (st == null) {
                        System.out.println("Student Not Found!");
                        break;
                    }

                    System.out.print("Enter New Name: ");
                    String newName = sc.nextLine().trim();

                    System.out.print("Enter New Grade: ");
                    String newGrade = sc.nextLine().trim();

                    if (!newName.isEmpty()) st.setName(newName);
                    if (!newGrade.isEmpty()) st.setGrade(newGrade);

                    System.out.println("Student Updated!");
                    break;

                case 5:
                    List<Student> list = sms.getAllStudents();
                    if (list.isEmpty()) System.out.println("No Students Available");
                    else list.forEach(System.out::println);
                    break;

                case 6:
                    System.out.println("Exiting... Goodbye!");
                    return;

                default:
                    System.out.println("Invalid Choice! Try Again.");
            }
        }
    }
}
