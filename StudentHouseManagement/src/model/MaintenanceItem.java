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
public class MaintenanceItem {

    private int itemID;
    private String item;
    private int roomID;

    public MaintenanceItem(int itemID, String item, int roomID) {
        this.itemID = itemID;
        this.item = item;
        this.roomID = roomID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    @Override
    public String toString() {
        return "Maintenance Item{" + "itemID=" + itemID + ", item=" + item + ", roomID=" + roomID + '}';
    }

}
