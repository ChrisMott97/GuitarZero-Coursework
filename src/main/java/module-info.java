module GSE.Project {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.xml;
    requires junit;
    requires java.desktop;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens org.gsep.select to javafx.fxml, com.fasterxml.jackson.databind;
    exports org.gsep.select;
    opens org.gsep.carousel to javafx.fxml, com.fasterxml.jackson.databind;
    exports org.gsep.carousel;
    opens org.gsep.slash to javafx.fxml, com.fasterxml.jackson.databind;
    exports org.gsep.slash;
}