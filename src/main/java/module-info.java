module GSE.Project {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.xml;

    opens org.gsep to javafx.fxml;
    exports org.gsep;
}