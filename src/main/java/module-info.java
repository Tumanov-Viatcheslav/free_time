module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.arkanoid.view;
    exports org.example.arkanoid.source;
}
