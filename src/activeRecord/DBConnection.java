package activeRecord;

import java.sql.*;
/**
 * @author condenseau, bergerat
 */
public class DBConnection {

    /**
     * nom de la base de donnees
     */
    private static String nomDB = "testSerie";
    /**
     * Le connexion (singleton)
     */
    private static Connection connection;
    /**
     * Le port sur lequel se connecter
     */
    private static String port = "3306";
    /**
     * Le login
     */
    private static String user = "root";
    private static String password = "";
    /**
     * url de la base de donnees
     */
    private static String url = "jdbc:mysql://localhost:" + port + "/" + nomDB;

    /**
     * Constructeur de la classe DBConnection
     */
    private DBConnection() {

        connection = null;
    }

    /**
     * Methode qui retourne une instance singleton d'une connection
     * @return la connection
     * @throws SQLException exception
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    /**
     * Methode qui permet de changer le nom de la database
     */
    public static void setNomDB(String nomDB) {
        url = "jdbc:mysql://localhost:" + port + "/" + nomDB;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode qui permet d'avoir le nom de la database
     */
    public static String getNomDB() {
        return nomDB;
    }


}