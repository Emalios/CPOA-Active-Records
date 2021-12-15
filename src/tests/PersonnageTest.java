package tests;

import activeRecord.DBConnection;
import activeRecord.JDBCException;
import org.junit.*;

import java.sql.Connection;

import static org.junit.Assert.*;

public class PersonnageTest {

    Connection connection;
    Connection connection2;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }
