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
public class Student extends User {
    private int roomID;

    public Student(int ID, int roomID, String userName, String firstName, String lastName, String phone, String emailID, String userType, String password) {
        super(ID, userName, firstName, lastName, phone, emailID, userType, password);
        this.roomID = roomID;
    }

   
    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    
    @Override
    public String toString() {
        return super.toString() + "Student{" + "roomID=" + roomID + '}';
    }
}
