module lab {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;
    requires java.logging;
    requires org.jetbrains.annotations;
    opens lab to javafx.fxml;
    exports lab;
    exports lab.components;
    opens lab.components to javafx.fxml;
    exports lab.enums;
    opens lab.enums to javafx.fxml;
    exports lab.interfaces;
    opens lab.interfaces to javafx.fxml;
    exports lab.events;
    opens lab.events to javafx.fxml;
    exports lab.scenes;
    opens lab.scenes to javafx.fxml;
    exports lab.score;
    opens lab.score to javafx.fxml;
}
