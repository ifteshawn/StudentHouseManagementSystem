/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ifte
 */
public abstract class User {  //abstract class for student, employee and management to inherit from
    private final int ID;
    private String userName;
    private String firstName;
    private String lastName;
    private String phone;
    private String emailID;
    private String userType;
    private String password;

    public User(int ID, String userName, String firstName, String lastName, String phone, String emailID, String userType, String password) {
        this.ID = ID;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.emailID = emailID;
        this.userType = userType;
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", firstName=" + firstName + 
                ", lastName=" + lastName + ", phone=" + phone + ", emailID=" +
                emailID + ", userType=" + userType + ", password=" + password + '}';
    }
    
}
