/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package StaffManagementTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import staffmanagementapplication.Staff;
import staffmanagementapplication.StaffManagement;

/**
 *
 * @author giaba
 */
public class StaffManagementTest {

    private StaffManagement instance;
    String simulatedUserInput = "nguyen van truong\n"
            + "tro ly\n"
            + "25\n";

    public StaffManagementTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new StaffManagement();
        // Cập nhật Constructor có 5 tham số (totalWorkingHours = 0.0)
        instance.staffList.add(new Staff("NV100", "Ngo Gia Bao", "Thu Ngan", "30", 0.0));
        instance.staffList.add(new Staff("NV101", "Tran Van An", "BA", "40", 0.0));
    }

    @After
    public void tearDown() {
        instance = null;
    }

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
        String expectedResult = "+---------+----------------------+----------------------+---------------+---------------+\n"
                + "|   ID    |        Fullname      |       Position       |  Hourly Wage  |  Total Hours  |\n"
                + "+---------+----------------------+----------------------+---------------+---------------+\n"
                + String.format("|%-9s|%-22s|%-22s|%-15s|%-15.2f|\n", "NV100", "Ngo Gia Bao", "Thu Ngan", "30", 0.0)
                + String.format("|%-9s|%-22s|%-22s|%-15s|%-15.2f|\n", "NV101", "Tran Van An", "BA", "40", 0.0)
                + "+---------+----------------------+----------------------+---------------+---------------+";
        String rawOutput = outContent.toString();
        String expected = expectedResult.replace("\r\n", "\n").trim();
        String actual = rawOutput.replace("\r\n", "\n").trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testAddStaff() {
        System.out.println("Test: addStaff");
        int sizeBefore = instance.staffList.size();
        simulateInput(simulatedUserInput);
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        instance.addStaff();
        System.setOut(originalOut);
        assertEquals("Staff list size should increase by 1", sizeBefore + 1, instance.staffList.size());
        Staff lastStaff = instance.staffList.get(instance.staffList.size() - 1);
        assertEquals("nguyen van truong", lastStaff.getFullName());
        assertEquals("tro ly", lastStaff.getPosition());
        assertEquals("25", lastStaff.getHourlyWage());
    }

    @Test
    public void testEditFullName() {
        System.out.println("Test: editFullName");
        String simulatedInput = "nv100\nngo gia bao edited\n";
        simulateInput(simulatedInput);
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        instance.editFullName();
        System.setOut(originalOut);
        Staff editedStaff = instance.staffList.get(0);
        assertEquals("Name should be updated", "ngo gia bao edited", editedStaff.getFullName());
    }

    @Test
    public void testRemoveStaffByStaffID() {
        System.out.println("Test: removeStaffByStaffID");
        simulateInput("nv100\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeStaffByStaffID();
        System.setOut(originalOut);
        String rawOutput = outContent.toString();
        String prompt = "Please enter staff ID: ";
        String actualResult = rawOutput.substring(prompt.length()).trim();
        String expectedResult = "Removed staff successfully";
        assertEquals(expectedResult, actualResult);
        assertEquals(1, instance.staffList.size());
        Staff remainingStaff = instance.staffList.get(0);
        assertEquals("Tran Van An", remainingStaff.getFullName());
    }

    @Test
    public void testSearchStaffByStaffID() {
        System.out.println("Test: SearchStaffByStaffID");
        simulateInput("nv100\n");

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.SearchStaffByStaffID();
        System.setOut(originalOut);
        String expectedResult = "+---------+----------------------+----------------------+---------------+---------------+\n"
                + "|   ID    |        Fullname      |       Position       |  Hourly Wage  |  Total Hours  |\n"
                + "+---------+----------------------+----------------------+---------------+---------------+\n"
                + String.format("|%-9s|%-22s|%-22s|%-15s|%-15.2f|\n", "NV100", "Ngo Gia Bao", "Thu Ngan", "30", 0.0)
                + "+---------+----------------------+----------------------+---------------+---------------+";
        String prompt = "Please enter staff ID: ";
        String rawOutput = outContent.toString();
        String actualResult = rawOutput.substring(rawOutput.indexOf(prompt) + prompt.length());
        String expected = expectedResult.replace("\r\n", "\n").trim();
        String actual = actualResult.replace("\r\n", "\n").trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateSalaryForOneStaff() {
        System.out.println("Test: calculateSalaryForOneStaff");
        Staff testStaff = new Staff("NV999", "Test", "Test", "50", 10.5);
        double expectedSalary = 50 * 10.5;
        double actualSalary = instance.calculateSalaryForOneStaff(testStaff);
        assertEquals(expectedSalary, actualSalary, 0.0001);
    }

    //--------------------------------- abnormal case ------------------------------------//
    @Test
    public void testDisplayTableEmptyList() {
        System.out.println("Abnormal Test: Display with empty list");
        instance.staffList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.displayTable();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        assertEquals("List of student is emplty!", rawOutput);
    }

    @Test
    public void testRemoveStaffByStaffIDNotFound() {
        System.out.println("Abnormal Test: Remove staff not found");
        simulateInput("nv999\n");

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeStaffByStaffID();
        System.setOut(originalOut);
        String rawOutput = outContent.toString();
        String prompt = "Please enter staff ID: ";
        String actualResult = rawOutput.substring(prompt.length()).trim();
        assertEquals("Not found staff ID", actualResult);
    }
}
