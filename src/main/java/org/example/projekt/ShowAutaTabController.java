package org.example.projekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Kontroler obsługujący widok wyświetlający listę pojazdów w aplikacji.
 * Ładuje dane pojazdów z bazy danych PostgreSQL do TableView.
 */
public class ShowAutaTabController implements Initializable {

    /** Domyślny konstruktor */
    public ShowAutaTabController() {
    }

    @FXML
    private TableView<auta> tableAuta;

    @FXML
    private TableColumn<auta,Integer> idAuta;

    @FXML
    private TableColumn<auta,Integer> idAutaMechanicy;

    @FXML
    private TableColumn<auta,String> makeAuta;

    @FXML
    private TableColumn<auta,String> modelAuta;

    @FXML
    private TableColumn<auta,String> engineAuta;

    @FXML
    private TableColumn<auta,String> manufactureAuta;

    @FXML
    private TableColumn<auta, Text> faultAuta;

    ObservableList<auta> listAuta = FXCollections.observableArrayList();

    /** Inicjalizacja kontrolera
     * @param url link do zasobu, który inicjuje kontroler
     * @param resourceBundle zasoby przypisane do kontrolera
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String sqlGetMechanicy = "select * from pojazdy";
        String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
        String username = "postgres";
        String password = "student";

        {
            try {
                Connection con = DriverManager.getConnection(urlDB,username,password);
                Statement st = con.createStatement();
                ResultSet resultGM = st.executeQuery(sqlGetMechanicy);

                while (resultGM.next()) {

                    int id = resultGM.getInt("id");
                    int mechanikid = resultGM.getInt("idmechanika");
                    String make = resultGM.getString("marka");
                    String model = resultGM.getString("model");
                    String engine = resultGM.getString("typsilnika");
                    String manufacture = resultGM.getString("rokprodukcji");
                    String fault = resultGM.getString("opisusterki");

                    listAuta.add(new auta(id,mechanikid,make,model,engine,manufacture,fault));

                }

                idAuta.setCellValueFactory(new PropertyValueFactory<auta,Integer>("id"));
                idAutaMechanicy.setCellValueFactory(new PropertyValueFactory<auta,Integer>("mechanicyid"));
                makeAuta.setCellValueFactory(new PropertyValueFactory<auta,String>("make"));
                modelAuta.setCellValueFactory(new PropertyValueFactory<auta,String>("model"));
                engineAuta.setCellValueFactory(new PropertyValueFactory<auta,String>("engine"));
                manufactureAuta.setCellValueFactory(new PropertyValueFactory<auta,String>("manufacture"));
                faultAuta.setCellValueFactory(new PropertyValueFactory<auta,Text>("fault"));

                tableAuta.setItems(listAuta);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
