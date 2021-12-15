package tests;

import activeRecord.DBConnection;
import activeRecord.JDBCException;
import activeRecord.Serie;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestSerie {

    private Connection connection;

    private static Serie serie1 = new Serie("serie1","genre1");
    private static Serie serie2 = new Serie("serie2","genre1");
    private static Serie serie3 = new Serie("serie3","genre2");
    private static Serie serie4 = new Serie("serie4","genre3");
    private static List<Serie> listSeries = new ArrayList<>();

    /**
     * méthode exécutée avant chaque test
     * @throws SQLException
     */
    @Before
    public void setUp() throws Exception {
        Serie.createTable();
        listSeries.add(serie1);
        listSeries.add(serie2);
        listSeries.add(serie3);
        listSeries.add(serie4);
        serie1.save();
        serie2.save();
        serie3.save();
        serie4.save();
    }

    /**
     * méthode exécutée après chaque test
     * @throws SQLException
     */
    @After
    public void tearDown() throws SQLException {
        Serie.deleteTable();
    }

    /**
     * Test s'assurant du bon fonctionnement de la méthode findAll, censée retourner tous les tuples contenus dans la table Serie
     */
    @Test
    public void testFindAll(){
        assertEquals(listSeries,Serie.findAll());
    }

    /**
     * Test de la méthode findByName, qui doit retourner tous les tuples qui ont dans le nom la chaîne en paramètre
     */
    @Test
    public void testFindByName(){
        List<Serie> listSeries2 = new ArrayList<>();
        listSeries2.add(serie3);
        assertEquals(listSeries,Serie.findByName("serie"));
        assertEquals(listSeries2,Serie.findByName("3"));
    }

    /**
     * Test de la méthode findByGenre, qui doit retourner tous les tuples qui ont comme genre la chaîne en paramètre
     */
    @Test
    public void testFindByGenre(){
        List<Serie> listSeries2 = new ArrayList<>();
        listSeries2.add(serie1);
        listSeries2.add(serie2);
        assertEquals(listSeries2,Serie.findByGenre("genre1"));
        assertEquals(new ArrayList<>(),Serie.findByGenre("genre"));
    }
}
