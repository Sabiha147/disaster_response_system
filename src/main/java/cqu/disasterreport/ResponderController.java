package cqu.disasterreport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ResponderController implements Initializable {

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
    private TableColumn<DisasterReportView, String> departmentColumn;

    @FXML
    private TableColumn<DisasterReportView, String> handledColumn;

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
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().assignedDepartmentProperty());
        handledColumn.setCellValueFactory(cellData -> cellData.getValue().handledProperty());

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
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5],
                        parts[6],
                        parts[7],
                        parts.length >= 9 ? parts[8] : ""
                );

                reportList.add(report);
            }

            reportTable.setItems(reportList);
            
            // add color on rows
            reportTable.setRowFactory(tv -> new TableRow<DisasterReportView>() {
                @Override
                protected void updateItem(DisasterReportView item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setStyle("");
                    } else {
                        String severity = item.getSeverity().toLowerCase();
                        switch (severity) {
                            case "high":
                                setStyle("-fx-background-color: #ffcccc;");
                                break;
                            case "medium":
                                setStyle("-fx-background-color: #fff5cc;");
                                break;
                            case "low":
                                setStyle("-fx-background-color: #ccffcc;");
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
    private void markSelectedAsHandled() {
        DisasterReportView selected = reportTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Please select a report to mark as handled.");
            return;
        }

        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            writer.println("MARK_HANDLED|" + selected.getId());

            String response = reader.readLine();
            showAlert(response);

            // Reload the reports to reflect change
            loadReports();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error communicating with server.");
        }
    }
    
      @FXML
    private void goBack() throws IOException {
        App.setRoot("login");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Responder");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
