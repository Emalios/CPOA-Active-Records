package tests;

import activeRecord.DBConnection;
import activeRecord.JDBCException;
import activeRecord.Serie;
import org.junit.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestSerie {

    Connection connection;
    Connection connection2;
    private static Serie serie1 = new Serie("serie1","genre1");
    private static Serie serie2 = new Serie("serie2","genre1");
    private static Serie serie3 = new Serie("serie3","genre2");
    private static Serie serie4 = new Serie("serie4","genre3");
    private static List<Serie> listSeries = new ArrayList<>();

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

    @After
    public void tearDown() throws Exception {
        Serie.deleteTable();
    }

    @Test
    public void testFindAll(){
        assertEquals(listSeries,Serie.findAll());
    }

    @Test
    public void testFindByName(){
        List<Serie> listSeries2 = new ArrayList<>();
        listSeries.add(serie3);
        assertEquals(listSeries,Serie.findByName("serie"));
        assertEquals(listSeries2,Serie.findByName("3"));
    }

    @Test
    public void testFindByGenre(){
        List<Serie> listSeries2 = new ArrayList<>();
        listSeries2.add(serie1);
        listSeries2.add(serie2);
        assertEquals(listSeries2,Serie.findByGenre("genre1"));
        assertEquals(listSeries,Serie.findByGenre("genre"));
    }
}
