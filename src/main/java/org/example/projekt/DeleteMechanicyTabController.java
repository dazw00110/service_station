package org.example.projekt;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/** Klasa ta odpowiada za usuwanie mechaników z bazy danych */
public class DeleteMechanicyTabController extends MechanicyMainController implements Initializable {

    /** Domyślny konstruktor */
    public DeleteMechanicyTabController() {
    }

    /** Metoda ta realizuje usuwanie mechaników z bazy danych
 * @param e zdarzenie kliknięcia LPM na przycisk USUŃ PRACOWNIKA */
    public void goToDeleteMechanicy(MouseEvent e) {

        mechanik = tableMechanicy.getSelectionModel().getSelectedItem();

        if (mechanik != null) {
            String urlDB = "jdbc:postgresql://localhost:5432/warsztat";
            String username = "postgres";
            String password = "student";

            try (Connection conCheck = DriverManager.getConnection(urlDB, username, password);
                PreparedStatement pstCheck = conCheck.prepareStatement("SELECT COUNT(*) FROM public.pojazdy WHERE idmechanika = ?")) {
                pstCheck.setInt(1, mechanik.getId());
                ResultSet rs = pstCheck.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count > 0) {
                    // Powiadomienie użytkownika o istniejących powiązaniach
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można usunąć mechanika. Istnieją powiązane pojazdy!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.setTitle("Błąd");
                    alert.show();
                    return; // Przerwij operację usunięcia
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            try (Connection conDelete = DriverManager.getConnection(urlDB, username, password);
                 PreparedStatement pstDelete = conDelete.prepareStatement("DELETE FROM public.mechanicy WHERE id = ?")) {
                pstDelete.setInt(1, mechanik.getId());
                pstDelete.executeUpdate();

                // Powiadomienie użytkownika o pomyślnym usunięciu
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pomyślnie usunięto mechanika!", ButtonType.OK);
                alert.setHeaderText(null);
                alert.setTitle("Informacja");
                alert.show();

                // Odświeżenie wyników (jeśli to potrzebne)

            } catch (SQLException ex) {
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

    private Stage stage;

    private Object root;

    private mechanicy mechanik; // referencja do edytowanego mechanika

/** Inicjalizacja kontrolera
  * @param url link do zasobu, który inicjuje kontroler
  * @param resourceBundle zasoby przypisane do kontrolera*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show();
    }
}
