/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package staffmanagementapplication;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class shows a menu to edit staff information.
 *
 * @author giaba
 */
public class SubMenuForEdit {

    Scanner scanner;
    StaffManagement staffManagement;
    private final String NUMBER_REGEX = "^\\d+$";
    Pattern NUMER_PATTERN;

    /**
     * Creates a new menu for editing staff.
     * * @param sharedStaffManagement The main staff management system.
     */
    public SubMenuForEdit(StaffManagement sharedStaffManagement) {
        staffManagement = sharedStaffManagement;
        scanner = new Scanner(System.in);
        NUMER_PATTERN = Pattern.compile(NUMBER_REGEX);
    }

    /**
     * Shows the menu and runs the user's choice. 
     * It loops until the user chooses to go back (option 4).
     */
    public void SubmenuForEditOptions() {
        Boolean flag;
        Integer selectedOption;
        String choice;
        Boolean choiceValidate;
        do {
            flag = true;
            SubmenuOptions();
            choice = inputChoice();
            choiceValidate = validateChoice(choice);
            if (choiceValidate) {
                flag = true;
                selectedOption = Integer.parseInt(choice);
                switch (selectedOption) {
                    case 1:
                        staffManagement.editFullName();
                        flag = true;
                        break;
                    case 2:
                        staffManagement.editPosition();
                        flag = true;
                        break;
                    case 3:
                        staffManagement.editHourlyWage();
                        flag = true;
                        break;
                    case 4:
                        flag = false; // Stop the loop and go back
                        break;
                    default:
                        flag = true;
                        break;
                }
            }
        } while (flag);
    }

    /**
     * Checks if the user typed a correct number (1, 2, 3, or 4).
     * * @param choice The text the user typed.
     * @return true if the choice is good, false if it is bad.
     */
    private Boolean validateChoice(String choice) {
        Matcher numberMatcher = NUMER_PATTERN.matcher(choice);
        if (!numberMatcher.matches()) {
            System.out.println("Please enter a number (1-4)");
            return false;
        } else {
            int numberChoice = Integer.parseInt(choice);
            if (numberChoice >= 1 && numberChoice <= 4) {
                return true;
            } else {
                System.out.println("Please choose a number between 1 and 4");
                return false;
            }
        }
    }

    /**
     * Asks the user to type their choice.
     * * @return The text the user typed.
     */
    private String inputChoice() {
        String choice;
        System.out.print("Please enter your option edit: ");
        choice = scanner.nextLine();
        return choice;
    }

    /**
     * Prints the menu choices on the screen.
     */
    private void SubmenuOptions() {
        System.out.println("======================================");
        System.out.println("|              EDIT OPTIONS          |");
        System.out.println("======================================");
        System.out.println("| 1. Edit Full Name                  |");
        System.out.println("| 2. Edit Position                   |");
        System.out.println("| 3. Edit Hourly Wage                |");
        System.out.println("| 4. Back to main menu               |");
        System.out.println("======================================");
    }

}