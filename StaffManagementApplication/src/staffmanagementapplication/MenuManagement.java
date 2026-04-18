/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package staffmanagementapplication;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author giaba
 */
public class MenuManagement {

    Scanner scanner;
    StaffManagement staffManagement;
    SubMenuForRemove submenuForRemove;
    SubMenuForEdit submenuForEdit;

    private final String NUMBER_REGEX = "^\\d+$";
    Pattern NUMER_PATTERN;

    /**
     * Setup menu management, load data from file and initialize sub menu for
     * remove and edit.
     *
     */
    public MenuManagement() {
        staffManagement = new StaffManagement();
        submenuForRemove = new SubMenuForRemove(staffManagement);
        submenuForEdit = new SubMenuForEdit(staffManagement);
        scanner = new Scanner(System.in);
        NUMER_PATTERN = Pattern.compile(NUMBER_REGEX);
        try {
            staffManagement.loadStaffFile();
        } catch (Exception e) {
            System.out.println("System failed to load data: " + e.getMessage());
        }
    }

    /**
     * Run the menu loop and do what the user chooses.
     */
    public void menuLogic() {
        Boolean flag;
        Integer selectedOption;
        String choice;
        Boolean choiceValidate;
        do {
            flag = true;
            menuOptions();
            choice = inputChoice();
            choiceValidate = validateChoice(choice);
            if (choiceValidate) {
                flag = true;
                selectedOption = Integer.parseInt(choice);
                switch (selectedOption) {
                    case 1:
                        staffManagement.displayTable();
                        flag = true;
                        break;
                    case 2:
                        staffManagement.addStaff();
                        flag = true;
                        break;
                    case 3:
                        staffManagement.SearchStaffByStaffID();
                        flag = true;
                        break;
                    case 4:
                        submenuForEdit.SubmenuForEditOptions();
                        flag = true;
                        break;
                    case 5:
                        submenuForRemove.SubmenuForRemoveOptions();
                        flag = true;
                        break;
                    case 6:
                        staffManagement.sortName();
                        flag = true;
                        break;
                    case 7:
                        staffManagement.calculateMonthlySalary();
                        flag = true;
                        break;
                    case 8:
                        staffManagement.checkIn();
                        flag = true;
                        break;
                    case 9:
                        staffManagement.checkOut();
                        flag = true;
                        break;
                    case 10:
                        System.out.println("Exit application");
                        try {
                            staffManagement.saveStaffFile();
                        } catch (Exception e) {
                            System.out.println("System failed to save data: " + e.getMessage());
                        }
                        flag = false;
                        break;
                    default:
                        flag = true;
                        break;
                }
            }
        } while (flag);
    }

    /**
     * Run the menu loop and do what the user chooses.
     *
     * @param choice choice The text the user typed in the console.
     * @return True if the number is good, False if not.
     */
    private Boolean validateChoice(String choice) {
        Matcher numberMatcher = NUMER_PATTERN.matcher(choice);
        if (!numberMatcher.matches()) {
            System.out.println("Please enter a number (1-10)");
            return false;
        } else {
            int numberChoice = Integer.parseInt(choice);
            if (numberChoice >= 1 && numberChoice <= 10) {
                return true;
            } else {
                System.out.println("Please choose a number between 1 and 10");
                return false;
            }
        }
    }

    /**
     * Ask the user to type a choice. r
     *
     * @return The text typed by the user
     */
    private String inputChoice() {
        String choice;
        System.out.print("Please enter  your option: ");
        choice = scanner.nextLine();
        return choice;
    }

    /**
     * Show the list of menu options on the screen.
     */
    private void menuOptions() {
        System.out.println("========================================");
        System.out.println("|       STAFF MANAGEMENT OPTIONS       |");
        System.out.println("========================================");
        System.out.println("| 1. Display all staff                 |");
        System.out.println("| 2. Add new staff                     |");
        System.out.println("| 3. Search staff                      |");
        System.out.println("| 4. Edit staff                        |");
        System.out.println("| 5. Remove staff                      |");
        System.out.println("| 6. sort by name                      |");
        System.out.println("| 7. Calculate monthly salary          |");
        System.out.println("| 8. Check-in                          |");
        System.out.println("| 9. Check-out                         |");
        System.out.println("| 10. Exit application and save file   |");
        System.out.println("========================================");
    }
}
