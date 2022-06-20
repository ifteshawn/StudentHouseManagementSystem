/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBUtility.DatabaseConnect;
import static controller.SHMSController.maintenanceList;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Maintenance;
import model.Maintenance.Status;

/**
 * FXML Controller class
 *
 * @author Ifte
 */
public class ManagementController implements Initializable {

    
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
    private ChoiceBox<String> empList;

    @FXML
    private RadioButton completed;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton requested;

    @FXML
    private RadioButton assigned;

    @FXML
    private RadioButton underReview;
    
    @FXML
    private Button generate;

    @FXML
    private ComboBox<Integer> taskList;   //to list all maintenance requests to select from
    
    ObservableList<Integer> tasks;        //observable list to allow setting values for maintenance details according the maintenance ID selected
    
    PreparedStatement st;
    


    @FXML  //this method sets the right values in each textfield according to the maintenance ID selected by the user
    void changeOption(ActionEvent event) {
        int maintID = taskList.getValue(); 
        String itemIDResult;
        try {
            //to get and set values for item ID field
            st = DatabaseConnect.getDbConnection().prepareStatement("Select * "
                    + "from maintenance where maintenanceID = ?");
            st.setString(1, String.valueOf(maintID));
            ResultSet rs = st.executeQuery();
            if (rs.next()){
            itemIDResult = rs.getString("itemID");
            itemID.setText(itemIDResult);
            
            
            //to get and set values for room and item fields
            PreparedStatement st1 = DatabaseConnect.getDbConnection().prepareStatement("Select * "
                    + "from maintenanceItem where itemID = ?");
            st1.setString(1, itemIDResult);
            ResultSet rs1 = st1.executeQuery();
            if (rs1.next()){
                item.setText(rs1.getString("item"));
                room.setText(rs1.getString("roomID"));
            }
            
            //to get and set values for buidling field
            st1 = DatabaseConnect.getDbConnection().prepareStatement("Select name "
                    + "from building b INNER JOIN room r ON b.buildingID = r.buildingID WHERE roomID = ?");
            st1.setString(1, itemIDResult);
            rs1 = st1.executeQuery();
            if (rs1.next()){
                building.setText(rs1.getString("name"));
            }
            
            //sets value for description field
            description.setText(rs.getString("description"));
            
            //ses value for assigned employee filed
            empList.setValue(rs.getString("employeeID"));
            
            //to get and set values for status of the maintenance request
            String assignRole = rs.getString("status");
                switch (assignRole) {
                    case "REQUESTED":
                        status.selectToggle(requested);
                        break;
                    case "UNDER_REVIEW":
                        status.selectToggle(underReview);
                        break;
                    case "ASSIGNED":
                        status.selectToggle(assigned);
                        break;
                    default:
                        status.selectToggle(completed);
                        break;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void onLogout(ActionEvent event) {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
    }

    //to makes changes to maintenance requests and save the details to database
    @FXML
    void onSave(ActionEvent event) {
        //setting values to each variable of the maintenance 
        String maintID = String.valueOf(taskList.getValue());
        String itmID = itemID.getText();
        String desc = description.getText();
        String stat;
        if(status.getSelectedToggle() == requested) {
            stat = "REQUESTED";
        }
        else if (status.getSelectedToggle() == underReview) {
            stat = "UNDER_REVIEW";
        }
        else if (status.getSelectedToggle() == assigned) {
            stat = "ASSIGNED";
        }
        else {
            stat = "COMPLETED";
        }
        
        String empID = empList.getValue();
        String mgmtID = String.valueOf(SHMSController.m.getID());
        
        //runs the update query with all variables to make changes to record in table in database 
        try {
            st = DatabaseConnect.getDbConnection().prepareStatement("UPDATE maintenance SET "
                    + "itemID = ?, description = ?, status = ?, managementID = ?,"
                    + "  employeeID = ? WHERE maintenanceID = ?;");
            st.setString(1, itmID);
            st.setString(2, desc);
            st.setString(3, stat);
            st.setString(4, mgmtID);
            st.setString(5, empID);
            st.setString(6, maintID);
            st.executeUpdate();
            //to get the students ID related to the maintenance
            PreparedStatement st1 = DatabaseConnect.getDbConnection().prepareStatement("Select studentID "
                    + "from maintenance where maintenanceID = ?");
            st1.setString(1, maintID);
            ResultSet r = st1.executeQuery();
            int stdID;
            if (r.next()) {
                stdID = Integer.parseInt(r.getString("studentID"));
                //creates new maintenance with changes implemented to old maintenance request
                Maintenance maintenance = new Maintenance(
                        Integer.parseInt(maintID),
                        Integer.parseInt(itmID),
                        desc,
                        Status.valueOf(stat),
                        Integer.parseInt(mgmtID),
                        stdID, 
                        Integer.parseInt(empID));
                //replaces the old maintenance items with newly updated maintenance
                maintenanceList.set(maintenance.getMaintenanceID()-1, maintenance);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    //opens a new window to print all maintenance requests and their details
    @FXML
    void onGenerate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MaintenanceReport.fxml"));
                   Parent root = loader.load();

                   Scene scene = new Scene(root);
                   Stage stage = new Stage();
                   stage.setTitle("Generate Report");
                   stage.setScene(scene);
                   stage.show();
                   
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  //on login, add the employees list and maintenance list to choose from
        //to add all maintenance employees to choicebox to allow for assigning employee to task
        try {
            Statement st1 = DatabaseConnect.getDbConnection().createStatement();
            ResultSet result = st1.executeQuery("select employeeID from employee");
            while (result.next()){
                empList.getItems().add(result.getString("employeeID"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        //to add all maintenance ID to list to be displayed in combox box
        tasks = taskList.getItems();
        maintenanceList.forEach((m) -> {
            tasks.add(m.getMaintenanceID());
            taskList.setItems(tasks);
            
        });

    }    
    
}
