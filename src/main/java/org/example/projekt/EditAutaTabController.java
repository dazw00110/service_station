package org.example.projekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ResourceBundle;


/** Klasa ta odpowiada za dodawanie aut do bazy danych */
public class EditAutaTabController implements Initializable {

    /** Domyślny konstruktor */
    public EditAutaTabController() {
    }
    @FXML
    private TextField idAuta;
    @FXML
    private TextField marka;
    @FXML
    private TextField model;
    @FXML
    private ComboBox<String> typsilnika;
    @FXML
    private TextField rokprodukcji;
    @FXML
    private TextField opisusterki;
    @FXML
    private ComboBox<String> mechanik;

    private Stage stage;

    /** Metoda ta odpowiada za sprawdzanie, czy podana data jest w odpowiednim foramcie (YYYY)
     * @param year podana przez użytkownika data produkcji pojazdu*/
    private boolean isValidYear(String year) {
        return year.matches("^[0-9]{4}$");
    }

    /** Metoda ta odpowiada za sprawdzanie, czy podane id składa się wyłącznie z cyfr
     * @param ID podane przez użytkownika id*/
    private boolean isValidID(String ID) {
        return ID.matches("^[0-9]$");
    }

    /** Metoda ta realizuje dodawanie auta do bazy danych
     * @param e zdarzenie kliknięcia LPM na przycisk Dodaj */
    public void editAuta(MouseEvent e) {
        try {
            String ID = idAuta.getText();
            String make = marka.getText();
            String carmodel = model.getText();
            String manufacture = rokprodukcji.getText();
            String fault = opisusterki.getText();
            String engineType = typsilnika.getValue();
            String mechanic = mechanik.getValue();


            if (ID == null || make.isEmpty() || carmodel.isEmpty() || manufacture == null || fault == null || engineType == null || mechanic == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "BRAKUJE DANYCH!", ButtonType.OK);
                alert.setHeaderText(null);
                alert.setTitle("BŁĄD");
                alert.show();
                return;
            }

            if (!isValidYear(manufacture) || (!isValidID(ID))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "PODAJ ROK PRODUKCJI W FORMACIE YYYY!", ButtonType.OK);
                alert.setHeaderText(null);
                alert.setTitle("BŁĄD");
                alert.show();
                return;
            }

            String sqlEditAuta = "UPDATE public.pojazdy SET idmechanika=?, marka=?, model=?, typsilnika=?, rokprodukcji=?, opisusterki=? WHERE id = ?";

            String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
            String username = "postgres";
            String password = "student";

            try (Connection con = DriverManager.getConnection(urlDB, username, password);
                PreparedStatement pst = con.prepareStatement(sqlEditAuta)) {

                pst.setInt(1, getID(mechanic));
                pst.setString(2, make);
                pst.setString(3, carmodel);
                pst.setString(4, engineType);
                pst.setString(5, manufacture);
                pst.setString(6, fault);
                pst.setInt(7, Integer.parseInt(ID));

                if(checkEngine(mechanik.getValue(), typsilnika.getValue())){
                    Boolean stan = pst.execute();
                    if(!stan)
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "EDYCJA PRZEBIEGŁA POMYŚLNIE", ButtonType.OK);
                        alert.setHeaderText(null);
                        alert.setTitle("SUKCES");
                        alert.show();
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "EDYCJA NIEUDANA", ButtonType.OK);
                        alert.setHeaderText(null);
                        alert.setTitle("BŁĄD");
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "ZŁY MECHANIK, NIEZGODNOŚĆ SPECJALIZACJI", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.setTitle("BŁĄD");
                    alert.show();
                }

                Object root;

                //Odświeżanie wyników
                root = FXMLLoader.load(getClass().getResource("auta.fxml"));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                Scene scene = new Scene((Parent) root);
                stage.setScene(scene);
                stage.show();

            } catch (SQLException ex) {
                throw new RuntimeException("Nie udało się dodać auta do bazy danych", ex);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wystąpił nieoczekiwany błąd!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.setTitle("BŁĄD");
            alert.show();
        }
    }

    ObservableList listMechanicy = FXCollections.observableArrayList();

    /** Metoda ta realizuje wypełnienie ComboBox'a aktualnymi danymi z bazy danych*/
    public void fillCombobox(){
        String sqlGetMechanicy = "select nazwisko, specjalizacja from mechanicy";
        String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
        String username = "postgres";
        String password = "student";

        {
            try {
                Connection con = DriverManager.getConnection(urlDB,username,password);
                ResultSet resultGM = con.createStatement().executeQuery(sqlGetMechanicy);

                while (resultGM.next()) {

                    String nazwisko = resultGM.getString("nazwisko");

                    listMechanicy.add(nazwisko);
                }

                mechanik.setItems(listMechanicy);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /** Metoda ta sprawdza, czy wybrany przez użytkownika mechanik zajmuje się typem silnika dla wprowadzanego auta
     * @param mechanic nazwisko mechanika wybrane przez użytkownika z ComboBox'a
     * @param engine typ silnika wybrany przez użytkownika z ComboBox'a
     * @return true jeśli mechanik specjalizuje się w danym typie silnika, false w przeciwnym razie
     */
    public boolean checkEngine(String mechanic, String engine) {
        String sqlGetMechanicy = "SELECT nazwisko, specjalizacja FROM mechanicy WHERE nazwisko = ? AND specjalizacja = ?";
        String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
        String username = "postgres";
        String password = "student";

        boolean wynik = false;

        System.out.println("Sprawdzanie mechanika: " + mechanic + " z silnikiem: " + engine);

        try (Connection con = DriverManager.getConnection(urlDB, username, password);
             PreparedStatement pst = con.prepareStatement(sqlGetMechanicy)) {

            pst.setString(1, mechanic);
            pst.setString(2, engine);

            try (ResultSet resultGM = pst.executeQuery()) {
                wynik = resultGM.next();
                if (wynik) {
                    System.out.println("Znaleziono dopasowanie w bazie danych.");
                } else {
                    System.out.println("Brak dopasowania w bazie danych.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wynik;
    }

    /** Metoda ta zwraca z bazy danych dla danego mechanika jego ID
     * @param mechanic nazwisko mechanika wybrane z ComboBox'a
     * @return ID zwraca id mechanika*/
    public int getID(String mechanic) {
        String sqlGetMechanicy = "SELECT id FROM mechanicy WHERE nazwisko = ?";
        String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
        String username = "postgres";
        String password = "student";

        int wynik = 0;

        try (Connection con = DriverManager.getConnection(urlDB, username, password);
             PreparedStatement pst = con.prepareStatement(sqlGetMechanicy)) {

            pst.setString(1, mechanic);

            try (ResultSet resultGM = pst.executeQuery()) {
                if (resultGM.next()) {
                    wynik = resultGM.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wynik;
    }

    /** Inicjalizacja kontrolera
     * @param url link do zasobu, który inicjuje kontroler
     * @param resourceBundle zasoby przypisane do kontrolera*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typsilnika.getItems().addAll("Benzyna", "Diesel", "LPG", "Hybryda", "Elektryk");
        fillCombobox();

    }
}
