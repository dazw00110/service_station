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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/** Klasa ta odpowiada za usuwanie aut z bazy danych */
public class DeleteAutaTabController implements Initializable {

    /** Domyślny konstruktor */
    public DeleteAutaTabController() {
    }

    @FXML
    private TableView<auta> tableAuta;

    @FXML
    private TableColumn<auta, Integer> idAuta;

    @FXML
    private TableColumn<auta, Integer> idAutaMechanicy;

    @FXML
    private TableColumn<auta, String> makeAuta;

    @FXML
    private TableColumn<auta, String> modelAuta;

    @FXML
    private TableColumn<auta, String> engineAuta;

    @FXML
    private TableColumn<auta, String> manufactureAuta;

    @FXML
    private TableColumn<auta, Text> faultAuta;

    private Stage stage;

    private Object root;

    ObservableList<auta> listAuta = FXCollections.observableArrayList();

/** Inicjalizacja kontrolera
     * @param url link do zasobu, który inicjuje kontroler
     * @param resourceBundle zasoby przypisane do kontrolera*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sqlGetMechanicy = "select * from pojazdy";
        String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
        String username = "postgres";
        String password = "student";

        {
            try {
                Connection con = DriverManager.getConnection(urlDB, username, password);
                Statement st = con.createStatement();
                //resultGM - resultGetMechanicy
                ResultSet resultGM = st.executeQuery(sqlGetMechanicy);

                while (resultGM.next()) {

                    int id = resultGM.getInt("id");
                    int mechanikid = resultGM.getInt("id");
                    String make = resultGM.getString("marka");
                    String model = resultGM.getString("model");
                    String engine = resultGM.getString("typsilnika");
                    String manufacture = resultGM.getString("rokprodukcji");
                    String fault = resultGM.getString("opisusterki");

                    listAuta.add(new auta(id, mechanikid, make, model, engine, manufacture, fault));
                    //tableAuta.refresh();

                }

                idAuta.setCellValueFactory(new PropertyValueFactory<auta, Integer>("id"));
                idAutaMechanicy.setCellValueFactory(new PropertyValueFactory<auta, Integer>("mechanicyid"));
                makeAuta.setCellValueFactory(new PropertyValueFactory<auta, String>("make"));
                modelAuta.setCellValueFactory(new PropertyValueFactory<auta, String>("model"));
                engineAuta.setCellValueFactory(new PropertyValueFactory<auta, String>("engine"));
                manufactureAuta.setCellValueFactory(new PropertyValueFactory<auta, String>("manufacture"));
                faultAuta.setCellValueFactory(new PropertyValueFactory<auta, Text>("fault"));

                tableAuta.setItems(listAuta);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private auta auto; // referencja do edytowanego auta

/** Metoda ta realizuje usuwanie auta z bazy danych
* @param e zdarzenie kliknięcia LPM na przycisk USUŃ AUTO */
    public void goToDeleteCar(MouseEvent e) {
        auto = tableAuta.getSelectionModel().getSelectedItem();

        if (auto != null) {
            String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
            String username = "postgres";
            String password = "student";

            try (Connection con = DriverManager.getConnection(urlDB, username, password);
                PreparedStatement pst = con.prepareStatement("DELETE FROM public.pojazdy WHERE id = '"+auto.getId()+"';")) {
                pst.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "POMYŚLNIE USUNIĘTO AUTO!", ButtonType.OK);
                alert.setHeaderText(null);
                alert.setTitle("BŁĄD");
                alert.show();

                //Odświeżanie wyników
                root = FXMLLoader.load(getClass().getResource("auta.fxml"));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                Scene scene = new Scene((Parent) root);
                stage.setScene(scene);
                stage.show();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "NIE WYBRANO AUTA!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.setTitle("BŁĄD");
            alert.show();
        }
    }
}



