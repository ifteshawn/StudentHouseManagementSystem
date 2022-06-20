/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Ifte
 */
public class Employee extends User {
    
    private ArrayList<Maintenance> jobs;

    public Employee(int ID, String userName, String firstName, String lastName, String phone, String emailID, String userType, String password) {
        super(ID, userName, firstName, lastName, phone, emailID, userType, password);
        this.jobs = new ArrayList<>();
    }

    public ArrayList<Maintenance> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Maintenance> jobs) {
        this.jobs = jobs;
    }

}
