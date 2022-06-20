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
public class Room {
    private int roomID;
    private String room;
    private int buildingID;
    private ArrayList<MaintenanceItem> items;

    public Room(int roomID, String room, int buildingID, ArrayList<MaintenanceItem> items) {
        this.roomID = roomID;
        this.room = room;
        this.buildingID = buildingID;
        this.items = items;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public ArrayList<MaintenanceItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MaintenanceItem> items) {
        this.items = items;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", buildingID=" + buildingID + ", items=" + items + '}';
    }


    
}
