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

    public ArrayList<Staff> staffList;
    Scanner scanner;

    public StaffManagement() {
        staffList = new ArrayList<Staff>();
        scanner = new Scanner(System.in);
    }
    
    public void editHourlyWage() {
        if (!staffList.isEmpty()) {
            String StaffID = inputString("Please enter staff ID to edit: ");
            Staff staffToEdit = findStaffByStaffID(StaffID);
            if (staffToEdit != null) {
                System.out.println("Current hourly wage: " + staffToEdit.getHourlyWage());
                String newWage = inputString("Enter new hourly wage: ");
                staffToEdit.setHourlyWage(newWage);
                System.out.println("Update hourly wage successfully!");
            } else {
                System.out.println("Not found staff " + StaffID);
            }
        } else {
            System.out.println("List staff is empty");
        }
    }

    public void editPosition() {
        if (!staffList.isEmpty()) {
            String StaffID = inputString("Please enter Staff ID to edit: ");
            Staff staffToEdit = findStaffByStaffID(StaffID);
            if (staffToEdit != null) {
                System.out.println("Current Position: " + staffToEdit.getPosition());
                String newPosition = inputString("Enter new position: ");
                staffToEdit.setPosition(newPosition);
                System.out.println("Update position successfully!");
            } else {
                System.out.println("Not found staff " + StaffID);
            }
        } else {
            System.out.println("List staff is empty");
        }
    }

    public void editFullName() {
        if (!staffList.isEmpty()) {
            String StaffID = inputString("Please enter Staff ID to edit: ");
            Staff staffToEdit = findStaffByStaffID(StaffID);
            if (staffToEdit != null) {
                System.out.println("Current full Name: " + staffToEdit.getFullName());
                String newName = inputString("Enter new full Name: ");
                staffToEdit.setFullName(newName);
                System.out.println("Update full Name successfully!");
            } else {
                System.out.println("Not found staff " + StaffID);
            }
        } else {
            System.out.println("List staff is empty");
        }
    }

    public void removeAllStaff() {
        if (!staffList.isEmpty()) {
            staffList.removeAll(staffList);
            System.out.println("Remove all staff successfully");
        } else {
            System.out.println("List staff empty");
        }
    }

    public void removeMultipleStaffByStaffID() {
        if (!staffList.isEmpty()) {
            String StaffIDs = inputString("Please enter multiple staff ID: ");
            String[] idsToRemove = StaffIDs.split("\\s+");
            removeMultipleIDs(idsToRemove);
            System.out.println("Removed multiple staff successfully");
        } else {
            System.out.println("List staff is empty");
        }
    }

    public void removeStaffByStaffID() {
        if (!staffList.isEmpty()) {
            String StaffID = inputString("Please enter staff ID: ");
            Staff staffToRemove = findStaffByStaffID(StaffID);
            if (staffToRemove != null) {
                staffList.remove(staffToRemove);
                System.out.println("Removed staff successfully");
            } else {
                System.out.println("Not found staff ID ");
            }
        } else {
            System.out.println("List staff is empty");
        }
    }

    public void SearchStaffByStaffID() {
        String keyword = inputString("Please enter staff ID: ");
        ArrayList<Staff> result = findStaffForSearch(keyword);
        if (!result.isEmpty()) {
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
            System.out.println("|    ID   |      Fullname        |       Position       |  Hourly Wage  |");
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
            System.out.println("|    ID   |      Fullname        |       Position       |  Hourly Wage  |");
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

    private void removeMultipleIDs(String[] idsToRemove) {
        for (int i = 0; i < idsToRemove.length; i++) {
            String id = idsToRemove[i];
            if (id.isEmpty()) {
                continue;
            }
            Staff staffToRemove = findStaffByStaffID(id);
            if (staffToRemove != null) {
                staffList.remove(staffToRemove);
            } else {
                System.out.println("Not found staff ID: " + id.toUpperCase());
            }
        }
    }

    private Staff findStaffByStaffID(String staffCode) {
        for (Staff staff : staffList) {
            if (staff.getStaffID().equalsIgnoreCase(staffCode)) {
                return staff;
            }
        }
        return null;
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
