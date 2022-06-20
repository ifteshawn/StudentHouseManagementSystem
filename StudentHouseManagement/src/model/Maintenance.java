/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.SHMSController;

/**
 *
 * @author Ifte
 */
public class Maintenance {
    public enum Status {
        REQUESTED, UNDER_REVIEW, ASSIGNED, COMPLETED
    };
    
    private int maintenanceID;
    private int maintenanceItemID;
    private String description;
    private Status status;
    private int managementID;
    private final int studentID;
    private int employeeID;
    

    //contructor for when a student requests a new maintenane 
    public Maintenance(int maintenanceID,int maintenanceItemID, String description, int studentID) {
        this.maintenanceID = maintenanceID;
        this.maintenanceItemID = maintenanceItemID;
        this.description = description;
        this.status = Status.REQUESTED;
        this.studentID = studentID;
    }
    
    //constructor for reading maintenance requests in from database
    public Maintenance(int maintenanceID, int maintenanceItemID, String description, Status status, int managementID, int studentID, int employeeID) {
        this.maintenanceID = maintenanceID;
        this.maintenanceItemID = maintenanceItemID;
        this.description = description;
        this.status = status;
        this.managementID = managementID;
        this.studentID = studentID;
        this.employeeID = employeeID;
    }

    public int getMaintenanceID() {
        return maintenanceID;
    }

    public void setMaintenanceID(int maintenanceID) {
        this.maintenanceID = maintenanceID;
    }

    public int getMaintenanceItemID() {
        return maintenanceItemID;
    }

    public void setMaintenanceItemID(int maintenanceItemID) {
        this.maintenanceItemID = maintenanceItemID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getManagementID() {
        return managementID;
    }

    public void setManagementID(int managementID) {
        this.managementID = managementID;
    }

    public int getStudentID() {
        return studentID;
    }


    @Override
    public String toString() {
        return "Maintenance: " + "\nMaintenanceID: " + getMaintenanceID() + "\nMaintenanceItemID: " + getMaintenanceItemID() + "\nStudentID: " + getStudentID() + "\nDescription: " + getDescription() + "\nStatus: " + getStatus() + "\nAssigned EmployeeID: " + getEmployeeID() + "\n";
    }
    
}
