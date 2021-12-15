package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
