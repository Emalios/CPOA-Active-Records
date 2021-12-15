package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant la table Serie
 */
public class Serie {

    /**
     * ID de la serie
     */
    private int id;

    /**
     * Nom de la serie
     */
    private String nom;

    /**
     * Date de debut de la serie
     */
    private String genre;

    /**
     * Constructeur par defaut
     */
    public Serie(String n, String g) {
        this.id = -1;
        this.nom = n;
        this.genre = g;
    }

    /**
     * Se connecte à la base et retourne toutes les series de la table
     * @return liste de toutes les séries
     */
    public static List<Serie> findAll() {
        List<Serie> series = new ArrayList<>();
        String SQLPrep = "SELECT * FROM Serie;";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement prep1 = con.prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                String genre = rs.getString("genre");
                int id = rs.getInt("id");
                Serie serie = new Serie(nom, genre);
                serie.id = id;
                series.add(serie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return series;
    }

    /**
     * Se connecte a la base et retourne la serie dont l'id est passe en parametre
     * @param id id de la serie
     * @return serie
     */
    public static Serie findById(int id) {
        Serie serie = null;
        try {
            Connection con = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM Serie WHERE id=?;";
            PreparedStatement prep1 = con.prepareStatement(SQLPrep);
            prep1.setInt(1, id);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            if (rs.next()) {
                String nom = rs.getString("nom");
                String genre = rs.getString("genre");
                int resId = rs.getInt("id");
                serie = new Serie(nom, genre);
                serie.id = resId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serie;
    }

    /**
     * Se connecte a la base et retourne les series dont le nom contient la chaine 'search' passé en paramètre
     * @param search nom contenue dans la serie
     * @return liste de series
     */
    public static List<Serie> findByName(String search) {
        List<Serie> series = new ArrayList<>();
        try {
            // s'il y a un resultat
            Connection con = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM serie WHERE nom like ?";
            PreparedStatement prep1 = con.prepareStatement(SQLPrep);
            prep1.setString(1, "%" + search + "%");
            prep1.executeQuery();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                String genre = rs.getString("genre");
                int resId = rs.getInt("id");
                Serie serie = new Serie(nom, genre);
                serie.id = resId;
                series.add(serie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return series;
    }

    /**
     * Se connecte a la base et retourne les series dont le genre est passe en parametre
     * @param genre genre de la serie
     * @return liste de series serie
     */
    public static List<Serie> findByGenre(String genre) {
        List<Serie> series = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM Serie WHERE genre=?;";
            PreparedStatement prep1 = con.prepareStatement(SQLPrep);
            prep1.setString(1, genre);
            prep1.executeQuery();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                String gnre = rs.getString("genre");
                int resId = rs.getInt("id");
                Serie serie = new Serie(nom, gnre);
                serie.id = resId;
                series.add(serie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return series;
    }

    /**
     * méthode créant la table Serie dans la base de donnée
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        Connection con = DBConnection.getConnection();
        String SQLPrep = "CREATE TABLE Serie" +
                "                   (id int(11) not NULL AUTO_INCREMENT," +
                "                    nom varchar(40) not NULL," +
                "                    genre varchar(40) not null, " +
                "                    PRIMARY KEY ( id ))";
        PreparedStatement prep1 = con.prepareStatement(SQLPrep);
        prep1.executeUpdate();
    }

    /**
     * Méthode supprimant la table Serie dans la base de donnée
     * @throws SQLException
     */
    public static void deleteTable() throws SQLException {
        //besoin de supprimer Personnage avant de supprimer série à cause des contraintes de clés étrangeres
        try {
            Personnage.deleteTable();
        } catch (SQLException ignored) {}
        Connection con = DBConnection.getConnection();
        String SQLPrep = "DROP TABLE serie";
        PreparedStatement prep1 = con.prepareStatement(SQLPrep);
        prep1.executeUpdate();
    }

    /**
     * Méthode supprimant l'instance courrante dans la base de donnée
     * @throws SQLException
     */
    public void delete() throws SQLException {
        if(id == -1) return;
        Connection con = DBConnection.getConnection();
        String SQLPrep = "DELETE FROM serie WHERE ID=?";
        PreparedStatement prep1 = con.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.executeQuery();
        id = -1;
    }

    /**
     * Méthode permattant d'actualiser la base de donnée avec l'instance actuelle de serie
     * @throws SQLException
     */
    public void save() throws SQLException {
        if(id == -1) saveNew();
        else update();
    }

    /**
     * Méthode permattant d'enregister une nouvelle Serie dans la base de donnée
     * @throws SQLException
     */
    private void saveNew() throws SQLException {
        String SQLPrep = "INSERT INTO Serie (nom, genre) VALUES (?,?)";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, nom);
        prep.setString(2, genre);
        prep.executeUpdate();
        // recuperation de la derniere ligne ajoutee (auto increment)
        // recupere le nouvel id
        int autoInc = -1;
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            autoInc = rs.getInt(1);
        }
        id = autoInc;
    }

    /**
     * Méthode permettant de mettre à jour l'instance courrante dans la base de donnée
     * @throws SQLException
     */
    private void update() throws SQLException {
        String SQLPrep = "UPDATE serie " +
                "SET nom = ?," +
                "  genre = ?" +
                "WHERE id = ?";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, nom);
        prep.setString(2, genre);
        prep.setInt(3, id);
        prep.executeUpdate();
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return id == serie.id && Objects.equals(nom, serie.nom) && Objects.equals(genre, serie.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, genre);
    }
}
