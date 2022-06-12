module com.github.metakol.ourpetproject{
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;
    requires java.sql;

    opens com.github.metakol to javafx.fxml;
    opens com.github.metakol.controllers to javafx.fxml;
    exports com.github.metakol;
    exports com.github.metakol.entities to com.fasterxml.jackson.databind;
}