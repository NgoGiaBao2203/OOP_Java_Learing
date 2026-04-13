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

    public Staff(String staffID, String fullName, String position, String hourlyWage) {
        this.staffID = staffID;
        this.fullName = fullName;
        this.position = position;
        this.hourlyWage = hourlyWage;
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




