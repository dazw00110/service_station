package org.example.projekt;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Kontroler obsługujący interakcje użytkownika w aplikacji GUI.
 * Odpowiada za przejścia między widokami oraz obsługę zdarzeń.
 */
public class HelloController {

    /** Domyślny konstruktor */
    public HelloController() {
    }

    /**
     * Obsługuje zdarzenie wyjścia z programu.
     * @param e Zdarzenie myszy, które wywołało tę metodę.
     */
    @FXML
    protected void exit(MouseEvent e) {
        System.out.println("Wyjście z programu");
        System.exit(0);
    }

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    /**
     * Obsługuje zdarzenie przechodzenia do widoku "auta.fxml".
     * @param e Zdarzenie akcji, które wywołało tę metodę.
     * @throws IOException Jeśli wystąpi błąd podczas ładowania pliku FXML.
     */
    @FXML
    protected void goToCars(ActionEvent e) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("auta.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Obsługuje zdarzenie przechodzenia do widoku "mechanicy.fxml".
     * @param e Zdarzenie akcji, które wywołało tę metodę.
     * @throws IOException Jeśli wystąpi błąd podczas ładowania pliku FXML.
     */
    @FXML
    protected void goToMechanics(ActionEvent e) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("mechanicy.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Obsługuje zdarzenie przechodzenia do widoku "hello-view.fxml".
     * @param e Zdarzenie akcji, które wywołało tę metodę.
     * @throws IOException Jeśli wystąpi błąd podczas ładowania pliku FXML.
     */
    @FXML
    protected void goToMain(ActionEvent e) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,600,400);
        stage.setScene(scene);
        stage.show();
    }
}