module org.example.gestorjuegosjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;

    opens org.example.gestorjuegosjdbc.models;


    opens org.example.gestorjuegosjdbc to javafx.fxml;
    exports org.example.gestorjuegosjdbc;
}