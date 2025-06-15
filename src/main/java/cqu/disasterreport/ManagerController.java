package cqu.disasterreport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import dao.DisasterReportDAO;


public class ManagerController implements Initializable {

    @FXML
    private TableView<DisasterReportView> reportTable;

    @FXML
    private TableColumn<DisasterReportView, Integer> idColumn;

    @FXML
    private TableColumn<DisasterReportView, String> typeColumn;

    @FXML
    private TableColumn<DisasterReportView, String> locationColumn;

    @FXML
    private TableColumn<DisasterReportView, String> severityColumn;

    @FXML
    private TableColumn<DisasterReportView, String> reporterColumn;

    @FXML
    private TableColumn<DisasterReportView, String> descriptionColumn;

    @FXML
    private TableColumn<DisasterReportView, String> timeColumn;

    @FXML
    private TableColumn<DisasterReportView, String> handledColumn;
    
    @FXML
    private TableColumn<DisasterReportView, String> departmentColumn;

 
    @FXML
    private ComboBox<String> departmentCombo;


    private ObservableList<DisasterReportView> reportList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().disasterTypeProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        severityColumn.setCellValueFactory(cellData -> cellData.getValue().severityProperty());
        reporterColumn.setCellValueFactory(cellData -> cellData.getValue().reporterNameProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().reportedTimeProperty());
        handledColumn.setCellValueFactory(cellData -> cellData.getValue().handledProperty());
        
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().assignedDepartmentProperty());
       
         // ✅ Add departments to ComboBox
        departmentCombo.getItems().addAll("Fire Department", "Medical Unit", "Police", "Electricity Board", "Relief Team");

    
        loadReports();
    }

    private void loadReports() {
        reportList.clear();

        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            writer.println("GET_ALL_REPORTS");

            String line;
            while (!(line = reader.readLine()).equals("END_OF_REPORTS")) {
                String[] parts = line.split("\\|", -1);
                
                DisasterReportView report = new DisasterReportView(
                    Integer.parseInt(parts[0]),  // ID
                    parts[1],                    // Type
                    parts[2],                    // Location
                    parts[3],                    // Severity
                    parts[4],                    // Reporter
                    parts[5],                    // Description
                    parts[6],                    // Time
                    parts[7],                    // Handled
                    parts.length >= 9 ? parts[8] : "" // ✅ Assigned Department
                );

         

                reportList.add(report);
            }

            reportTable.setItems(reportList);
            
           

            // 🔴 Color-code rows based on severity
            reportTable.setRowFactory(tv -> new TableRow<DisasterReportView>() {
                @Override
                protected void updateItem(DisasterReportView item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setStyle("");
                    } else {
                        String severity = item.severityProperty().get().toLowerCase();
                        switch (severity) {
                            case "high":
                                setStyle("-fx-background-color: #ffcccc;"); // Light red
                                break;
                            case "medium":
                                setStyle("-fx-background-color: #fff5cc;"); // Light yellow
                                break;
                            case "low":
                                setStyle("-fx-background-color: #ccffcc;"); // Light green
                                break;
                            default:
                                setStyle("");
                        }
                    }
                }
            });

            
            

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading reports from server.");
        }
    }
    
     @FXML
    private void goBack() throws IOException {
        App.setRoot("login");
    }
    
    @FXML
    private void assignDepartment() {
        DisasterReportView selected = reportTable.getSelectionModel().getSelectedItem(); // Use DisasterReportView
        String dept = departmentCombo.getValue();

        if (selected != null && dept != null) {
            selected.setAssignedDepartment(dept);
            new DisasterReportDAO().updateAssignedDepartment(selected.getId(), dept);
            reportTable.refresh();
            loadReports(); // Reload full data after updating
            showAlert("Department assigned successfully.");
        } else {
            showAlert("Please select a report and a department.");
        }
    }

    

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Manager");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



