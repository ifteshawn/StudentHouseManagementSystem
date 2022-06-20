/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBUtility.DatabaseConnect;
import static controller.SHMSController.maintenanceList;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Maintenance;

/**
 * FXML Controller class
 *
 * @author Ifte
 */
public class EmployeeController implements Initializable {

    @FXML
    private Button save;

    @FXML
    private Button logout;

    @FXML
    private TextField itemID;

    @FXML
    private TextField item;

    @FXML
    private TextField room;

    @FXML
    private TextField building;

    @FXML
    private TextArea description;

    @FXML
    private RadioButton completed;  //to mark a task completed if needed

    @FXML
    private ComboBox<Integer> assignedList;  //to list all maintenance task assigned to the current employee by management
    
    ObservableList<Integer> assigned;   //to reflect details of task selected by employee on the text fields
    String jobStatus;   //holds information about if a job is completed or not which is used to change completed toggle button on or off

    @FXML  //log out button functionality
    void onLogout(ActionEvent event) {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
    }
    
    
    @FXML  //to reflect details of current task selected by employee on the text fields
    void onSelect(ActionEvent event) {
        int maintID = assignedList.getValue();
        String itemIDResult;
        try {
            //query to get and set the maintenance itemID
            PreparedStatement st = DatabaseConnect.getDbConnection().prepareStatement("Select * "
                    + "from maintenance where maintenanceID = ?");
            st.setString(1, String.valueOf(maintID));
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                itemIDResult = rs.getString("itemID");
                itemID.setText(itemIDResult);
                //query to get and set the maintenance item and room
                PreparedStatement st1 = DatabaseConnect.getDbConnection().prepareStatement("Select * "
                        + "from maintenanceItem where itemID = ?");
                st1.setString(1, itemIDResult);
                ResultSet rs1 = st1.executeQuery();
                if (rs1.next()) {
                    item.setText(rs1.getString("item"));
                    room.setText(rs1.getString("roomID"));
                }

                //query to get and set the maintenance item's designated building
                st1 = DatabaseConnect.getDbConnection().prepareStatement("Select name "
                        + "from building b INNER JOIN room r ON b.buildingID = r.buildingID WHERE roomID = ?");
                st1.setString(1, itemIDResult);
                rs1 = st1.executeQuery();
                if (rs1.next()) {
                    building.setText(rs1.getString("name"));
                }
                
                description.setText(rs.getString("description"));
                
                //toggles the completed radio button if status of the task is completed
                if (rs.getString("status").compareTo("COMPLETED") == 0) {
                    completed.setSelected(true);
                }
                else {
                    completed.setSelected(false);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML  //saves all changes made to maintenance requests by user on clicking save button
    void onSave(ActionEvent event) {
        //gets all values from text fields and if a job is marked completed or not
        String maintID = String.valueOf(assignedList.getValue());
        String itmID = itemID.getText();
        String desc = description.getText();
        
        if (completed.isSelected() == true){
            jobStatus = "COMPLETED";
        }
        //
        String empID = String.valueOf(SHMSController.em.getID());
        
        try {
            //query to update description and status of task
            if ("COMPLETED".equals(jobStatus)){
                PreparedStatement st = DatabaseConnect.getDbConnection().prepareStatement("UPDATE maintenance SET "
                        + "description = ?, status = ?"
                        + " WHERE maintenanceID = ?;");
                st.setString(1, desc);
                st.setString(2, jobStatus);
                st.setString(3, maintID);
                st.executeUpdate();
            }
            //query to update only description of task
            else {
                PreparedStatement st1 = DatabaseConnect.getDbConnection().prepareStatement("UPDATE maintenance SET "
                        + "description = ?"
                        + " WHERE maintenanceID = ?;");
                st1.setString(1, desc);
                st1.setString(2, maintID);
                st1.executeUpdate();
            }
            //
            PreparedStatement st2 = DatabaseConnect.getDbConnection().prepareStatement("Select * "
                    + "from maintenance where maintenanceID = ?");
            st2.setString(1, maintID);
            ResultSet r = st2.executeQuery();
            int stdID;
            int mgmtID;
            if (r.next()) {
                stdID = Integer.parseInt(r.getString("studentID"));
                //creates new maintenance with changes implemented to old maintenance request
                mgmtID = Integer.parseInt(r.getString("managementID"));
                jobStatus = r.getString("status");
                Maintenance maintenance = new Maintenance(
                        Integer.parseInt(maintID),
                        Integer.parseInt(itmID),
                        desc,
                        Maintenance.Status.valueOf(jobStatus),
                        mgmtID,
                        stdID, 
                        Integer.parseInt(empID));
                //replaces the old maintenance items with newly updated maintenance
                maintenanceList.set(maintenance.getMaintenanceID()-1, maintenance);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try { //on login, adds assigned maintenance  to list to choose from
            int maintID;
            assigned = assignedList.getItems();
            PreparedStatement st = DatabaseConnect.getDbConnection().prepareStatement("Select * from maintenance"
                    + " where employeeID = ?");
            st.setString(1, String.valueOf(SHMSController.em.getID()));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                maintID = Integer.parseInt(rs.getString("maintenanceID"));
                assigned.add(maintID);
            }
            assignedList.setItems(assigned);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }    
    
}
