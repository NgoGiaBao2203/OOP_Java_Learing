/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package StaffManagementTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
    public void testSaveAndLoadStaffFile() throws IOException {
        System.out.println("Test 12: Save and Load Staff File completely");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.saveStaffFile();
        String saveOutput = outContent.toString().trim();
        assertEquals("Data saved successfully", saveOutput);
        instance.staffList.clear();
        assertEquals(0, instance.staffList.size());
        outContent.reset();
        instance.loadStaffFile();
        System.setOut(originalOut);
        String loadOutput = outContent.toString().trim();
        assertEquals("Data loaded successfully", loadOutput);
        assertEquals(2, instance.staffList.size());
        Staff staff1 = instance.staffList.get(0);
        assertEquals("NV100", staff1.getStaffID());
        assertEquals("Vo Minh Duy", staff1.getFullName());
        assertEquals("Thu Ngan", staff1.getPosition());
        assertEquals("30", staff1.getHourlyWage());
        assertEquals(0.0, staff1.getTotalWorkingHours(), 0.0001);
        Staff staff2 = instance.staffList.get(1);
        assertEquals("NV101", staff2.getStaffID());
        assertEquals("Tran Quoc Ba", staff2.getFullName());
        assertEquals("BA", staff2.getPosition());
        assertEquals("40", staff2.getHourlyWage());
    }

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
    public void testremoveMultipleStaffByStaffID() {
        System.out.println("Test 4.1: remove staff by staff ID");
        simulateInput("nv100 nv101\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeMultipleStaffByStaffID();
        System.setOut(originalOut);
        String rawOutput = outContent.toString();
        String prompt = "Please enter multiple staff ID: ";
        String actualResult = rawOutput.substring(prompt.length()).trim();
        String expectedResult = "Removed multiple staff successfully";
        assertEquals(expectedResult, actualResult);
        assertEquals(0, instance.staffList.size());
    }

    @Test
    public void testremoveAllStaffByStaff() {
        System.out.println("Test 4.2: remove staff by staff ID");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeAllStaff();
        System.setOut(originalOut);
        String actualResult = outContent.toString().trim();
        String expectedResult = "Remove all staff successfully";
        assertEquals(expectedResult, actualResult);
        assertEquals(0, instance.staffList.size());
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

    @Test
    public void testCalculateMonthlySalary_Success() {
        System.out.println("Test 7: Calculate Monthly Salary Normal");
        instance.staffList.get(0).setTotalWorkingHours(10.0);
        instance.staffList.get(1).setTotalWorkingHours(5.5);
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.calculateMonthlySalary();
        System.setOut(originalOut);
        String rawOutput = outContent.toString();
        String expectedOutput = "+---------+----------------------+---------------+---------------+----------------+\n"
                + "|   ID    |      Fullname        |  Hourly Wage  |  Total Hours  |  Total Salary  |\n"
                + "+---------+----------------------+---------------+---------------+----------------+\n"
                + "|NV100    |Vo Minh Duy           |30             |10.00          |300.00          |\n"
                + "|NV101    |Tran Quoc Ba          |40             |5.50           |220.00          |\n"
                + "+---------+----------------------+---------------+---------------+----------------+";
        String expected = expectedOutput.replace("\r\n", "\n").trim();
        String actual = rawOutput.replace("\r\n", "\n").trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testCheckIn_Success() {
        System.out.println("Test 8: Check-in successfully");
        simulateInput("nv100\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.checkIn();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        String actualMessage = rawOutput;
        if (rawOutput.contains(" at ")) {
            actualMessage = rawOutput.substring(0, rawOutput.indexOf(" at ")) + " at [TIME]";
        }
        String expectedMessage = "Enter Staff ID to Check-in: Vo Minh Duy checked in successfully at [TIME]";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testCheckOut() throws InterruptedException {
        System.out.println("Test 9: Check-out successfully");
        simulateInput("nv100\n");
        instance.checkIn();
        Thread.sleep(100);
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        simulateInput("nv100\n");
        instance.checkOut();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        String actualMessage = rawOutput.replaceAll("\\d+(\\.\\d+)?", "[NUM]");
        String expectedMessage = "Enter Staff ID to Check-out: Vo Minh Duy checked out! Session: [NUM] seconds ([NUM] hours). Total: [NUM] hours";
        assertEquals(expectedMessage, actualMessage);
    }

    //--------------------------------- abnormal case ------------------------------------//
    @Test
    public void testDisplayTableEmptyList() {
        System.out.println("Test 1: Display with empty list");
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
        System.out.println("Test 2: Add staff with invalid then valid wage");
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
    public void testEditFullNameEmptyList() {
        System.out.println("Test 3: Edit full name with empty list");
        instance.staffList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.editFullName();
        System.setOut(originalOut);
        assertEquals("List staff is empty", outContent.toString().trim());
    }

    @Test
    public void testEditFullName_NotFound() {
        System.out.println("Test 3.1: Edit full name with invalid ID");
        simulateInput("nv999\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.editFullName();
        System.setOut(originalOut);
        String expectedMessage = "Please enter Staff ID to edit: Not found staff nv999";
        assertEquals(expectedMessage, outContent.toString().trim());
    }

    @Test
    public void testEditPositionEmptyList() {
        System.out.println("Test 4: Edit position with empty list");
        instance.staffList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.editPosition();
        System.setOut(originalOut);
        assertEquals("List staff is empty", outContent.toString().trim());
    }

    @Test
    public void testEditPositionNotFound() {
        System.out.println("Test 4.1: Edit position with invalid ID");
        simulateInput("nv999\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.editPosition();
        System.setOut(originalOut);
        String expectedMessage = "Please enter Staff ID to edit: Not found staff nv999";
        assertEquals(expectedMessage, outContent.toString().trim());
    }

    @Test
    public void testEditHourlyWage_EmptyList() {
        System.out.println("Test 5: Edit hourly wage with empty list");
        instance.staffList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.editHourlyWage();
        System.setOut(originalOut);
        assertEquals("List staff is empty", outContent.toString().trim());
    }

    @Test
    public void testEditHourlyWageNotFound() {
        System.out.println("Test 5.1: Edit hourly wage with invalid ID");
        simulateInput("nv999\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.editHourlyWage();
        System.setOut(originalOut);
        String expectedMessage = "Please enter staff ID to edit: Not found staff nv999";
        assertEquals(expectedMessage, outContent.toString().trim());
    }

    @Test
    public void testRemoveStaffByStaffIDNotFound() {
        System.out.println("Test 6: Remove staff not found");
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

    @Test
    public void testSearchStaffByStaffIDNotFound() {
        System.out.println("Test 7: Search staff not found");
        simulateInput("nv999\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.SearchStaffByStaffID();
        System.setOut(originalOut);
        String expectedMessage = "Please enter staff ID: Not found nv999";
        assertEquals(expectedMessage, outContent.toString().trim());
    }

    @Test
    public void testRemoveStaffByStaffIDEmptyList() {
        System.out.println("Test 8: Remove staff by ID with empty list");
        instance.staffList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeStaffByStaffID();
        System.setOut(originalOut);
        String actualResult = outContent.toString().trim();
        String expectedResult = "List staff is empty";
        assertEquals(expectedResult, actualResult);
        assertEquals(0, instance.staffList.size());
    }

    @Test
    public void testRemoveMultipleStaffByStaffIDEmptyList() {
        System.out.println("Test 8.1: Remove multiple staff with empty list");
        instance.staffList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeMultipleStaffByStaffID();
        System.setOut(originalOut);
        assertEquals("List staff is empty", outContent.toString().trim());
        assertEquals(0, instance.staffList.size());
    }

    @Test
    public void testRemoveAllStaffEmptyList() {
        System.out.println("Test 8.2: Remove all staff when list is empty");
        instance.staffList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.removeAllStaff();
        System.setOut(originalOut);
        String actualResult = outContent.toString().trim();
        String expectedResult = "List staff empty";
        assertEquals(expectedResult, actualResult);
        assertEquals(0, instance.staffList.size());
    }

    @Test
    public void testCalculateMonthlySalaryEmptyList() {
        System.out.println("Test 9: Calculate Monthly Salary with empty list");
        instance.staffList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.calculateMonthlySalary();
        System.setOut(originalOut);
        assertEquals("List staff is empty", outContent.toString().trim());
    }

    @Test
    public void testCheckInNotFound() {
        System.out.println("Test 10: Check-in with invalid ID");
        simulateInput("nv999\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.checkIn();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        String expectedMessage = "Enter Staff ID to Check-in: Not found staff nv999";
        assertEquals(expectedMessage, rawOutput);
    }

    @Test
    public void testCheckInEmptyList() {
        System.out.println("Test 10.1: Check-in with empty list");
        instance.staffList.clear();
        simulateInput("nv100\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.checkIn();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        String expectedMessage = "Enter Staff ID to Check-in: Not found staff nv100";
        assertEquals(expectedMessage, rawOutput);
    }

    @Test
    public void testCheckOutNotCheckedInYet() {
        System.out.println("Test 11: Check-out without check-in");
        simulateInput("nv101\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.checkOut();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        String expectedMessage = "Enter Staff ID to Check-out: This staff hasn't checked in yet!";
        assertEquals(expectedMessage, rawOutput);
    }

    @Test
    public void testCheckOutNotFound() {
        System.out.println("Test 11.1: Check-out with invalid ID");
        simulateInput("nv999\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.checkOut();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        String expectedMessage = "Enter Staff ID to Check-out: Not found staff nv999";
        assertEquals(expectedMessage, rawOutput);
    }

    @Test
    public void testCheckOut_EmptyList() {
        System.out.println("Test 11.2: Check-out with empty list");
        instance.staffList.clear();
        simulateInput("nv100\n");
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.checkOut();
        System.setOut(originalOut);
        String rawOutput = outContent.toString().trim();
        String expectedMessage = "Enter Staff ID to Check-out: Not found staff nv100";
        assertEquals(expectedMessage, rawOutput);
    }

    @Test
    public void testLoadStaffFileFileNotFound() throws IOException {
        System.out.println("Test 12: Load file when staff_data.txt doesn't exist");
        File file = new File("staff_data.txt");
        if (file.exists()) {
            file.delete();
        }
        instance.staffList.clear();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        instance.loadStaffFile();
        System.setOut(originalOut);
        assertEquals("", outContent.toString().trim());
        assertEquals(0, instance.staffList.size());
    }
}
