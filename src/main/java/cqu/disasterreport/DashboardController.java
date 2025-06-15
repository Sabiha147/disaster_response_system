/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cqu.disasterreport;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.IOException;
import javafx.fxml.FXML;




public class DashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    
     @FXML
    private void switchToCivilian() throws IOException {
        App.setRoot("civilian");
    }

    @FXML
    private void switchToManager() throws IOException {
        App.setRoot("manager");
    }

    @FXML
    private void switchToResponder() throws IOException {
        App.setRoot("responder");
    }
    
}
