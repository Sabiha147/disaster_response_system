package model;

import java.time.LocalDateTime;

public class DisasterReport {
    private int id;
    private String disasterType;
    private String location;
    private String severity;
    private String reporterName;
    private String description;
    private LocalDateTime reportedTime;
    private boolean handled;
    private String assignedDepartment;

    public DisasterReport() {
    }

    public DisasterReport(int id, String disasterType, String location, String severity, String reporterName, String description, LocalDateTime reportedTime) {
        this.id = id;
        this.disasterType = disasterType;
        this.location = location;
        this.severity = severity;
        this.reporterName = reporterName;
        this.description = description;
        this.reportedTime = reportedTime;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getReportedTime() {
        return reportedTime;
    }

    public void setReportedTime(LocalDateTime reportedTime) {
        this.reportedTime = reportedTime;
    }
    
    public boolean isHandled() {
        return handled;
    }
    
    public String getAssignedDepartment() {
        return assignedDepartment;
    }


    public void setHandled(boolean handled) {
        this.handled = handled;
    }
    
    public void setAssignedDepartment(String assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }
    
    public int getPriorityLevel() {
        switch (this.severity.toLowerCase()) {
            case "high": return 3;
            case "medium": return 2;
            case "low": return 1;
            default: return 0;
        }
    }


    @Override
    public String toString() {
        return "DisasterReport{" +
                "id=" + id +
                ", disasterType='" + disasterType + '\'' +
                ", location='" + location + '\'' +
                ", severity='" + severity + '\'' +
                ", reporterName='" + reporterName + '\'' +
                ", description='" + description + '\'' +
                ", reportedTime=" + reportedTime +
                '}';
    }
}
