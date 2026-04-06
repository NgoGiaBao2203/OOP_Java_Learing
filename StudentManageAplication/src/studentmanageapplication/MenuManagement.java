/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanageapplication;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author giaba
 */
public class MenuManagement {

    Scanner scanner;
    StudentManagement studentManagement;
    private final String NUMBER_REGEX = "^\\d+$";
    Pattern NUMER_PATTERN;

    public MenuManagement() {
        studentManagement = new StudentManagement();
        NUMER_PATTERN = Pattern.compile(NUMBER_REGEX);
        scanner = new Scanner(System.in);
    }

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
                        studentManagement.displayTable();
                        flag = true;
                        break;
                    case 2:
                        studentManagement.addStudent();
                        flag = true;
                        break;
                    case 3:
                        studentManagement.editStudent();
                        flag = true;
                        break;
                    case 4:
                        studentManagement.removeStudent();
                        flag = true;
                        break;
                    case 5:
                        studentManagement.SearchStudentByStudentCode();
                        flag = true;
                        break;
                    case 6:
                        studentManagement.sortName();
                        flag = true;
                        break;
                    case 7:
                        System.out.println("option7");
                        flag = false;
                        break;
                    default:
                        flag = true;
                        break;
                }
            }
        } while (flag);
    }

    private Boolean validateChoice(String choice) {
        Matcher numberMatcher = NUMER_PATTERN.matcher(choice);
        if (!numberMatcher.matches()) {
            System.out.println("Error choice! Please enter a number (1-5)");
            return false;
        } else {
            int numberChoice = Integer.parseInt(choice);
            if (numberChoice >= 1 && numberChoice <= 7) {
                return true;
            } else {
                System.out.println("Error: Please choose a number between 1 and 5.");
                return false;
            }
        }
    }

    private void menuOptions() {
        System.out.println("========================================");
        System.out.println("|           STUDENT MANAGE APP         |");
        System.out.println("========================================");
        System.out.println("| 1. Display all student               |");
        System.out.println("| 2. Add new student                   |");
        System.out.println("| 3. Edit student infomation           |");
        System.out.println("| 4. Remove student                    |");
        System.out.println("| 5. Search student by student code    |");
        System.out.println("| 6. sort by name                      |");
        System.out.println("| 7. Exit application                  |");
        System.out.println("========================================");
    }

    public String inputChoice() {
        String choice;
        System.out.print("Please enter  your otion: ");
        choice = scanner.nextLine();
        return choice;
    }
}
