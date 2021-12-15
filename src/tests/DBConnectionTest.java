package tests;


import activeRecord.DBConnection;
import activeRecord.JDBCException;
import org.junit.*;

import java.sql.Connection;

import static org.junit.Assert.*;

public class DBConnectionTest {

    Connection connection;
    Connection connection2;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getConnection() throws Exception {
        connection = DBConnection.getConnection();
        DBConnection.setNomDB("testSerie");
        assertNotNull(connection);
    }

    @Test
    public void getConnectionMultiple() throws Exception {
        connection = DBConnection.getConnection();
        connection2 = DBConnection.getConnection();
        DBConnection.setNomDB("testSerie");
        assertNotNull(connection);
        assertSame("Les instances devraient être les mêmes", connection, connection2);
    }

    @Test
    public void setNomDB() throws Exception {
        connection = DBConnection.getConnection();
        DBConnection.setNomDB("testSerie");
        assertEquals("testSerie", DBConnection.getNomDB());
    }
}