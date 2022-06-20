/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.SHMSController.maintenanceList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ifte
 */
public class MaintenanceReportController implements Initializable {

    @FXML
    private TextArea reportArea; //textarea to print maintenance report
    
    @FXML
    private Button close;

    @FXML //closes report window
    private void onClose(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //to print all maintenance held in maintenance list
        maintenanceList.forEach((m) -> {
            reportArea.appendText("===================" + "\n");
            reportArea.appendText(m.toString() + "\n");
        });

    }
}
