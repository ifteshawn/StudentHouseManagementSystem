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
public class Building {
    private int buildingID;
    private String name;
    private ArrayList<Room> rooms;

    public Building(int buildingID, String name, ArrayList<Room> rooms) {
        this.buildingID = buildingID;
        this.name = name;
        this.rooms = rooms;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Building{" + "buildingID=" + buildingID + ", name=" + name + ", rooms=" + rooms + '}';
    }
    
}
