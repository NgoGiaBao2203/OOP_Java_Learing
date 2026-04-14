/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package StaffManagementTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import staffmanagementapplication.Staff;
import staffmanagementapplication.StaffManagement;

/**
 *
 * @author giaba
 */
public class StaffManagementTest {

    private StaffManagement instance;
      String simulatedUserInput = "Nguyen Van Truong\n"
            + "Tro ly\n"
            + "25\n";

    public StaffManagementTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        instance = new StaffManagement();
        instance.staffList.add(new Staff("NV100", "Ngo Gia Bao", "Thu Ngan", "30"));
        instance.staffList.add(new Staff("NV101", "Tran Van An", "BA", "40"));
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testAdd() {
        
    }
}
