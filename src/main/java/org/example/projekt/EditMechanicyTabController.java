package org.example.projekt;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

/** Klasa ta odpowiada za dodawanie mechaników do bazy danych */
public class EditMechanicyTabController extends MechanicyMainController implements Initializable {

    /** Domyślny konstruktor */
    public EditMechanicyTabController() {
    }

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox<String> spec;

    private mechanicy mechanik; // referencja do edytowanego mechanika

    /** Inicjalizacja kontrolera
     * @param url link do zasobu, który inicjuje kontroler
     * @param resourceBundle zasoby przypisane do kontrolera*/
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spec.getItems().addAll("Benzyna", "Diesel", "LPG", "Hybryda", "Elektryk");
    }

    /** Metoda ta realizuje wyszukiwanie mechanika o podanym id
     * @param id podane przez użytkownika id*/
    private mechanicy checkMechanicByID(String id) {
        String sqlGetMechanic = "SELECT * FROM mechanicy WHERE id = ?";
        String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
        String username = "postgres";
        String password = "student";

        try (Connection con = DriverManager.getConnection(urlDB, username, password);
             PreparedStatement pst = con.prepareStatement(sqlGetMechanic)) {

            pst.setInt(1, Integer.parseInt(id));

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int mechanikId = rs.getInt("id");
                    String imie = rs.getString("imie");
                    String nazwisko = rs.getString("nazwisko");
                    String dataZatrudnienia = rs.getString("datazatrudnienia");
                    String specjalizacja = rs.getString("specjalizacja");

                    return new mechanicy(mechanikId, imie, nazwisko, dataZatrudnienia, specjalizacja);
                } else {
                    return null; // Jeśli nie znaleziono mechanika o podanym ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Metoda ta realizuje dodawanie mechaników do bazy danych */
    @FXML
    public void save() {
        String mechanicID = id.getText();

        String mechanicName = name.getText();
        String mechanicSurname = surname.getText();
        LocalDate mechanicDate = date.getValue();
        String mechanicSpec = spec.getValue();


        if (mechanicID != null && mechanicName != null && mechanicSurname != null && mechanicDate != null && mechanicSpec != null)
        {
            if(isValidID(mechanicID) && isValidName(mechanicName) && isValidName(mechanicSurname))
            {
                mechanik = checkMechanicByID(mechanicID);
                if (mechanik != null) {
                    // Jeśli mechanik o podanym ID istnieje, wczytaj jego dane do pól formularza
                    name.setText(mechanicName);
                    surname.setText(mechanicSurname);
                    // Przyjmuję, że mechanik.getEmploymentdate() zwraca String (data w formacie tekstowym)
                    // Jeśli zwraca LocalDate, należy odpowiednio przekształcić
                    date.setValue(mechanicDate);

                    String sqlAddMechanicy = "UPDATE public.mechanicy SET imie=?, nazwisko=?, datazatrudnienia=?, specjalizacja=? WHERE id = "+mechanicID;
                    String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
                    String username = "postgres";
                    String password = "student";

                    try (Connection con = DriverManager.getConnection(urlDB, username, password);
                         PreparedStatement pst = con.prepareStatement(sqlAddMechanicy)) {

                        pst.setString(1, mechanicName);
                        pst.setString(2, mechanicSurname);
                        pst.setString(3, mechanicDate.toString());
                        pst.setString(4, mechanicSpec);
                        pst.execute();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "EDYCJA DANYCH POWIODŁA SIĘ!!!", ButtonType.OK);
                        alert.setHeaderText(null);
                        alert.setTitle("SUKCES");
                        alert.show();
                        return;

                    } catch (SQLException ex) {
                        throw new RuntimeException("Nie udało się dodać mechanika do bazy danych", ex);
                    }
                }
                else {
                    System.out.println("Nie znaleziono mechanika o podanym ID: " + mechanicID);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "NIEPOPRAWNE DANE!", ButtonType.OK);
                alert.setHeaderText(null);
                alert.setTitle("BŁĄD");
                alert.show();
                return;             }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "BRAKUJE DANYCH!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.setTitle("BŁĄD");
            alert.show();
            return;        }
    }
    /** Metoda ta odpowiada za sprawdzanie, czy podane imie lub nazwisko składa się wyłącznie z liter
     * @param name podane przez użytkownika imie lub nazwisko*/
    private boolean isValidName(String name) {
        return name.matches("^[a-zA-Z\\s\\-]+$");
        // Możliwość wpisania znaków od a-z oraz A-Z, minimum 1 znak, \s oznacza dowolny bialy znak
    }
    /** Metoda ta odpowiada za sprawdzanie, czy podane id składa się wyłącznie z cyfr
     * @param ID podane przez użytkownika id*/
    private boolean isValidID(String ID) {
        return ID.matches("^[0-9]$");
    }

}
