package dao;

import model.DisasterReport;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DisasterReportDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/drs_db";
    private String jdbcUsername = "root"; // replace with your MySQL username
    private String jdbcPassword = "Abcd1234"; // replace with your MySQL password

    // private static final String INSERT_SQL = "INSERT INTO disasters (disaster_type, location, severity, reporter_name, description, reported_time) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_SQL = "INSERT INTO disasters (disaster_type, location, severity, reporter_name, description, reported_time, assigned_department, handled) VALUES (?, ?, ?, ?, ?, ?, ?, false)";

    public void insertDisasterReport(DisasterReport report) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setString(1, report.getDisasterType());
            preparedStatement.setString(2, report.getLocation());
            preparedStatement.setString(3, report.getSeverity());
            preparedStatement.setString(4, report.getReporterName());
            preparedStatement.setString(5, report.getDescription());

            // Convert LocalDateTime to SQL Timestamp
            Timestamp timestamp = Timestamp.valueOf(report.getReportedTime());
            preparedStatement.setTimestamp(6, timestamp);
            preparedStatement.setString(7, report.getAssignedDepartment());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
            }
        }
    }
    
    
    

    public List<DisasterReport> getAllDisasterReports() throws SQLException {
        List<DisasterReport> reports = new ArrayList<>();

        String sql = "SELECT * FROM disasters";

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DisasterReport report = new DisasterReport();
                report.setId(resultSet.getInt("id"));
                report.setDisasterType(resultSet.getString("disaster_type"));
                report.setLocation(resultSet.getString("location"));
                report.setSeverity(resultSet.getString("severity"));
                report.setReporterName(resultSet.getString("reporter_name"));
                report.setDescription(resultSet.getString("description"));
                report.setReportedTime(resultSet.getTimestamp("reported_time").toLocalDateTime());
                report.setAssignedDepartment(resultSet.getString("assigned_department"));
                report.setHandled(resultSet.getBoolean("handled"));

                reports.add(report);
            }
        }
        reports.sort((a, b) -> Integer.compare(b.getPriorityLevel(), a.getPriorityLevel()));
        return reports;
    }

    public void markDisasterAsHandled(int id) throws SQLException {
        String sql = "UPDATE disasters SET handled = TRUE WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void updateAssignedDepartment(int id, String department) {
        String sql = "UPDATE disasters SET assigned_department = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, department);
            statement.setInt(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    
    
}
