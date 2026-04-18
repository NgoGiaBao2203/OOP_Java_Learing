/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package staffmanagementapplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * @author giaba
 */
public class StaffManagement {

    public ArrayList<Staff> staffList;
    public ArrayList<Staff> staffSortedList;
    private final String STAFF_FILE = "staff_data.txt";
    private final Pattern ID_PATTERN = Pattern.compile("^NV\\d{3}$");
    private final Pattern SPACE_PATTERN = Pattern.compile("\\s+");
    private final Pattern VALID_NAME_PATTERN = Pattern.compile("^[\\p{L}\\s]+$");
    private final Pattern WAGE_PATTERN = Pattern.compile("^(?!0+(\\.0+)?$)\\d+(\\.\\d+)?$");
    private final HashMap<String, LocalDateTime> checkInRecords;
    public Scanner scanner;

    public StaffManagement() {
        staffList = new ArrayList<Staff>();
        staffSortedList = new ArrayList<>();
        checkInRecords = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    /**
     * Put staff names in order from A to Z.
     */
    public void sortName() {
        if (staffList == null || staffList.isEmpty()) {
            System.out.println("The staff list is empty. It cannot be sort");
        } else {
            staffSortedList = sortStaffList();
            System.out.println("Sorted by Name (A-Z) successfully");
            displayTable(staffSortedList);
        }
    }

    /**
     * Calculates the monthly salary for all staff members.
     */
    public void calculateMonthlySalary() {
        if (!staffList.isEmpty()) {
            LinkedHashMap<Staff, Double> calculatedData = new LinkedHashMap<>();
            for (Staff staff : staffList) {
                if (staff.getHourlyWage() != null && WAGE_PATTERN.matcher(staff.getHourlyWage()).matches()) {
                    double totalSalary = calculateSalaryForOneStaff(staff);
                    calculatedData.put(staff, totalSalary);
                } else {
                    calculatedData.put(staff, -1.0);
                }
            }
            displaySalaryTable(calculatedData);
        } else {
            System.out.println("List staff is empty");
        }
    }

    /**
     * Calculate money for one staff person.
     *
     * @return
     */
    public double calculateSalaryForOneStaff(Staff staff) {
        double wage = Double.parseDouble(staff.getHourlyWage());
        return wage * staff.getTotalWorkingHours();
    }

    /**
     * Save the time when staff starts work.
     */
    public void checkIn() {
        String staffID = inputString("Enter Staff ID to Check-in: ");
        Staff staff = findStaffByStaffID(staffID);
        if (staff != null) {
            if (checkInRecords.containsKey(staff.getStaffID())) {
                System.out.println("This staff has already checked in!");
                return;
            }
            checkInRecords.put(staff.getStaffID(), LocalDateTime.now());
            System.out.println(staff.getFullName() + " checked in successfully at " + LocalDateTime.now());
        } else {
            System.out.println("Not found staff " + staffID);
        }
    }

    /**
     * Save the time when staff stops work and find work hours for this session,
     * then add to total working hours of staff.
     */
    public void checkOut() {
        String staffID = inputString("Enter Staff ID to Check-out: ");
        Staff staff = findStaffByStaffID(staffID);
        if (staff != null) {
            if (checkInRecords.containsKey(staff.getStaffID())) {
                LocalDateTime inTime = checkInRecords.get(staff.getStaffID());
                LocalDateTime outTime = LocalDateTime.now();
                Duration duration = Duration.between(inTime, outTime);
                double secondsWorked = duration.toSeconds();
                double hoursWorked = secondsWorked / 3600.0;
                double newTotal = staff.getTotalWorkingHours() + hoursWorked;
                staff.setTotalWorkingHours(newTotal);
                checkInRecords.remove(staff.getStaffID());
                System.out.printf("%s checked out! Session: %.0f seconds (%.6f hours). Total: %.4f hours\n",
                        staff.getFullName(), secondsWorked, hoursWorked, newTotal);
            } else {
                System.out.println("This staff hasn't checked in yet!");
            }
        } else {
            System.out.println("Not found staff " + staffID);
        }
    }

    /**
     * Edit the hourly pay for a staff person.
     */
    public void editHourlyWage() {
        if (!staffList.isEmpty()) {
            String StaffID = inputString("Please enter staff ID to edit: ");
            Staff staffToEdit = findStaffByStaffID(StaffID);
            if (staffToEdit != null) {
                System.out.println("Current hourly wage: " + staffToEdit.getHourlyWage());
                String newWage = inputStringWage("Enter new hourly wage: ");
                staffToEdit.setHourlyWage(newWage);
                System.out.println("Update hourly wage successfully!");
            } else {
                System.out.println("Not found staff " + StaffID);
            }
        } else {
            System.out.println("List staff is empty");
        }
    }

    /**
     * Edit the position for a staff person.
     */
    public void editPosition() {
        if (!staffList.isEmpty()) {
            String StaffID = inputString("Please enter Staff ID to edit: ");
            Staff staffToEdit = findStaffByStaffID(StaffID);
            if (staffToEdit != null) {
                System.out.println("Current Position: " + staffToEdit.getPosition());
                String newPosition = inputPosition("Enter new position: ");
                staffToEdit.setPosition(newPosition);
                System.out.println("Update position successfully!");
            } else {
                System.out.println("Not found staff " + StaffID);
            }
        } else {
            System.out.println("List staff is empty");
        }
    }

    /**
     * Edit the full name for a staff person.
     */
    public void editFullName() {
        if (!staffList.isEmpty()) {
            String StaffID = inputString("Please enter Staff ID to edit: ");
            Staff staffToEdit = findStaffByStaffID(StaffID);
            if (staffToEdit != null) {
                System.out.println("Current full Name: " + staffToEdit.getFullName());
                String newName = inputFullName("Enter new full Name: ");
                staffToEdit.setFullName(newName);
                System.out.println("Update full Name successfully!");
            } else {
                System.out.println("Not found staff " + StaffID);
            }
        } else {
            System.out.println("List staff is empty");
        }
    }

    /**
     * Delete every staff person from the list.
     */
    public void removeAllStaff() {
        if (!staffList.isEmpty()) {
            staffList.removeAll(staffList);
            System.out.println("Remove all staff successfully");
        } else {
            System.out.println("List staff empty");
        }
    }

    /**
     * Delete many staff person using their IDs.
     */
    public void removeMultipleStaffByStaffID() {
        if (!staffList.isEmpty()) {
            System.out.print("Please enter multiple staff ID: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty!");
                return;
            }
            String[] idsToRemove = SPACE_PATTERN.split(input);
            removeMultipleIDs(idsToRemove);
            System.out.println("Removed multiple staff successfully");
        } else {
            System.out.println("List staff is empty");
        }
    }

    /**
     * Delete a staff person using their ID.
     */
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

    /**
     * Search for staff members by their IDs, allowing the user to input a
     * search keyword ID.
     */
    public void SearchStaffByStaffID() {
        String keyword = inputString("Please enter staff ID: ");
        ArrayList<Staff> result = findStaffForSearch(keyword);
        if (!result.isEmpty()) {
            displayTable(result);
        } else {
            System.out.println("Not found " + keyword);
        }
    }

    /**
     * Add a new staff person to the list.
     */
    public void addStaff() {
        String newID = generateStaffID();
        if (newID == null) {
            return;
        }
        Staff staff = new Staff("", "", "", "", 0.0);
        staff.setStaffID(newID);
        staff.setFullName(inputFullName("Please enter full name: "));
        staff.setPosition(inputPosition("Please enter position: "));
        staff.setHourlyWage(inputStringWage("Please enter hourly wage: "));
        staffList.add(staff);
        System.out.println("Add staff successfully!");
    }

    /**
     * Displays a salary table that shows the staff members IDs, full names,
     * hourly wages, total working hours, and total salaries.
     *
     * @param salaryData
     */
    public void displaySalaryTable(LinkedHashMap<Staff, Double> salaryData) {
        System.out.println("+---------+----------------------+---------------+---------------+----------------+");
        System.out.println("|   ID    |      Fullname        |  Hourly Wage  |  Total Hours  |  Total Salary  |");
        System.out.println("+---------+----------------------+---------------+---------------+----------------+");
        for (Map.Entry<Staff, Double> entry : salaryData.entrySet()) {
            Staff staff = entry.getKey();
            Double totalSalary = entry.getValue();
            if (totalSalary != -1.0) {
                System.out.printf("|%-9s|%-22s|%-15s|%-15.2f|%-16.2f|\n",
                        staff.getStaffID(),
                        staff.getFullName(),
                        staff.getHourlyWage(),
                        staff.getTotalWorkingHours(),
                        totalSalary
                );
            } else {
                System.out.printf("|%-9s|%-22s|%-15s|%-15.2f|%-16s|\n",
                        staff.getStaffID(),
                        staff.getFullName(),
                        staff.getHourlyWage(),
                        staff.getTotalWorkingHours(),
                        "ERROR WAGE"
                );
            }
        }
        System.out.println("+---------+----------------------+---------------+---------------+----------------+");
    }

    /**
     * Displays a table of staff members information, including their IDs, full
     * names, positions, hourly wages, and total working hours.
     *
     * @param staffList
     */
    public void displayTable(ArrayList<Staff> staffList) {
        if (!staffList.isEmpty()) {
            System.out.println("+---------+----------------------+----------------------+---------------+---------------+");
            System.out.println("|   ID    |       Fullname       |       Position       |  Hourly Wage  |  Total Hours  |");
            System.out.println("+---------+----------------------+----------------------+---------------+---------------+");
            for (Staff staff : staffList) {
                System.out.printf(
                        "|%-9s|%-22s|%-22s|%-15s|%-15.2f|\n",
                        staff.getStaffID(),
                        staff.getFullName(),
                        staff.getPosition(),
                        staff.getHourlyWage(),
                        staff.getTotalWorkingHours()
                );
            }
            System.out.println("+---------+----------------------+----------------------+---------------+---------------+");
        } else {
            System.out.println("Not found!");
        }
    }

    /**
     * Displays a table of all staff members information, including their IDs,
     * full names, positions, hourly wages, and total working hours.
     */
    public void displayTable() {
        if (!staffList.isEmpty()) {
            System.out.println("+---------+----------------------+----------------------+---------------+---------------+");
            System.out.println("|   ID    |       Fullname       |       Position       |  Hourly Wage  |  Total Hours  |");
            System.out.println("+---------+----------------------+----------------------+---------------+---------------+");
            for (Staff staff : staffList) {
                System.out.printf(
                        "|%-9s|%-22s|%-22s|%-15s|%-15.2f|\n",
                        staff.getStaffID(),
                        staff.getFullName(),
                        staff.getPosition(),
                        staff.getHourlyWage(),
                        staff.getTotalWorkingHours()
                );
            }
            System.out.println("+---------+----------------------+----------------------+---------------+---------------+");
        } else {
            System.out.println("List of student is emplty!");
        }
    }

    /**
     * Get staff data from the file.
     *
     * @throws IOException IOException If file error happens.
     */
    public void loadStaffFile() throws IOException {
        File f = new File(this.STAFF_FILE);
        if (!f.exists()) {
            return;
        }
        ArrayList<Staff> tempStaffList = new ArrayList<>();
        HashMap<String, LocalDateTime> tempCheckInRecords = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            if (line == null || line.trim().isEmpty()) {
                return;
            }
            int numberOfStaff = 0;
            try {
                numberOfStaff = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("There is an error in the file. Please check the file.");
                return;
            }
            for (int i = 0; i < numberOfStaff; i++) {
                String staffID = br.readLine();
                String fullName = br.readLine();
                String position = br.readLine();
                String hourlyWage = br.readLine();
                String hoursStr = br.readLine();
                if (!isValidID(staffID) || !isValidFullName(fullName)
                        || !isValidPosition(position) || !isValidHourlyWage(hourlyWage)
                        || !isValidTotalWorkingHours(hoursStr)) {
                    System.out.println("There is an error in the file. Please check the file.");
                    return;
                }
                double hours = Double.parseDouble(hoursStr.trim());
                tempStaffList.add(new Staff(staffID, fullName, position, hourlyWage, hours));
            }
            line = br.readLine();
            if (line != null && !line.trim().isEmpty()) {
                int numberOfCheckIns = 0;
                try {
                    numberOfCheckIns = Integer.parseInt(line.trim());
                } catch (NumberFormatException e) {
                    System.out.println("There is an error in the file. Please check the file.");
                    return;
                }
                for (int i = 0; i < numberOfCheckIns; i++) {
                    String staffID = br.readLine();
                    String timeStr = br.readLine();

                    if (staffID != null && timeStr != null) {
                        try {
                            tempCheckInRecords.put(staffID, LocalDateTime.parse(timeStr));
                        } catch (java.time.format.DateTimeParseException e) {
                            System.out.println("There is an error in the file. Please check the file.");
                            return;
                        }
                    }
                }
            }
            this.staffList.clear();
            this.checkInRecords.clear();
            this.staffList.addAll(tempStaffList);
            this.checkInRecords.putAll(tempCheckInRecords);
            System.out.println("Data loaded successfully");
        } catch (Exception e) {
            System.out.println("There is an error in the file. Please check the file.");
        }
    }

    /**
     * Saves staff data to file.
     *
     * @throws IOException
     */
    public void saveStaffFile() throws IOException {
        try (FileWriter fw = new FileWriter(this.STAFF_FILE)) {
            fw.write(this.staffList.size() + "\n");
            for (Staff staff : this.staffList) {
                fw.write(staff.getStaffID() + "\n");
                fw.write(staff.getFullName() + "\n");
                fw.write(staff.getPosition() + "\n");
                fw.write(staff.getHourlyWage() + "\n");
                fw.write(staff.getTotalWorkingHours() + "\n");
            }
            fw.write(this.checkInRecords.size() + "\n");
            for (Map.Entry<String, LocalDateTime> entry : this.checkInRecords.entrySet()) {
                fw.write(entry.getKey() + "\n");
                fw.write(entry.getValue().toString() + "\n");
            }
            System.out.println("Data saved successfully");
        }
    }

    /**
     * Check if this ID is already in the list.
     *
     * @param staffID
     * @return
     */
    private boolean staffIDDuplicated(String staffID) {
        for (Staff staff : staffList) {
            if (staff != null && staff.getStaffID() != null && staff.getStaffID().equals(staffID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the full name is correct. It needs at least two words.
     *
     * * @param name The full name of the staff.
     * @return true if the name is correct, false if not.
     */
    private boolean isValidFullName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (!VALID_NAME_PATTERN.matcher(name).matches()) {
            return false;
        }
        String[] words = SPACE_PATTERN.split(name.trim());
        if (words.length < 2) {
            return false;
        }
        for (String word : words) {
            if (word.length() >= 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the job position is correct. It needs at least two letters.
     *
     * * @param position The job position of the staff.
     * @return true if the position is correct, false if not.
     */
    private boolean isValidPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            return false;
        }
        if (!VALID_NAME_PATTERN.matcher(position).matches()) {
            return false;
        }
        return SPACE_PATTERN.matcher(position).replaceAll("").length() >= 2;
    }

    /**
     * Checks if the staff ID is in the right format.
     *
     * * @param id The ID of the staff.
     * @return true if the ID is correct, false if not.
     */
    private boolean isValidID(String id) {
        return id != null && ID_PATTERN.matcher(id).matches();
    }

    /**
     * Checks if the hourly wage is a correct number format.
     *
     * * @param wage The hourly wage of the staff.
     * @return true if the wage is correct, false if not.
     */
    private boolean isValidHourlyWage(String wage) {
        if (wage == null || wage.trim().isEmpty()) {
            return false;
        }
        return WAGE_PATTERN.matcher(wage.trim()).matches();
    }

    /**
     * Checks if the working hours are correct. Hours cannot be less than zero.
     *
     * * @param hoursStr The total working hours.
     * @return true if the hours are correct, false if not.
     */
    private boolean isValidTotalWorkingHours(String hoursStr) {
        if (hoursStr == null || hoursStr.trim().isEmpty()) {
            return false;
        }
        try {
            double hours = Double.parseDouble(hoursStr.trim());
            return hours >= 0.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Sort the staff list by name.
     *
     * @return
     */
    private ArrayList<Staff> sortStaffList() {
        ArrayList<Staff> termList = new ArrayList<>(staffList);
        termList.sort((staff1, staff2) -> {
            String name1 = getLastName(staff1.getFullName());
            String name2 = getLastName(staff2.getFullName());
            return name1.compareToIgnoreCase(name2);
        });
        return termList;
    }

    /**
     * Find many staff people by typing part of an ID.
     *
     * @param StaffID
     * @return
     */
    private ArrayList<Staff> findStaffForSearch(String StaffID) {
        ArrayList<Staff> StaffListFound = new ArrayList<>();
        for (Staff staff : staffList) {
            if (staff.getStaffID().toUpperCase().contains(StaffID)) {
                StaffListFound.add(staff);
            }
        }
        return StaffListFound;
    }

    /**
     * Finds a staff member in the list by their ID.
     *
     * @param staffID
     * @return
     */
    private Staff findStaffByStaffID(String staffID) {
        for (Staff staff : staffList) {
            if (staff.getStaffID().equalsIgnoreCase(staffID)) {
                return staff;
            }
        }
        return null;
    }

    /**
     * Delete many staff IDs from the list.
     *
     * @param idsToRemove
     */
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

    /**
     * Get the last part of a name, which is used for sorting the staff list by
     * name.
     *
     * @param fullName
     * @return
     */
    private String getLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }
        String[] parts = SPACE_PATTERN.split(fullName.trim());
        return parts[parts.length - 1];
    }

    /**
     * Create a new ID for staff (like NV100, NV101).
     *
     * @return
     */
    private String generateStaffID() {
        int currentNumber = 100;
        String staffID = "NV" + currentNumber;
        while (staffIDDuplicated(staffID)) {
            currentNumber++;
            if (currentNumber > 999) {
                System.out.println("The system has reached the maximum staff limit(NV999)");
                return null;
            }
            staffID = "NV" + currentNumber;
        }
        System.out.println("Staff ID: " + staffID);
        return staffID;
    }

    /**
     * Ask the user to type the pay amount.
     *
     * @param title
     * @return
     */
    private String inputStringWage(String title) {
        while (true) {
            System.out.print(title);
            String input = scanner.nextLine().trim();
            if (isValidHourlyWage(input)) {
                return input;
            }
            System.out.println("Error: Invalid wage format! Please enter a valid positive number.");
        }
    }

    /**
     * Ask the user to type something and return it as a string.
     *
     * @param title
     * @return
     */
    private String inputString(String title) {
        String input;
        while (true) {
            System.out.print(title);
            input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty! Please try again.");
                continue;
            }
            if (ID_PATTERN.matcher(input).matches()) {
                return input;
            } else {
                System.out.println("Error: Input contains invalid special characters.");
            }
        }
    }

    /**
     * Prompts the user to enter a full name and applies strict validation.
     *
     * * @param title The message displayed to prompt the user.
     * @return A valid full name string in lowercase.
     */
    private String inputFullName(String title) {
        while (true) {
            System.out.print(title);
            String input = scanner.nextLine().trim();
            if (isValidFullName(input.toLowerCase())) {
                return input;
            }
            System.out.println("Error: Invalid name. Must contain at least 2 words, no special characters,"
                    + "and no single letters like.");
        }
    }

    /**
     * Prompts the user to enter a job position with flexible validation.
     *
     * * @param title The message displayed to prompt the user.
     * @return A valid job position string.
     */
    private String inputPosition(String title) {
        while (true) {
            System.out.print(title);
            String input = scanner.nextLine().trim();
            if (isValidPosition(input.toLowerCase())) {
                return input;
            }
            System.out.println("Error: Invalid position. Must contain at least 2 character and no special characters.");
        }
    }
}
