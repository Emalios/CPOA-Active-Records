package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PrincipaleJDBC {

    //IL FAUT PENSER A AJOUTER MYSQLCONNECTOR AU CLASSPATH

    public static void main(String[] args) throws SQLException {


        // variables a modifier en fonction de la base
        String userName = "root";
        String password = "";
        String serverName = "localhost";
        //Attention, sous MAMP, le port est 8889
        String portNumber = "3306";
        String tableName = "Serie";

        // iL faut une base nommee testSerie !
        String dbName = "testSerie";

        // creation de la connection
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;


        Connection connect = DBConnection.getConnection();
        // creation de la table Serie
        {
            String createString = "CREATE TABLE Serie ( " + "ID INTEGER  AUTO_INCREMENT, "
                    + "NOM varchar(40) NOT NULL, " + "GENRE varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(createString);
            System.out.println("1) creation table Serie\n");
        }

        // ajout de serie avec requete preparee
        {
            String SQLPrep = "INSERT INTO Serie (nom, genre) VALUES (?,?);";
            PreparedStatement prep;
            // l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
            // auto-increment)
            prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, "Guimauve Throne");
            prep.setString(2, "Fantasy");
            prep.executeUpdate();
            System.out.println("2) ajout \"Guimauve Throne\"\n");
        }

        // ajout seconde serie
        {
            String SQLPrep = "INSERT INTO Serie (nom, genre) VALUES (?,?);";
            PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, "Docteur Qui");
            prep.setString(2, "Science-fiction");
            prep.executeUpdate();
            System.out.println("3) ajout \"Docteur Qui\"");

            // recuperation de la derniere ligne ajoutee (auto increment)
            // recupere le nouvel id
            int autoInc = -1;
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()) {
                autoInc = rs.getInt(1);
            }
            System.out.print("  ->  id utilise lors de l'ajout : ");
            System.out.println(autoInc);
            System.out.println();
        }

        // recuperation de toutes les series + affichage
        {
            System.out.println("4) Recupere les series de la table Serie");
            String SQLPrep = "SELECT * FROM Serie;";
            PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                String genre = rs.getString("genre");
                int id = rs.getInt("id");
                System.out.println("  -> (" + id + ") " + nom + ", " + genre);
            }
            System.out.println();
        }

        // suppression de la serie 1
        {
            PreparedStatement prep = connect.prepareStatement("DELETE FROM Serie WHERE id=?");
            prep.setInt(1, 1);
            prep.execute();
            System.out.println("5) Suppression serie id 1 (\"Guimauve Throne\")");
            System.out.println();
        }

        // recuperation de la seconde serie + affichage
        {
            System.out.println("6) Recupere serie d'id 2");
            String SQLPrep = "SELECT * FROM Serie WHERE id=?;";
            PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
            prep1.setInt(1, 2);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            if (rs.next()) {
                String nom = rs.getString("nom");
                String genre = rs.getString("genre");
                int id = rs.getInt("id");
                System.out.println("  -> (" + id + ") " + nom + ", " + genre);
            }
            System.out.println();
        }

        // met a jour serie 2
        {
            String SQLprep = "update Serie set nom=?, genre=? where id=?;";
            PreparedStatement prep = connect.prepareStatement(SQLprep);
            prep.setString(1, "Docteur Qui ???");
            prep.setString(2, "Science-Fiction-Bizarre");
            prep.setInt(3, 2);
            prep.execute();
            System.out.println("7) Effectue modification Serie id 2");
            System.out.println();
        }

        // recuperation de la seconde serie + affichage
        {
            System.out.println("8) Affiche Serie id 2 apres modification");
            String SQLPrep = "SELECT * FROM Serie WHERE id=?;";
            PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
            prep1.setInt(1, 2);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            if (rs.next()) {
                String nom = rs.getString("nom");
                String genre = rs.getString("genre");
                int id = rs.getInt("id");
                System.out.println("  -> (" + id + ") " + nom + ", " + genre);
            }
            System.out.println();
        }

        // suppression de la table serie
        {
            String drop = "DROP TABLE Serie";
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(drop);
            System.out.println("9) Supprime table Serie");
        }

    }

}