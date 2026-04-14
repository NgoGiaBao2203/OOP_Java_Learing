/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package staffmanagementapplication;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author giaba
 */
public class StaffManagement {

    private ArrayList<Staff> staffList;
    Scanner scanner;

    public StaffManagement() {
        staffList = new ArrayList<Staff>();
        scanner = new Scanner(System.in);

    }

    public void SearchStaffByStaffID() {
        String keyword = inputString("Please enter staff ID: ");
        ArrayList<Staff> result = findStaffForSearch(keyword);
        if(!result.isEmpty()) {
            displayTable(result);
        } else {
             System.out.println("Not found " + keyword);
        }
    }

    public void addStaff() {
        Staff staff = new Staff("", "", "", "");
        staff.setStaffID(generateStaffID());
        staff.setFullName(inputString("Please enter full name: "));
        staff.setPosition(inputString("Please enter position: "));
        staff.setHourlyWage(inputString("Please enter hourly wage: "));
        staffList.add(staff);
        System.out.println("Add staff successfully!");
    }
    public void displayTable(ArrayList<Staff> staffList) {
        if (!staffList.isEmpty()) {
            System.out.println("+---------+----------------------+----------------------+---------------+");
            System.out.println("|   Code  |      Fullname        |       Position       |  Hourly Wage  |");
            System.out.println("+---------+----------------------+----------------------+---------------+");
            for (Staff staff : staffList) {
                System.out.printf(
                        "|%-9s|%-22s|%-22s|%-15s|\n",
                        staff.getStaffID(),
                        staff.getFullName(),
                        staff.getPosition(),
                        staff.getHourlyWage()
                );
            }
            System.out.println("+---------+----------------------+----------------------+---------------+");
        } else {
            System.out.println("Not found!");
        }
    }

    public void displayTable() {
        if (!staffList.isEmpty()) {
            System.out.println("+---------+----------------------+----------------------+---------------+");
            System.out.println("|   Code  |      Fullname        |       Position       |  Hourly Wage  |");
            System.out.println("+---------+----------------------+----------------------+---------------+");
            for (Staff staff : staffList) {
                System.out.printf(
                        "|%-9s|%-22s|%-22s|%-15s|\n",
                        staff.getStaffID(),
                        staff.getFullName(),
                        staff.getPosition(),
                        staff.getHourlyWage()
                );
            }
            System.out.println("+---------+----------------------+----------------------+---------------+");
        } else {
            System.out.println("List of student is emplty!");
        }
    }

    private ArrayList<Staff> findStaffForSearch(String StaffID) {
        ArrayList<Staff> StaffListFound = new ArrayList<>();
        for (Staff staff : staffList) {
            if (staff.getStaffID().toLowerCase().contains(StaffID)) {
                StaffListFound.add(staff);
            }
        }
        return StaffListFound;
    }

    private String generateStaffID() {
        int currentNumber = 100;
        String staffID = "NV" + currentNumber;
        while (staffIDDuplicated(staffID)) {
            currentNumber++;
            if (currentNumber > 999) {
                System.out.println("The system has reached the maximum staff limit(NV999)");
            }
            staffID = "NV" + currentNumber;
        }
        System.out.println("Staff ID: " + staffID);
        return staffID;
    }

    private boolean staffIDDuplicated(String staffID) {
        for (Staff staff : staffList) {
            if (staff != null && staff.getStaffID() != null && staff.getStaffID().equals(staffID)) {
                return true;
            }
        }
        return false;
    }

    private String inputString(String title) {
        String input;
        System.out.print(title);
        input = scanner.nextLine().trim().toLowerCase();
        return input;
    }
}
