module cqu.disasterreport {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    
    requires java.sql;

    opens cqu.disasterreport to javafx.fxml;
    exports cqu.disasterreport;
}
