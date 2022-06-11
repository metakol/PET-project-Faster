module com.github.metakol {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires javafx.graphics;

    opens com.github.metakol to javafx.fxml;
    opens com.github.metakol.controllers to javafx.fxml;
    exports com.github.metakol;
}