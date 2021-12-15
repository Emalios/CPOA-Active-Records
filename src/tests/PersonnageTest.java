package tests;

import activeRecord.*;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class PersonnageTest {

    Connection connection;
    Connection connection2;

    private Serie s1 = new Serie("serie1", "genre1");
    private Serie s2 = new Serie("serie2", "genre2");
    private Serie s3 = new Serie("serie3", "genre3");
    private Personnage p1, p2, p3, p4;
    private List<Personnage> listAll;

    @Before
    public void setUp() throws SQLException, SerieAbsenteException {
        Personnage.createTable();
        s1.save();
        s2.save();
        p1 = new Personnage("p1", s1);
        p2 = new Personnage("p2", s1);
        p3 = new Personnage("p3", s2);
        p4 = new Personnage("p4", s2);
        listAll = List.of(p1, p2, p3, p4);
        listAll.forEach(personnage -> {
            try {
                personnage.save();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (SerieAbsenteException e) {

            }
        });
    }

    @After
    public void tearDown() throws SQLException {
        Personnage.deleteTable();
    }

    /**
     * Test s'assurant du bon fonctionne de la méthode findAll, censé retourner tous les tuples contenus dans la table Personnage
     */
    @Test
    public void testFindAll(){
        assertEquals(listAll,Personnage.findAll());
    }

}
