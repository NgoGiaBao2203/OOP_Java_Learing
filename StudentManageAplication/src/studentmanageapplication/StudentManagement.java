/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanageapplication;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author giaba
 */
public class StudentManagement {

    public ArrayList<Student> studentList;
    public ArrayList<Student> studentSortedList;
    public Scanner scanner;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public StudentManagement() {
        studentList = new ArrayList<>();
        scanner = new Scanner(System.in);
        studentSortedList = new ArrayList<>();
    }

    public void sortName() {
        if (studentList == null || studentList.isEmpty()) {
            System.out.println("The student list is empty. It cannot be sorted!");
        } else {
            studentSortedList = sortStudentList();
            System.out.println("Sorted by Name (A-Z) successfully!");
            displayTable(studentSortedList);
        }
    }

    public void editStudent() {
        String studentCode = inputString("Please enter student code: ");
        Student student = findStudentByStudentCode(studentCode);
        if (student == null) {
            System.out.println("Not found student have student code: " + studentCode);
        } else {
            Student oldStudentInfo = new Student("", "", "", "");
            oldStudentInfo.setStudentCode(student.getStudentCode());
            oldStudentInfo.setFullName(student.getFullName());
            oldStudentInfo.setEmail(student.getEmail());
            oldStudentInfo.setPassword(student.getPassword());

            System.out.println("Found student have student code: " + studentCode);
            student.setFullName(inputString("Please edit fullname: "));
            student.setEmail(inputString("Please edit email: "));
            student.setPassword(inputString("Please edit password: "));
            System.out.println("Update information for student successfully.");
            displayTable(oldStudentInfo, "Old information of student");
            displayTable(student, "New information of student");
        }
    }

    public void removeStudent() {
        Boolean flag;
        do {
            flag = false;
            String choice = inputString("Type 'all' to remove all student.\n"
                    + "Type 'code' to remove student by code.\n"
                    + "Type 'exit' to exit feature remove.\n"
                    + "Please type your select: ");
            switch (choice) {
                case "all":
                    removeAllStudent();
                    flag = false;
                    break;
                case "code":
                    removeStudentByStudentCode();
                    flag = false;
                    break;
                case "exit":
                    System.out.println("Exit remove feature");
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid option! Please type 'all', 'code', or 'exit'.");
                    flag = true;
                    break;
            }
        } while (flag);
    }

    public void SearchStudentByStudentCode() {
        String keyword = inputString("Please enter student code: ");
        ArrayList<Student> result = findStudentForSearch(keyword);
        if (result.isEmpty()) {
            System.out.println("Not found " + keyword);
        } else {
            displayTable(result);
        }
        result.removeAll(result);
    }

    public void addStudent() {
        Student student = new Student("", "", "", "");
        student.setStudentCode(generateStudentCode());
        student.setFullName(inputString("Please enter fullname: "));
        while (true) {
            String email = inputString("Please enter email: ");
            if (validateEmail(email)) {
                student.setEmail(email);
                break;
            } else {
                System.out.println("Invalid email format. Please try again!");
            }
        }
        student.setPassword(inputString("Please enter password: "));
        studentList.add(student);
        System.out.println("Add student successfully!");
    }

    public void displayTable() {
        if (!studentList.isEmpty()) {
            System.out.println("+---------+----------------------+----------------------+---------------+");
            System.out.println("|   Code  |      Fullname        |         Email        |    Password   |");
            System.out.println("+---------+----------------------+----------------------+---------------+");
            for (int i = 0; i < studentList.size(); i++) {
                Student student = this.studentList.get(i);
                System.out.printf(
                        "|%-9s|%-22s|%-22s|%-15s|\n",
                        student.getStudentCode(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getPassword()
                );
            }
            System.out.println("+---------+----------------------+----------------------+---------------+");
        } else {
            System.out.println("List of student is emplty!");
        }
    }

    public void displayTable(Student student, String message) {
        if (student != null) {
            System.out.println(message);
            System.out.println("+---------+----------------------+----------------------+---------------+");
            System.out.println("|   Code  |      Fullname        |         Email        |    Password   |");
            System.out.println("+---------+----------------------+----------------------+---------------+");
            System.out.printf(
                    "|%-9s|%-22s|%-22s|%-15s|\n",
                    student.getStudentCode(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getPassword()
            );
            System.out.println("+---------+----------------------+----------------------+---------------+");
        } else {
            System.out.println("Not found!");
        }
    }

    public void displayTable(ArrayList<Student> students) {
        if (!students.isEmpty()) {
            System.out.println("+---------+----------------------+----------------------+---------------+");
            System.out.println("|   Code  |      Fullname        |         Email        |    Password   |");
            System.out.println("+---------+----------------------+----------------------+---------------+");
            for (Student student : students) {
                System.out.printf(
                        "|%-9s|%-22s|%-22s|%-15s|\n",
                        student.getStudentCode(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getPassword()
                );
            }
            System.out.println("+---------+----------------------+----------------------+---------------+");
        } else {
            System.out.println("Not found!");
        }
    }

    private String inputString(String title) {
        System.out.print(title);
        String input = scanner.nextLine().trim().toLowerCase();
        return input;
    }

    private String generateStudentCode() {
        String studentCode;
        Random random = new Random();
        int threeDigitNum = random.nextInt(900) + 100;
        studentCode = "CA" + threeDigitNum;
        if (isStudentCodeDuplicated(studentCode)) {
            return generateStudentCode();
        }
        System.out.println("Student code: " + studentCode);
        return studentCode;
    }

    private Boolean isStudentCodeDuplicated(String studentCode) {
        for (Student student : studentList) {
            if (student.getStudentCode().equals(studentCode)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches() == true) {
            return true;
        } else {
            return false;

        }
    }

    private ArrayList<Student> findStudentForSearch(String studentCode) {
        ArrayList<Student> studentListFound = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getStudentCode().toLowerCase().contains(studentCode)) {
                studentListFound.add(student);
            }
        }
        return studentListFound;
    }

    private Student findStudentByStudentCode(String studentCode) {
        for (Student student : studentList) {
            if (student.getStudentCode().equalsIgnoreCase(studentCode)) {
                return student;
            }
        }
        return null;
    }

    private void removeAllStudent() {
        if (!studentList.isEmpty()) {
            studentList.removeAll(studentList);
            System.out.println("Remove all student successfully.");
        }
        System.out.println("List student is empty.");
    }

    private void removeStudentByStudentCode() {
        if (!studentList.isEmpty()) {
            String studentCode = inputString("Please enter student code: ");
            for (Student student : studentList) {
                if (student.getStudentCode().equalsIgnoreCase(studentCode)) {
                    studentList.remove(student);
                    System.out.println("Removed student have an student code: " + studentCode);
                } else {
                    System.out.println("Not found student have an student code: " + studentCode);
                }
            }
        } else {
            System.out.println("List student is empty.");
        }
    }

    private String getNameOnly(String fullName) {
        String name = fullName;
        if (name.contains(" ")) {
            return name.substring(name.lastIndexOf(" ") + 1);
        }
        return name;
    }

    private ArrayList<Student> sortStudentList() {
        ArrayList<Student> termList = new ArrayList<>(studentList);
        termList.sort((student1, student2) -> {
            String name1 = getLastName(student1.getFullName());
            String name2 = getLastName(student2.getFullName());
            return name1.compareToIgnoreCase(name2);
        });
        return termList;
    }

    private String getLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }
        String[] parts = fullName.trim().split("\\s+");
        return parts[parts.length - 1];
    }
}
