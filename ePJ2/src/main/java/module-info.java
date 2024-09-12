module org.unibl.etf.epj2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.unibl.etf.epj2 to javafx.fxml;
    exports org.unibl.etf.epj2;
    opens org.unibl.etf.epj2.controllers to javafx.fxml;
    exports org.unibl.etf.epj2.controllers;
    opens org.unibl.etf.epj2.models.vehicle to javafx.base;
    exports org.unibl.etf.epj2.models.vehicle;
    opens org.unibl.etf.epj2.models.reports to javafx.base;
    exports org.unibl.etf.epj2.models.reports;
    opens org.unibl.etf.epj2.models.malfunction to javafx.base;
    exports org.unibl.etf.epj2.models.malfunction;
}