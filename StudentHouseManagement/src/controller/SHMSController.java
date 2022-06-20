/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBUtility.DatabaseConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import model.Maintenance.Status;

/**
 *
 * @author Ifte
 */
public class SHMSController implements Initializable {

    @FXML // fx:id="userName"
    private TextField userName;

    @FXML // fx:id="password"
    private TextField pass;

    @FXML // fx:id="userType"
    private ChoiceBox<String> userType; //lists type of users to select from; student, management and employee

    @FXML
    private Button login;

    static DatabaseConnect dbConnect;  //reference to the database connection which all other subsequent classes will use.
    static Student s;
    static Employee em;
    static Management m;

    static List<Maintenance> maintenanceList;  //reference to list that hold all maintenance requests
    static List<Employee> employeeList;        //reference to list holding all employees 
    public static int maintenanceCount;        //tracks the number of maintenance requested and is also used to derive maintenance id when creating new maintenance object
    PreparedStatement st;

    //implements the login functionality, opens appropriate window based of user type after validating username and password
    @FXML
    void onLogin(ActionEvent event) throws IOException {
        String user = userName.getText();
        String password = pass.getText();
        String usertype = userType.getValue();
        String ms = "Please enter the correct Username and Password OR select the correct User Type.";  //message to display if uncorrect credentials are used to login
        String person;  // to determine the type of user

        if (usertype.compareTo("Student") == 0) {
            person = "Student";
        } else if (usertype.compareTo("Employee") == 0) {
            person = "Employee";
        } else {
            person = "Management";
        }

        try {
            //run query to extract user name and password from the usertype's table
            st = DatabaseConnect.getDbConnection().prepareStatement("Select * "
                    + "from " + person + " where userName=? and password=?");
            st.setString(1, user);
            st.setString(2, password);
            st.executeQuery();
            ResultSet rs = st.executeQuery();
            //if username and password exist in the table then creates new instance of the person and logs in opening the right window for the type of user
            if (rs.next()) {
                if (person.compareTo("Student") == 0) {
                    s = new Student(rs.getInt("studentID"), rs.getInt("roomID"), rs.getString("userName"), rs.getString("firstName"),
                            rs.getString("lastName"), rs.getString("phone"), rs.getString("emailID"), rs.getString("userType"), rs.getString("password"));
                } else if (person.compareTo("Employee") == 0) {
                    em = new Employee(rs.getInt("employeeID"), rs.getString("userName"), rs.getString("firstName"),
                            rs.getString("lastName"), rs.getString("phone"), rs.getString("emailID"), rs.getString("userType"), rs.getString("password"));
                } else {
                    m = new Management(rs.getInt("managementID"), rs.getString("userName"), rs.getString("firstName"),
                            rs.getString("lastName"), rs.getString("phone"), rs.getString("emailID"), rs.getString("userType"), rs.getString("password"));
                }
                //opens new window for the user
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + person + ".fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("User: " + person);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Invalid Credentials.");
                alert.setContentText(ms);
                alert.show();
            }
            userName.clear();
            pass.clear();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userType.getItems().add("Management");
        userType.getItems().add("Employee");
        userType.getItems().add("Student");
        userType.setValue("Management");
        dbConnect = new DatabaseConnect();   //to establish database connection as programs is run
        maintenanceList = new LinkedList<>(); //creates new list to store all maintenance requests
        
        try { //runs database query to download all maintenance requests, creates their instances and add each to the maintenance list
            st = DatabaseConnect.getDbConnection().prepareStatement("Select * "
                    + "from maintenance");
            ResultSet rs = st.executeQuery();
            int empID;
            int mgmtID;
            while (rs.next()) {
                if ("null".equals(rs.getString("managementID"))) {
                    mgmtID = 0;
                } else {
                    mgmtID = Integer.parseInt(rs.getString("managementID"));
                }
                if ("null".equals(rs.getString("employeeID"))) {
                    empID = 0;
                } else {
                    empID = rs.getInt("employeeID");
                }
                //creates new maintenance as a maintenance is read from maintenance table in database
                Maintenance maint = new Maintenance(rs.getInt("maintenanceID"),
                        rs.getInt("itemID"),
                        rs.getString("description"),
                        Status.valueOf(rs.getString("status")),
                        mgmtID,
                        rs.getInt("studentID"),
                        empID);
                maintenanceList.add(maint);  //adds to maintenance list
                maintenanceCount++;          //increments the number of maintenance requests as new maintenance is added

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
