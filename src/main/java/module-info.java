module GSE.Project {
    requires javafx.fxml;
    requires javafx.controls;

    opens org.gsep.play to javafx.fxml;
    exports org.gsep.play;
}