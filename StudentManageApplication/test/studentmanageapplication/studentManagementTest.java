/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package studentmanageapplication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class studentManagementTest {

    private StudentManagement instance;
    String simulatedUserInput = "Ngo Gia Bao\n"
            + "baogmail.com\n"
            + "bao@gmail.com\n"
            + "password123\n";

    public studentManagementTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new StudentManagement();
        instance.studentList.add(new Student("CA123", "Ngo Gia Bao", "bao@gmail.com", "123"));
        instance.studentList.add(new Student("CA456", "Nguyen Van An", "an@gmail.com", "456"));
    }

    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Hàm hỗ trợ: Giả lập người dùng gõ phím (Mocking)
     */
    private void simulateInput(String data) {
        InputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);
        instance.scanner = new Scanner(in);
    }

    //--------------------------------- Normal case ------------------------------------//
    @Test
    public void testDisplayTable() {
        System.out.println("Test: displayTable");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.displayTable();
        System.setOut(originalOut);
        String expectedResult = "+---------+----------------------+----------------------+---------------+\n"
                + "|   Code  |      Fullname        |         Email        |    Password   |\n"
                + "+---------+----------------------+----------------------+---------------+\n"
                + String.format("|%-9s|%-22s|%-22s|%-15s|\n", "CA123", "Ngo Gia Bao", "bao@gmail.com", "123")
                + String.format("|%-9s|%-22s|%-22s|%-15s|\n", "CA456", "Nguyen Van An", "an@gmail.com", "456")
                + "+---------+----------------------+----------------------+---------------+";
        String rawOutput = outContent.toString();
        String expected = expectedResult.replace("\r\n", "\n").trim();
        String actual = rawOutput.replace("\r\n", "\n").trim();

        assertEquals(expected, actual);
    }

    @Test
    public void testAddStudent() {
        System.out.println("Test: addStudent with email validation");
        int sizeBefore = instance.studentList.size();
        simulateInput(simulatedUserInput);
        instance.addStudent();
        assertEquals("Student list size should increase by 1", sizeBefore + 1, instance.studentList.size());
        Student lastStudent = instance.studentList.get(instance.studentList.size() - 1);
        assertEquals(3, instance.studentList.size());
        assertEquals("Name should match the input", "ngo gia bao", lastStudent.getFullName());
        assertEquals("Email should match the valid input", "bao@gmail.com", lastStudent.getEmail());
        assertEquals("Password should match", "password123", lastStudent.getPassword());
    }

    @Test
    public void testEditStudent() {
        System.out.println("Test: editStudent");
        String simulatedInput = "ca123\nngo gia bao edited\nbao.edited@gmail.com\nnewpass123\n";
        simulateInput(simulatedInput);
        instance.editStudent();
        Student editedStudent = instance.studentList.get(0);
        assertEquals("Name should be updated", "ngo gia bao edited", editedStudent.getFullName());
        assertEquals("Email should be updated", "bao.edited@gmail.com", editedStudent.getEmail());
        assertEquals("Password should be updated", "newpass123", editedStudent.getPassword());
    }

    @Test
    public void testRemoveStudentByCode() {
        System.out.println("Test: removeStudent (Option 'code')");
        simulateInput("code\nca123\nexit\n");
        instance.removeStudent();
        assertEquals("Student list size should be 1", 1, instance.studentList.size());
        assertEquals("Remaining student should be CA456", "CA456", instance.studentList.get(0).getStudentCode());
    }

    @Test
    public void testRemoveStudentAll() {
        System.out.println("Test: removeStudent (Option 'all')");
        simulateInput("all\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeStudent();
        System.setOut(originalOut);
        assertEquals(0, instance.studentList.size());
        String rawOutput = outContent.toString();
        String prompt = "Type 'all' to remove all student.\n"
                + "Type 'code' to remove student by code.\n"
                + "Type 'exit' to exit feature remove.\n"
                + "Please type your select: ";
        String actualResult = rawOutput.substring(prompt.length()).trim();
        String expectedResult = "Remove all student successfully."
                + System.lineSeparator()
                + "List student is empty.";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSearchStudentByStudentCode() {
        System.out.println("Test: SearchStudentByStudentCode");
        simulateInput("ca123\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.SearchStudentByStudentCode();
        System.setOut(originalOut);
        String expectedResult = "+---------+----------------------+----------------------+---------------+\n"
                + "|   Code  |      Fullname        |         Email        |    Password   |\n"
                + "+---------+----------------------+----------------------+---------------+\n"
                + String.format("|%-9s|%-22s|%-22s|%-15s|\n", "CA123", "Ngo Gia Bao", "bao@gmail.com", "123")
                + "+---------+----------------------+----------------------+---------------+";
        String prompt = "Please enter student code: ";
        String rawOutput = outContent.toString();
        String actualResult = rawOutput.substring(rawOutput.indexOf(prompt) + prompt.length());
        String safeExpected = expectedResult.replace("\r\n", "\n").trim();
        String safeActual = actualResult.replace("\r\n", "\n").trim();
        assertEquals(safeExpected, safeActual);
    }

    @Test
    public void testSortName() {
        System.out.println("Test: sortName");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.sortName();
        System.setOut(originalOut);
        String output = outContent.toString(); // code không dùng thi clear
        assertEquals("Nguyen Van An", instance.studentSortedList.get(0).getFullName());
        assertEquals("Ngo Gia Bao", instance.studentSortedList.get(1).getFullName());
        assertEquals("Original list should NOT be sorted (Bao is still index 0)", "Ngo Gia Bao", instance.studentList.get(0).getFullName());
        assertEquals("Original list should NOT be sorted (An is still index 1)", "Nguyen Van An", instance.studentList.get(1).getFullName());
    }

    //--------------------------------- abnormal case ------------------------------------//
    @Test
    public void testDisplayTableEmptyList() {
        System.out.println("Abnormal Test 1: Display with empty list");
        instance.studentList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.displayTable();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        assertEquals("List of student is emplty!", rawOutput);
    }

    @Test
    public void testAddStudentInvalidEmail() {
        System.out.println("Abnormal Test 2: Add student with invalid emails before valid one");
        int sizeBefore = instance.studentList.size();
        String invalidInput = "Le Thi B\n"
                + "saiemail.com\n"
                + "sai@email@com\n"
                + "dung@gmail.com\n"
                + "pass123\n";
        simulateInput(invalidInput);
        instance.addStudent();
        assertEquals(sizeBefore + 1, instance.studentList.size());
        Student lastStudent = instance.studentList.get(instance.studentList.size() - 1);
        assertEquals("le thi b", lastStudent.getFullName());
        assertEquals("dung@gmail.com", lastStudent.getEmail());
        assertEquals("pass123", lastStudent.getPassword());
    }

    @Test
    public void testEditStudentNotFound() {
        System.out.println("Abnormal Test 3: Edit student with non-existent code");
        simulateInput("CA999\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.editStudent();
        System.setOut(originalOut);
        String rawOutput = outContent.toString();
        String prompt = "Please enter student code: ";
        String actualResult = rawOutput.substring(prompt.length()).trim();
        assertEquals("Not found student have student code: ca999", actualResult);
    }

    @Test
    public void testRemoveStudentInvalidOption() {
        System.out.println("Abnormal Test 4.1: Remove student with invalid menu option");
        simulateInput("abc\nexit\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeStudent();
        System.setOut(originalOut);
        String rawOutput = outContent.toString();
        String prompt = "Type 'all' to remove all student.\n"
                + "Type 'code' to remove student by code.\n"
                + "Type 'exit' to exit feature remove.\n"
                + "Please type your select: ";
        String actualResult = rawOutput.substring(prompt.length()).trim();
        assertEquals("Invalid option! Please type 'all', 'code', or 'exit'."
                + System.lineSeparator()
                + prompt
                + "Exit remove feature", actualResult);
    }

    @Test
    public void testRemoveStudentByCodeNotFound() {
        System.out.println("Abnormal Test 4.2: Remove by code but code not found");
        simulateInput("code\nca999\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeStudent();
        System.setOut(originalOut);
        String rawOutput = outContent.toString();
        String menuPrompt = "Type 'all' to remove all student.\n"
                + "Type 'code' to remove student by code.\n"
                + "Type 'exit' to exit feature remove.\n"
                + "Please type your select: ";
        String Prompt = "Please enter student code: ";
        String actualResult = rawOutput.replace(menuPrompt, "")
                .replace(Prompt, "")
                .trim();
        assertEquals("Not found student have an student code: ca999", actualResult);
    }

    @Test
    public void testRemoveStudentAllEmptyList() {
        System.out.println("Abnormal Test 4.3: Remove ALL but list is already empty");
        instance.studentList.clear();
        simulateInput("all");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeStudent();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        String prompt = ("Type 'all' to remove all student.\n"
                + "Type 'code' to remove student by code.\n"
                + "Type 'exit' to exit feature remove.\n"
                + "Please type your select: ");
        String actualResult = rawOutput.substring(prompt.length()).trim();
        assertEquals("List student is empty.", actualResult);
    }

    @Test
    public void testSearchStudentByStudentCodeNotFound() {
        System.out.println("Abnormal Test 5: Search with no result");
        simulateInput("ca999");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.SearchStudentByStudentCode();
        System.setOut(originalOut);
        String rawOutput = outContent.toString();
        String prompt = "Please enter student code: ";
        String actualResult = rawOutput.substring(prompt.length()).trim();
        assertEquals("Not found ca999", actualResult);
    } //đã refactor test  (Test mẫu)

    @Test
    public void testSortNameEmptyList() {
        System.out.println("Abnormal Test 6: Sort with empty list");
        instance.studentList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.sortName();
        System.setOut(originalOut);
        String rawOutput = outContent.toString();
        String actualResult = rawOutput.trim();
        assertEquals("The student list is empty. It cannot be sorted!", actualResult);
    }
}
