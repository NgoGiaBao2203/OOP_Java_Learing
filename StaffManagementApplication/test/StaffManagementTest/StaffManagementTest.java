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
        instance.staffList.add(new Staff("NV100", "Vo Minh Duy", "Thu Ngan", "30", 0.0));
        instance.staffList.add(new Staff("NV101", "Tran Quoc Ba", "BA", "40", 0.0));
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
        System.out.println("Test 1: display table");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.displayTable();
        System.setOut(originalOut);
        String expectedResult = "+---------+----------------------+----------------------+---------------+---------------+\n"
                + "|   ID    |       Fullname       |       Position       |  Hourly Wage  |  Total Hours  |\n"
                + "+---------+----------------------+----------------------+---------------+---------------+\n"
                + String.format("|%-9s|%-22s|%-22s|%-15s|%-15.2f|\n", "NV100", "Vo Minh Duy", "Thu Ngan", "30", 0.0)
                + String.format("|%-9s|%-22s|%-22s|%-15s|%-15.2f|\n", "NV101", "Tran Quoc Ba", "BA", "40", 0.0)
                + "+---------+----------------------+----------------------+---------------+---------------+";
        String rawOutput = outContent.toString();
        String expected = expectedResult.replace("\r\n", "\n").trim();
        String actual = rawOutput.replace("\r\n", "\n").trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testAddStaff() {
        System.out.println("Test 2: add staff");
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
        System.out.println("Test 3: edit full name");
        String simulatedInput = "nv100\nngo gia bao edited\n";
        simulateInput(simulatedInput);
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        instance.editFullName();
        System.setOut(originalOut);
        Staff editedStaff = instance.staffList.get(0);
        assertEquals("ngo gia bao edited", editedStaff.getFullName());
    }

    @Test
    public void testEditPosition() {
        System.out.println("Test 3.1: edit position");
        String simulatedInput = "nv101\nleader\n";
        simulateInput(simulatedInput);
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        instance.editPosition();
        System.setOut(originalOut);
        Staff editedStaff = instance.staffList.get(1);
        assertEquals("leader", editedStaff.getPosition());
    }

    @Test
    public void testEditHourlyWage() {
        System.out.println("Test 3.2: edit hourly wage");
        String simulatedInput = "nv101\n10\n";
        simulateInput(simulatedInput);
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        instance.editHourlyWage();
        System.setOut(originalOut);
        Staff editedStaff = instance.staffList.get(1);
        assertEquals("10", editedStaff.getHourlyWage());
    }

    @Test
    public void testRemoveStaffByStaffID() {
        System.out.println("Test 4: remove staff by staff ID");
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
        assertEquals("Tran Quoc Ba", remainingStaff.getFullName());
    }

    @Test
    public void testSearchStaffByStaffID() {
        System.out.println("Test 5: Search staff by staff ID");
        simulateInput("nv100\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.SearchStaffByStaffID();
        System.setOut(originalOut);
        String expectedResult = "+---------+----------------------+----------------------+---------------+---------------+\n"
                + "|   ID    |       Fullname       |       Position       |  Hourly Wage  |  Total Hours  |\n"
                + "+---------+----------------------+----------------------+---------------+---------------+\n"
                + String.format("|%-9s|%-22s|%-22s|%-15s|%-15.2f|\n", "NV100", "Vo Minh Duy", "Thu Ngan", "30", 0.0)
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
        System.out.println("Test 6: calculateSalaryForOneStaff");
        Staff testStaff = new Staff("NV999", "Test", "Test", "50", 10.5);
        double expectedSalary = 50 * 10.5;
        double actualSalary = instance.calculateSalaryForOneStaff(testStaff);
        assertEquals(expectedSalary, actualSalary, 0.0001);
    }

    //--------------------------------- abnormal case ------------------------------------//
    @Test
    public void testDisplayTableEmptyList() {
        System.out.println("Abnormal Test 1: Display with empty list");
        instance.staffList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.displayTable();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        assertEquals("List of student is emplty!", rawOutput);
        assertEquals(0, instance.staffList.size());
    }

    @Test
    public void testAddStaffInvalid() {
        System.out.println("Abnormal Test 2: Add staff with invalid then valid wage");
        simulateInput("Vo Trung Nguyen\nlao cong\n33a\n33\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.addStaff();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().replace("\r\n", "\n");
        String expectedOutput = "Staff ID: NV102\n"
                + "Please enter full name: "
                + "Please enter position: "
                + "Please enter hourly wage: "
                + "Invalid wage format! Please enter a valid positive number\n"
                + "Please enter hourly wage: "
                + "Add staff successfully!\n";
        assertEquals(expectedOutput, rawOutput);
        assertEquals(3, instance.staffList.size());
    }

    @Test
    public void testRemoveStaffByStaffIDNotFound() {
        System.out.println("Abnormal Test 3: Remove staff not found");
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
        assertEquals(2, instance.staffList.size());
    }
}
