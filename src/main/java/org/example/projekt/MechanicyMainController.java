package org.example.projekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
/**
 * Kontroler obsługujący widok główny dla mechaników w aplikacji.
 * Ładuje dane mechaników z bazy danych PostgreSQL do TableView.
 */
public class MechanicyMainController {

    /** Domyślny konstruktor */
    public MechanicyMainController() {
    }

    /** Tabela wyświetlająca dane mechaników */
    @FXML
    public TableView<mechanicy> tableMechanicy;

    @FXML
    private TableColumn<mechanicy, Integer> idMechanicy;

    @FXML
    private TableColumn<mechanicy, String> nameMechanicy;

    @FXML
    private TableColumn<mechanicy, String> surnameMechanicy;

    @FXML
    private TableColumn<mechanicy, String> employmentMechanicy;

    @FXML
    private TableColumn<mechanicy, String> specialisationMechanicy;

    ObservableList<mechanicy> listMechanicy = FXCollections.observableArrayList();

/**
 * Metoda inicjująca wyświetlanie danych w tabeli.
 * Pobiera dane z bazy danych i aktualizuje TableView.
 */
    public void show() {
        String sqlGetMechanicy = "select * from mechanicy";
        String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
        String username = "postgres";
        String password = "student";

        try {
            Connection con = DriverManager.getConnection(urlDB, username, password);
            Statement st = con.createStatement();
            ResultSet resultGM = st.executeQuery(sqlGetMechanicy);

            while (resultGM.next()) {
                int id = resultGM.getInt("id");
                String imie = resultGM.getString("imie");
                String nazwisko = resultGM.getString("nazwisko");
                String dataZatrudnienia = resultGM.getString("datazatrudnienia");
                String specjalizacja = resultGM.getString("specjalizacja");

                listMechanicy.add(new mechanicy(id, imie, nazwisko, dataZatrudnienia, specjalizacja));
                tableMechanicy.refresh();
            }

            idMechanicy.setCellValueFactory(new PropertyValueFactory<mechanicy, Integer>("id"));
            nameMechanicy.setCellValueFactory(new PropertyValueFactory<mechanicy, String>("name"));
            surnameMechanicy.setCellValueFactory(new PropertyValueFactory<mechanicy, String>("surname"));
            employmentMechanicy.setCellValueFactory(new PropertyValueFactory<mechanicy, String>("employmentdate"));
            specialisationMechanicy.setCellValueFactory(new PropertyValueFactory<mechanicy, String>("specialisation"));

            tableMechanicy.setItems(listMechanicy);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
