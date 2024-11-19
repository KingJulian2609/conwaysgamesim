module com.example.schach {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.xml.dom;


    opens com.example.conways to javafx.fxml;
    exports com.example.conways;
}