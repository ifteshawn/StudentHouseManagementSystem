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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Maintenance;

/**
 * StudentControllerClass
 *
 * @author Ifte
 */
public class StudentController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField studentID;

    @FXML
    private TextArea description;

    @FXML
    private Button request;

    @FXML
    private Button logout;
    
    @FXML
    private ChoiceBox<String> itemList;
    
    PreparedStatement st;
    Maintenance m;
    
    
    //logout button action to logout
    @FXML
    void onLogout(ActionEvent event) {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
    }
    
    //tp process and add student maintenance requerst to the database
    @FXML
    void onRequest(ActionEvent event) {
        //creates a new maintenance object taking inputs from student from text field.
        m = new Maintenance(SHMSController.maintenanceCount + 1 ,Integer.parseInt(itemList.getValue()), description.getText(), Integer.parseInt(studentID.getText()));
        //to add newly created maintenance to the maintenance list held in the SHMSController
        maintenanceList.add(m);
        //increments the number of maintenance requests in the maintenance list
        SHMSController.maintenanceCount++;
        try {
            DatabaseConnect.insertMaintenance.setInt(1, m.getMaintenanceID());
            DatabaseConnect.insertMaintenance.setInt(2, m.getMaintenanceItemID());
            DatabaseConnect.insertMaintenance.setString(3, m.getDescription());
            DatabaseConnect.insertMaintenance.setString(4, String.valueOf(m.getStatus()));
            DatabaseConnect.insertMaintenance.setString(5, null);
            DatabaseConnect.insertMaintenance.setInt(6, m.getStudentID());
            DatabaseConnect.insertMaintenance.setString(7, null);
            DatabaseConnect.insertMaintenance.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //adds the items related to the student's room to the item list visible to student
        try {
            st = DatabaseConnect.getDbConnection().prepareStatement("Select itemID from maintenanceItem i "
                    + "INNER JOIN room r ON i.roomID = r.roomID "
                    + "INNER JOIN student s ON r.roomID = s.roomID "
                    + "WHERE studentID = ? "
                    + "ORDER BY itemID;");
            st.setString(1, String.valueOf(SHMSController.s.getID()));
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String item = rs.getString("itemID");
                itemList.getItems().add(item);
            }
            //sets the student id in the student id field and 
            studentID.setText(String.valueOf(SHMSController.s.getID()));
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        
    }    
    
}
