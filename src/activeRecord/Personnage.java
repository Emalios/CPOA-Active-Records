package activeRecord;

import java.sql.*;
import java.util.Objects;

public class Personnage {

    private String nom;
    private int id, idSerie;

    public Personnage(String nom, Serie serie) {
        this.id = -1;
        this.nom = nom;
        this.idSerie = serie.getId();
    }

    private Personnage(int id, String nom, int idSerie) {
        this.id = id;
        this.nom = nom;
        this.idSerie = idSerie;
    }

    /**
     * Se connecte a la base et retourne le personnage dont l'id est passe en parametre
     * @param id id du personnage
     * @return instance de personnage
     */
    public static Personnage findById(int id) {
        Personnage personnage = null;
        try {
            Connection con = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM Personnage WHERE id=?;";
            PreparedStatement prep1 = con.prepareStatement(SQLPrep);
            prep1.setInt(1, id);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            if (rs.next()) {
                String nom = rs.getString("nom");
                int idSerie = rs.getInt("id_serie");
                int resId = rs.getInt("id");
                personnage = new Personnage(resId, nom, idSerie);
                personnage.id = resId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnage;
    }

    /**
     * méthode retournant la Série associé au personnage
     * @return Serie
     */
    public Serie getSerie() {
        return Serie.findById(idSerie);
    }

    public static void createTable() throws SQLException {
        Serie.createTable();
        Connection con = DBConnection.getConnection();
        String SQLPrep = "CREATE TABLE Personnage (" +
                "  ID int(11) NOT NULL AUTO_INCREMENT," +
                "  NOM varchar(40) NOT NULL," +
                "  ID_SERIE int(11) NOT NULL, " +
                " PRIMARY KEY ( ID )" +
                ")";
        PreparedStatement prep1 = con.prepareStatement(SQLPrep);
        prep1.executeUpdate();
        //ajout des contraintes
        PreparedStatement contrainte = con.prepareStatement("ALTER TABLE personnage" +
                "  ADD CONSTRAINT personnage_ibfk_1 FOREIGN KEY (ID_SERIE) REFERENCES serie (ID)");
        contrainte.executeUpdate();
    }

    public static void deleteTable() throws SQLException {
        Connection con = DBConnection.getConnection();
        String SQLPrep = "DROP TABLE personnage";
        PreparedStatement prep1 = con.prepareStatement(SQLPrep);
        prep1.executeUpdate();
    }

    public void delete() throws SQLException {
        if(id == -1) return;
        Connection con = DBConnection.getConnection();
        String SQLPrep = "DELETE FROM personnage WHERE ID=?";
        PreparedStatement prep1 = con.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.executeUpdate();
        id = -1;
    }

    public void save() throws SQLException, SerieAbsenteException {
        if(idSerie == -1) throw new SerieAbsenteException();
        if(id == -1) saveNew();
        else update();
    }

    private void saveNew() throws SQLException {
        String SQLPrep = "INSERT INTO Personnage (nom, genre) VALUES (?,?)";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, nom);
        prep.setInt(2, idSerie);
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

    private void update() throws SQLException {
        String SQLPrep = "UPDATE serie " +
                "SET nom = ?," +
                "  id_serie = ?" +
                "WHERE id = ?";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, nom);
        prep.setInt(2, idSerie);
        prep.setInt(3, id);
        prep.executeUpdate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personnage that = (Personnage) o;
        return id == that.id && idSerie == that.idSerie && Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, id, idSerie);
    }
}
