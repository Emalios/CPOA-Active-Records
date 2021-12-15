package tests;

import activeRecord.DBConnection;
import activeRecord.JDBCException;
import activeRecord.Personnage;
import activeRecord.Serie;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class PersonnageTest {

    Connection connection;
    Connection connection2;

    @Before
    public void setUp() throws SQLException {
        Serie.deleteTable();
        Personnage.createTable();
    }

    @After
    public void tearDown() throws SQLException {
        Personnage.deleteTable();
    }

    @Test
    public void test() throws SQLException {

    }

}
