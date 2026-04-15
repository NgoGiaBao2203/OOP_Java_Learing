/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package staffmanagementapplication;

/**
 *
 * @author giaba
 */
public class Staff {

    private String staffID;
    private String fullName;
    private String position;
    private String hourlyWage;
    private double totalWorkingHours;

    public Staff(String staffID, String fullName, String position, String hourlyWage, double totalWorkingHours) {
        this.staffID = staffID;
        this.fullName = fullName;
        this.position = position;
        this.hourlyWage = hourlyWage;
        this.totalWorkingHours = totalWorkingHours;
    }

    public double getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(double totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(String hourlyWage) {
        this.hourlyWage = hourlyWage;
    }
}
