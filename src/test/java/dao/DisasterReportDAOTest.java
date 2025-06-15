package dao;

import model.DisasterReport;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DisasterReportDAOTest {

    DisasterReportDAO dao = new DisasterReportDAO();

    @Test
    void testInsertAndFetch() throws Exception {
        // Arrange
        DisasterReport report = new DisasterReport();
        report.setDisasterType("TestFire");
        report.setLocation("TestLocation");
        report.setSeverity("High");
        report.setReporterName("JUnit Tester");
        report.setDescription("This is a test report");
        report.setReportedTime(LocalDateTime.now());
        report.setAssignedDepartment("Test Dept");

        // Act
        dao.insertDisasterReport(report);
        List<DisasterReport> reports = dao.getAllDisasterReports();

        // Assert
        assertFalse(reports.isEmpty());
        assertTrue(reports.stream().anyMatch(r ->
                r.getReporterName().equals("JUnit Tester") &&
                r.getDisasterType().equals("TestFire")
        ));
    }

    @Test
    void testUpdateAssignedDepartment() {
        try {
            List<DisasterReport> reports = dao.getAllDisasterReports();
            assertFalse(reports.isEmpty());

            DisasterReport first = reports.get(0);
            dao.updateAssignedDepartment(first.getId(), "UpdatedDept");

            List<DisasterReport> updatedReports = dao.getAllDisasterReports();
            DisasterReport updated = updatedReports.stream()
                    .filter(r -> r.getId() == first.getId())
                    .findFirst()
                    .orElse(null);

            assertNotNull(updated);
            assertEquals("UpdatedDept", updated.getAssignedDepartment());

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
    
    @Test
    void testInsertWithMissingFieldsShouldFail() {
        DisasterReport report = new DisasterReport();
        report.setLocation("Test");
        report.setSeverity("High");

        assertThrows(Exception.class, () -> {
            dao.insertDisasterReport(report);
        });
    }

    
    
    @Test
    void testMarkDisasterAsHandled() throws Exception {
        List<DisasterReport> reports = dao.getAllDisasterReports();
        assertFalse(reports.isEmpty());

        int id = reports.get(0).getId();
        dao.markDisasterAsHandled(id);

        List<DisasterReport> updated = dao.getAllDisasterReports();
        assertTrue(updated.stream()
                .anyMatch(r -> r.getId() == id && r.isHandled()));
    }


    @Test
    void testUpdateInvalidIdShouldNotCrash() {
        assertDoesNotThrow(() -> dao.updateAssignedDepartment(-1, "InvalidDept"));
    }


    @Test
    void testPrioritySorting() throws Exception {
        List<DisasterReport> reports = dao.getAllDisasterReports();
        for (int i = 1; i < reports.size(); i++) {
            int prev = reports.get(i - 1).getPriorityLevel();
            int current = reports.get(i).getPriorityLevel();
            assertTrue(prev >= current);
        }
    }




}
