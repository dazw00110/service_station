package org.example.projekt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

/** Klasa ta odpowiada za dodawanie mechaników do bazy danych */
public class AddMechanicyTabController implements Initializable {

    /** Domyślny konstruktor */
    public AddMechanicyTabController() {
    }

    @FXML
    private TextField imie;
    @FXML
    private TextField nazwisko;
    @FXML
    private DatePicker data;
    @FXML
    private ComboBox<String> spec;
    private Stage stage;

/** Metoda ta odpowiada za sprawdzanie, czy podane imie lub nazwisko składa się wyłącznie z liter
     * @param name podane przez użytkownika imie lub nazwisko*/
    private boolean isValidName(String name) {
        return name.matches("^[a-zA-Z\\s\\-]+$");
        // Możliwość wpisania znaków od a-z oraz A-Z, minimum 1 znak, \s oznacza dowolny bialy znak
    }

/** Metoda ta realizuje dodawanie mechanika do bazy danych
     * @param e zdarzenie kliknięcia LPM na przycisk Dodaj */
    @FXML
    protected void addMechanic(MouseEvent e) {
        try {
            String name = imie.getText();
            String surname = nazwisko.getText();
            String specialization = spec.getValue();
            LocalDate selectedDate = data.getValue();

            if (name.isEmpty() || surname.isEmpty() || specialization == null || selectedDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "BRAKUJE DANYCH!", ButtonType.OK);
                alert.setHeaderText(null);
                alert.setTitle("BŁĄD");
                alert.show();
                return;
            }

            if (isValidName(name) && isValidName(surname)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "POMYŚLNIE DODANO MECHANIKA!", ButtonType.OK);
                alert.setHeaderText(null);
                alert.setTitle("SUKCES");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "NIEPOPRAWNE DANE!", ButtonType.OK);
                alert.setHeaderText(null);
                alert.setTitle("BŁĄD");
                alert.show();
                return;
            }

            String sqlAddMechanicy = "INSERT INTO public.mechanicy(imie, nazwisko, datazatrudnienia, specjalizacja) VALUES (?, ?, ?, ?)";
            String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
            String username = "postgres";
            String password = "student";

            try (Connection con = DriverManager.getConnection(urlDB, username, password);
                 PreparedStatement pst = con.prepareStatement(sqlAddMechanicy)) {

                pst.setString(1, name);
                pst.setString(2, surname);
                pst.setString(3, selectedDate.toString());
                pst.setString(4, specialization);
                pst.execute();
                Object root;

                //Odświeżanie wyników
                root = FXMLLoader.load(getClass().getResource("mechanicy.fxml"));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                Scene scene = new Scene((Parent) root);
                stage.setScene(scene);
                stage.show();

            } catch (SQLException ex) {
                throw new RuntimeException("Nie udało się dodać mechanika do bazy danych", ex);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wystąpił nieoczekiwany błąd!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.setTitle("BŁĄD");
            alert.show();
        }
    }

    /** Inicjalizacja kontrolera
     * @param url link do zasobu, który inicjuje kontroler
     * @param resourceBundle zasoby przypisane do kontrolera*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spec.getItems().addAll("Benzyna", "Diesel", "LPG", "Hybryda", "Elektryk");
    }
}
