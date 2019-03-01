module GSE.Project {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.xml;
    requires junit;
    requires java.desktop;

    opens org.gsep.select to javafx.fxml;
    exports org.gsep.select;
    opens org.gsep.play to javafx.fxml;
    exports org.gsep.play;
}