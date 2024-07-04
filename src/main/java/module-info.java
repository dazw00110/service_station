/**
 * Moduł dla aplikacji warsztatu samochodowego wykorzystującej JavaFX i JDBC.
 */
module org.example.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.projekt to javafx.fxml;
    exports org.example.projekt;
}