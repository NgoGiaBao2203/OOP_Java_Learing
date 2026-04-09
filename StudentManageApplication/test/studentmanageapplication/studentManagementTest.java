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
import javax.print.attribute.PrintServiceAttribute;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class studentManagementTest {

    // Đưa instance ra ngoài để dùng chung cho tất cả các test
    private StudentManagement instance;
    // Kịch bản: 
    // - Tên: Ngo Gia Bao
    // - Email sai lần 1: baogmail.com
    // - Email đúng lần 2: bao@gmail.com
    // - Password: password123
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
        // Khởi tạo mới trước mỗi test 
        instance = new StudentManagement();

        // Thêm một vài dữ liệu giả để test sửa, xóa, tìm kiếm
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
        ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        instance.displayTable();
        System.setOut(originalOut);
        String output = outContent.toString();
        assertDataIntialForTest(output);
        assertFalse("Output should contain CA111", output.contains("CA111"));
        assertFalse("Output should contain Nguyen Van Tuan", output.contains("Nguyen Van Tuan"));
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
        assertTrue("Student code should start with CA", lastStudent.getStudentCode().startsWith("CA"));
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
        simulateInput("all\nexit\n");
        instance.removeStudent();
        assertTrue("Student list should be empty", instance.studentList.isEmpty());
    }

    @Test
    public void testSearchStudentByStudentCode() {
        System.out.println("Test: SearchStudentByStudentCode");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        simulateInput("ca\n");
        instance.SearchStudentByStudentCode();
        System.setOut(originalOut);
        String output = outContent.toString();
        assertDataIntialForTest(output);
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

    private void assertDataIntialForTest(String output) {
        assertTrue("Output should contain CA123", output.contains("CA123"));
        assertTrue("Output should contain Ngo Gia Bao", output.contains("Ngo Gia Bao"));
        assertTrue("Output should contain CA456", output.contains("CA456"));
        assertTrue("Output should contain Nguyen Van An", output.contains("Nguyen Van An"));
    }

    //--------------------------------- abnormal case ------------------------------------//
    @Test
    public void testDisplayTableEmptyList() {
        System.out.println("Abnormal Test 1: Display with empty list");
        instance.studentList.clear();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        instance.displayTable();
        assertTrue("Should notify that the list is empty", outContent.toString().contains("List of student is emplty!"));
    } // sai logic test

    @Test
    public void testAddStudentInvalidEmail() {
        System.out.println("Abnormal Test 2: Add student with invalid emails before valid one");
        String invalidInput = "Le Thi B\n"
                + "saiemail.com\n"
                + "sai@email@com\n"
                + "dung@gmail.com\n"
                + "pass123\n";
        simulateInput(invalidInput);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.addStudent();
        String output = outContent.toString();
        assertTrue("Should show invalid email warning", output.contains("Invalid email format. Please try again!"));
        Student addedStudent = instance.studentList.get(instance.studentList.size() - 1);
        assertEquals("dung@gmail.com", addedStudent.getEmail()); // kiem tra lai assert
    } //sai logic test

    @Test
    public void testEditStudentNotFound() {
        System.out.println("Abnormal Test 3: Edit student with non-existent code");
        simulateInput("CA999\n");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.editStudent();
        assertTrue("Should notify student not found", outContent.toString().contains("Not found student have student code: ca999"));
    } // sai logic test

    @Test
    public void testRemoveStudentInvalidOption() {
        System.out.println("Abnormal Test 4.1: Remove student with invalid menu option");
        simulateInput("abc\nexit\n");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeStudent();
        assertTrue("Should notify invalid option", outContent.toString().contains("Invalid option! Please type 'all', 'code', or 'exit'."));
    } //sai logic test

    @Test
    public void testRemoveStudentByCodeNotFound() {
        System.out.println("Abnormal Test 4.2: Remove by code but code not found");
        simulateInput("code\nca999\nexit\n");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeStudent();
        assertTrue("Should notify code not found", outContent.toString().contains("Not found student have an student code: ca999"));
    } //sai logic test

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
        String actualResult = rawOutput.substring(prompt.length());
        assertEquals("List student is empty.", actualResult);
    } //sai logic test

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
