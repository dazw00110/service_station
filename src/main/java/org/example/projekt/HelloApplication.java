package org.example.projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/** Jest to główna klasa aplikacji, która uruchamia cały program*/
public class HelloApplication extends Application {

    /** Domyślny konstruktor */
    public HelloApplication() {
    }

    /**
  * Metoda start aplikacji JavaFX, która wczytuje interfejs użytkownika z pliku FXML
  * i inicjuje główne okno aplikacji.
  *
  * @param stage Główne okno aplikacji, na którym będą renderowane wszystkie elementy interfejsu.
  */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connect.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Baza Warsztat Samochdowy");
        stage.show();
    }

/**
  * Główna metoda uruchamiająca aplikację JavaFX. Metoda ta inicjuje aplikację
  * poprzez wywołanie metody start z klasy HelloApplication, która jest rozszerzeniem
  * klasy Application.
  *
  * @param args Argumenty wiersza poleceń dostarczone podczas uruchamiania aplikacji.
  */
    public static void main(String[] args) {
        launch();
    }
}