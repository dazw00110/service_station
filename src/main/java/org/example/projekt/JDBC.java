package org.example.projekt;
import java.sql.*;

/**
 * Klasa JDBC odpowiada za połączenie i interakcję z bazą danych PostgreSQL.
 */
public class JDBC {

    /** Domyślny konstruktor */
    public JDBC() {
    }

    private static final String DRIVER = "org.postgresql.Driver";
    private static String DB_URL = "jdbc:postgresql://localhost:5432/warsztat_samochodowy";
    private static Connection connection;
    private static Statement statement;

    /**
     * Metoda główna aplikacji, odpowiedzialna za połączenie z bazą danych i wykonanie zapytania SQL.
     * @param args Argumenty przekazane do aplikacji w linii poleceń.
     */

    public static void main(String[] args) {
        try{
            Class.forName(DRIVER);
            System.out.println("1. Zarejestrowano sterownik do bazy danych");
            connection = DriverManager.getConnection(DB_URL,"postgres","student");
            System.out.println("2.  Nawiązano połazczenie z bazą danych");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Niewłaściwy sterownik lub jego brak");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Problem z otwarciem połączenia");
            e.printStackTrace();
        }

        try{
            statement = connection.createStatement();

            String sql = "SELECT * FROM mechanik";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next())
            {
                int id = rs.getInt("id");

                System.out.println("id: " + id);
            }

        } catch (SQLException e) {
            System.out.println("Nie mozna wykonac zapytania");
            e.printStackTrace();
        }
    }
}
