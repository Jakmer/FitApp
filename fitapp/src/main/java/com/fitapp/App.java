package com.fitapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("login/logs.txt"));

        // Dane do połączenia z bazą danych
        String jdbcUrl = reader.readLine();
        String username = reader.readLine();
        String password = reader.readLine();
        reader.close();

        // Ustawienia dla SSL (potrzebne w przypadku połączenia z bazą na Azure)
        String sslMode = "require";
        String sslFactory = "org.postgresql.ssl.NonValidatingFactory";

        // Proba polaczenia
        try {
            // Rejestrujemy sterownik JDBC
            Class.forName("org.postgresql.Driver");


            // Nawiązujemy połączenie
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Tutaj możesz wykonywać operacje na bazie danych
            // ...

            String sql = "SELECT * FROM history";

            // Tworzymy obiekt Statement
            Statement statement = connection.createStatement();

            // Wykonujemy zapytanie i otrzymujemy wyniki
            ResultSet resultSet = statement.executeQuery(sql);

            // Przechodzimy przez wyniki
            while (resultSet.next()) {
                // Wyświetlamy każdy rekord (zakładając, że tabela history ma kolumny id, name i date)
                int id = resultSet.getInt("id");
                String user_id = resultSet.getString("user_id");
                Date date = resultSet.getDate("date");
                int protein = resultSet.getInt("protein");
                int fat = resultSet.getInt("fat");
                int carbs = resultSet.getInt("carbs");
                int kcal = resultSet.getInt("kcal");

                System.out.println(id + " " + user_id + " " + date + " " + protein + " " + fat + " " + carbs + " " + kcal);
            }

            // Zamykamy połączenie
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
