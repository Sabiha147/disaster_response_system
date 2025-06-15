package server;

import java.io.*;
import java.net.Socket;

import dao.DisasterReportDAO;
import model.DisasterReport;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;



public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
        ) {
            String text;

            while ((text = reader.readLine()) != null) {
                System.out.println("Received: " + text);

                // Here you can add logic: parse command, access DB, return result
                if (text.startsWith("ADD_DISASTER")) {
                    handleAddDisaster(text, writer);
                }else if (text.startsWith("GET_ALL_REPORTS")) {
                    handleGetAllReports(writer);
                } else if (text.startsWith("MARK_HANDLED")) {
                    handleMarkHandled(text, writer);
                } else {
                    writer.println("Unknown command");
                }

             

                // Optionally break if client sends "bye"
                if (text.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            socket.close();
            System.out.println("Client disconnected");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    private void handleAddDisaster(String text, PrintWriter writer) {
        try {
            String[] parts = text.split("\\|");
            if (parts.length != 7) {
                writer.println("ERROR: Invalid ADD_DISASTER format");
                return;
            }

            DisasterReportDAO dao = new DisasterReportDAO();

            DisasterReport report = new DisasterReport();
            report.setDisasterType(parts[1]);
            report.setLocation(parts[2]);
            report.setSeverity(parts[3]);
            report.setReporterName(parts[4]);
            report.setDescription(parts[5]);
            report.setReportedTime(LocalDateTime.parse(parts[6]));

            dao.insertDisasterReport(report);

            writer.println("SUCCESS: Disaster report saved");

        } catch (Exception e) {
            e.printStackTrace();
            writer.println("ERROR: " + e.getMessage());
        }
    }
    
    
    private void handleGetAllReports(PrintWriter writer) {
        try {
            DisasterReportDAO dao = new DisasterReportDAO();
            List<DisasterReport> reports = dao.getAllDisasterReports();

            for (DisasterReport report : reports) {
                writer.println(String.format("%d|%s|%s|%s|%s|%s|%s|%s|%s",
                    report.getId(),
                    report.getDisasterType(),
                    report.getLocation(),
                    report.getSeverity(),
                    report.getReporterName(),
                    report.getDescription(),
                    report.getReportedTime().toString(),
                    report.isHandled() ? "Handled" : "Not Handled",
                    report.getAssignedDepartment() == null ? "" : report.getAssignedDepartment()
                ));
            }

            writer.println("END_OF_REPORTS");

        } catch (Exception e) {
            e.printStackTrace();
            writer.println("ERROR: " + e.getMessage());
        }
    }

    private void handleMarkHandled(String text, PrintWriter writer) {
        try {
            String[] parts = text.split("\\|");
            if (parts.length != 2) {
                writer.println("ERROR: Invalid MARK_HANDLED format");
                return;
            }

            int id = Integer.parseInt(parts[1]);

            DisasterReportDAO dao = new DisasterReportDAO();
            dao.markDisasterAsHandled(id);

            writer.println("SUCCESS: Report marked as handled");

        } catch (Exception e) {
            e.printStackTrace();
            writer.println("ERROR: " + e.getMessage());
        }
    }

    
    
    
    
}
