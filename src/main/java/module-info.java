module com.github.metakol.ourpetproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;

    opens com.github.metakol to javafx.fxml;
    exports com.github.metakol;
}