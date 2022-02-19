module master.toylanguagegui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;


    opens master.toylanguagegui to javafx.fxml;
    exports master.toylanguagegui;
}