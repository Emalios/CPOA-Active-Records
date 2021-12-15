package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
     * Se connecte à la base et retourne tous les personnages de la table
     * @return liste de tous les personnages
     */
    public static List<Personnage> findAll() {
        List<Personnage> personnages = new ArrayList<>();
        String SQLPrep = "SELECT * FROM Personnage;";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement prep1 = con.prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                int idSerie = rs.getInt("id_serie");
                int resId = rs.getInt("id");
                Personnage personnage = new Personnage(resId, nom, idSerie);
                personnage.id = resId;
                personnages.add(personnage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnages;
    }

    /**
     * Se connecte a la base et retourne les personnages dont le nom contient ce qui est passe en parametre
     * @param search nom
     * @return liste de personnage
     */
    public static List<Personnage> findByName(String search) {
        List<Personnage> personnages = new ArrayList<>();
        try {
            // s'il y a un resultat
            Connection con = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM personnage WHERE nom like ?";
            PreparedStatement prep1 = con.prepareStatement(SQLPrep);
            prep1.setString(1, "%" + search + "%");
            prep1.executeQuery();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                int idSerie = rs.getInt("id_serie");
                int resId = rs.getInt("id");
                Personnage personnage = new Personnage(resId, nom, idSerie);
                personnage.id = resId;
                personnages.add(personnage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnages;
    }

    /**
     * Se connecte a la base et retourne les personnages dont le genre est passe en parametre
     * @param serie serie
     * @return liste de personnages
     */
    public static List<Personnage> findBySerie(Serie serie) throws SerieAbsenteException {
        List<Personnage> personnages = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM Personnage WHERE id_serie=?;";
            PreparedStatement prep1 = con.prepareStatement(SQLPrep);
            int serieId = serie.getId();
            if(serieId == -1) throw new SerieAbsenteException();
            prep1.setInt(1, serie.getId());
            prep1.executeQuery();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                int idSerie = rs.getInt("id_serie");
                int resId = rs.getInt("id");
                Personnage personnage = new Personnage(resId, nom, idSerie);
                personnage.id = resId;
                personnages.add(personnage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnages;
    }

    /**
     * Se connecte a la base et retourne les personnages qui font partie d'une série dont le genre est égale au genre donnée en paramètre
     * @param genre genre de la serie
     * @return liste de personnages
     */
    public static List<Personnage> findByGenre(String genre) throws SerieAbsenteException {
        List<Personnage> personnages = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM Personnage";
            PreparedStatement prep1 = con.prepareStatement(SQLPrep);
            prep1.executeQuery();
            ResultSet rs = prep1.getResultSet();
            //deuxième requête pour récupèrer genre de la serie
            String genreRequest = "SELECT genre FROM serie where id=?";
            PreparedStatement prep2 = con.prepareStatement(genreRequest);
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                int idSerie = rs.getInt("id_serie");
                if(idSerie == -1) throw new SerieAbsenteException();
                int resId = rs.getInt("id");
                prep2.setInt(1, idSerie);
                ResultSet requestRs = prep2.executeQuery();
                if(requestRs.next()) {
                    String genreSerie = requestRs.getString("genre");
                    if(genreSerie.equals(genre)) {
                        Personnage personnage = new Personnage(resId, nom, idSerie);
                        personnage.id = resId;
                        personnages.add(personnage);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnages;
    }

    public int getId() {
        return id;
    }
    public String getNom(){
        return nom;
    }

    /**
     * méthode retournant la Série associé au personnage
     * @return Serie
     */
    public Serie getSerie() {
        return Serie.findById(idSerie);
    }

    /**
     * méthode créant la table Personnage dans la base de donnée
     * @throws SQLException
     */
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

    /**
     * Méthode supprimant la table Personnage dans la base de donnée
     * @throws SQLException
     */
    public static void deleteTable() throws SQLException {
        Connection con = DBConnection.getConnection();
        String SQLPrep = "DROP TABLE personnage";
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
        String SQLPrep = "DELETE FROM personnage WHERE ID=?";
        PreparedStatement prep1 = con.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.executeUpdate();
        id = -1;
    }

    /**
     * Méthode permattant d'actualiser la base de donnée avec l'instance actuelle de Personnage
     * @throws SQLException
     */
    public void save() throws SQLException, SerieAbsenteException {
        if(idSerie == -1) throw new SerieAbsenteException();
        if(id == -1) saveNew();
        else update();
    }

    /**
     * Méthode permattant d'enregister un nouveau Personnage dans la base de donnée
     * @throws SQLException
     */
    private void saveNew() throws SQLException {
        String SQLPrep = "INSERT INTO Personnage (nom, id_serie) VALUES (?,?)";
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

    /**
     * Méthode permettant de mettre à jour l'instance courrante dans la base de donnée
     * @throws SQLException
     */
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
