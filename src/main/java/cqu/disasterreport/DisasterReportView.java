package cqu.disasterreport;

import javafx.beans.property.*;

public class DisasterReportView {
    private final IntegerProperty id;
    private final StringProperty disasterType;
    private final StringProperty location;
    private final StringProperty severity;
    private final StringProperty reporterName;
    private final StringProperty description;
    private final StringProperty reportedTime;
    private final StringProperty handled;
    private final SimpleStringProperty assignedDepartment = new SimpleStringProperty();

    
    public DisasterReportView(int id, String disasterType, String location, String severity, String reporterName,
            String description, String reportedTime, String handled, String assignedDepartment) {
        this.id = new SimpleIntegerProperty(id);
        this.disasterType = new SimpleStringProperty(disasterType);
        this.location = new SimpleStringProperty(location);
        this.severity = new SimpleStringProperty(severity);
        this.reporterName = new SimpleStringProperty(reporterName);
        this.description = new SimpleStringProperty(description);
        this.reportedTime = new SimpleStringProperty(reportedTime);
        this.handled = new SimpleStringProperty(handled);
         this.assignedDepartment.set(assignedDepartment);
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty disasterTypeProperty() {
        return disasterType;
    }

    public StringProperty locationProperty() {
        return location;
    }

    public StringProperty severityProperty() {
        return severity;
    }
    public String getSeverity() {
        return severity.get();
    }


    public StringProperty reporterNameProperty() {
        return reporterName;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty reportedTimeProperty() {
        return reportedTime;
    }

    public StringProperty handledProperty() {
        return handled;
    }
    
    public StringProperty assignedDepartmentProperty() {
        return assignedDepartment;
    }

    public String getAssignedDepartment() {
        return assignedDepartment.get();
    }

    public void setAssignedDepartment(String assignedDepartment) {
        this.assignedDepartment.set(assignedDepartment);
    }

}
