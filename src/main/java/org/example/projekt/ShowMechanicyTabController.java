package org.example.projekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Kontroler odpowiadający za wyświetlanie listy mechaników w aplikacji.
 * Rozszerza funkcjonalność klasy bazowej `MechanicyMainController`.
 */
public class ShowMechanicyTabController extends MechanicyMainController implements Initializable {

    /** Domyślny konstruktor */
    public ShowMechanicyTabController() {
    }

    /** Inicjalizacja kontrolera
     * @param url link do zasobu, który inicjuje kontroler
     * @param resourceBundle zasoby przypisane do kontrolera
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        show();
    }
}
