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
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;


public class CivilianController implements Initializable {
    
    @FXML
    private ComboBox<String> disasterTypeCombo;

    @FXML
    private TextField locationField;

    @FXML
    private ComboBox<String> severityCombo;

    @FXML
    private TextField reporterNameField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button submitButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Populate ComboBoxes
        disasterTypeCombo.getItems().addAll("Fire", "Flood", "Earthquake", "Storm", "Cyclone", "Other");
        severityCombo.getItems().addAll("High", "Medium", "Low");
    } 
    
    
    
     @FXML
    private void submitReport() {
        String disasterType = disasterTypeCombo.getValue();
        String location = locationField.getText();
        String severity = severityCombo.getValue();
        String reporterName = reporterNameField.getText();
        String description = descriptionArea.getText();
        String reportedTime = LocalDateTime.now().toString();

        // Validate required fields
        if (disasterType == null || location.isEmpty() || severity == null || reporterName.isEmpty() || description.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        // Build command string
        String command = String.format("ADD_DISASTER|%s|%s|%s|%s|%s|%s",
                disasterType, location, severity, reporterName, description, reportedTime);

        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send command to server
            writer.println(command);

            // Read response
            String response = reader.readLine();

            // Show server response in popup
            showAlert(response);

            // Clear form
            disasterTypeCombo.setValue(null);
            locationField.clear();
            severityCombo.setValue(null);
            reporterNameField.clear();
            descriptionArea.clear();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to send disaster report to server.");
        }
    }

    
    
    @FXML
    private void goBack() throws IOException {
        App.setRoot("login");
    }
    
    
     private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Report Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    
}
