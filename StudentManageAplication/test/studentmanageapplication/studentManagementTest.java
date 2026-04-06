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
import static org.junit.Assert.*;

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
        // Khởi tạo mới trước mỗi test để dữ liệu không bị lẫn lộn
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

    @Test
    public void testDisplayTable() {
        System.out.println("Test: displayTable");

        java.io.PrintStream originalOut = System.out;
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
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
    public void testRemoveStudent_ByCode() {
        System.out.println("Test: removeStudent (Option 'code')");
        simulateInput("code\nca123\nexit\n");
        instance.removeStudent();
        assertEquals("Student list size should be 1", 1, instance.studentList.size());
        assertEquals("Remaining student should be CA456", "CA456", instance.studentList.get(0).getStudentCode());
    }

    @Test
    public void testRemoveStudent_All() {
        System.out.println("Test: removeStudent (Option 'all')");
        simulateInput("all\nexit\n");
        instance.removeStudent();
        assertTrue("Student list should be empty", instance.studentList.isEmpty());
    }

    @Test
    public void testSearchStudentByStudentCode() {
        System.out.println("Test: SearchStudentByStudentCode");
        java.io.PrintStream originalOut = System.out;
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
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
        java.io.PrintStream originalOut = System.out;
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        instance.sortName();
        System.setOut(originalOut);
        String output = outContent.toString();
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

    @Test
    public void testSortName_EmptyList() {
        System.out.println("Abnormal Test: sortName with empty list");
        instance.studentList.clear(); // Đảm bảo list trống

        // Chặn luồng output để kiểm tra thông báo
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        instance.sortName();

        assertTrue("Should notify that list is empty",
                outContent.toString().contains("The student list is empty"));
        assertTrue("Sorted list should be empty", instance.studentSortedList.isEmpty());
    }

    //--------------------------------- abnormal case ------------------------------------//
    @Test
    public void testAddInvalid() {
        String badInput = "Gia Bao\nbaogmail.com\nbao@@gmail.com\ngiabao@gmail.com\nPass123\n";
        simulateInput(badInput);
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        instance.addStudent();
        String output = outContent.toString();
        String errorMsg = "Invalid email format";
        int errorCount = (output.split(errorMsg, -1).length) - 1;
        assertTrue(output.contains(errorMsg));
        assertEquals(2, errorCount);
        Student s = instance.studentList.get(instance.studentList.size() - 1);
        assertEquals("gia bao", s.getFullName());
        assertEquals("giabao@gmail.com", s.getEmail());
        assertEquals("pass123", s.getPassword());
        assertTrue(s.getStudentCode().startsWith("CA"));
    }

    @Test
    public void testRemoveStudentInvalidOption() {
        System.out.println("Abnormal Test: removeStudent with invalid menu option");
        simulateInput("abc\nexit\n");
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        instance.removeStudent();
        assertTrue("Should notify invalid option",outContent.toString().contains("Invalid option!"));
    }

    @Test
    public void testDisplayTableEmptyList() {
        System.out.println("Abnormal Test: displayTable with empty list");
        instance.studentList.clear();
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        instance.displayTable();
        assertTrue("Should notify list is empty", outContent.toString().contains("List of student is emplty"));
    }

    @Test
    public void testEditStudentNotFound() {
        System.out.println("Abnormal Test: editStudent with non-existent code");
        simulateInput("CA999\n");
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        instance.editStudent();
        assertTrue("Should notify student not found", outContent.toString().contains("Not found student have student code: ca999"));
    }

    @Test
    public void testSearchStudentNoResult() {
        System.out.println("Abnormal Test: SearchStudentByStudentCode with no result");
        simulateInput("NOT_EXIST\n");
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        instance.SearchStudentByStudentCode();
        assertTrue("Should notify not found", outContent.toString().contains("Not found not_exist"));
    }

    @Test
    public void testSortNameEmptyList() {
        System.out.println("Abnormal Test: sortName with empty list");
        instance.studentList.clear();
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        instance.sortName();
        assertTrue("Should notify that list is empty", outContent.toString().contains("The student list is empty"));
        assertTrue("Sorted list should be empty", instance.studentSortedList.isEmpty());
    }
}
