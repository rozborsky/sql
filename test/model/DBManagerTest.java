package model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import romanRozborsky.controller.controller.Clear;
import romanRozborsky.controller.controller.Insert;
import view.Console;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by roman on 03.05.2016.
 */
public class DBManagerTest {
    static DBManager manager;
    static Connection connection;
    static Console view = new Console();

    @BeforeClass
    static public void process(){
        manager = new DBManager("public", "postgres", "mainuser", "jdbc:postgresql://localhost:5432/");
        try {
            connection = manager.connection();
        }catch (SQLException e){
            //do nothing
        }
        manager.setTable("user");
    }

    @Before
    public void before(){
        clearTable();
    }


    @Test
    public void connection(){
        connection = null;
        try {
            connection = manager.connection();
        }catch (SQLException e){
            //do nothing
        }
        assertNotNull(connection);
    }

    @Test
    public void clear(){
        insertValues("1|1|1");
        insertValues("2|2|2");
        insertValues("3|3|3");
        int rows = 3;
        try{
            manager.clear();
            rows = manager.tableHight();
        }catch (SQLException e){
            assertTrue(false);
        }
        assertEquals(0, rows);
    }

    @Test
    public void delete(){
        insertValues("1|1|1");
        insertValues("2|2|2");
        insertValues("3|3|3");
        try{
            manager.delete("2");
        }catch (SQLException e){
            assertTrue(false);
        }
        boolean result = manager.isExists(1) && !manager.isExists(2) && manager.isExists(3);
        assertTrue(result);
    }

    @Test
    public void insert(){
        insertValues("1|1|1");
        insertValues("2|2|2");
        String enteredValues = "3', '3', '3";
        String columns = "id, name, password";
        try{
            manager.insert(enteredValues, columns);
        }catch(SQLException e){
            assertTrue(false);
        }
        assertTrue(manager.isExists(3));
    }

    @Test
    public void update(){
        insertValues("1|1|1");
        insertValues("2|2|2");
        insertValues("3|3|3");
        String [] newValues = {"2", "4", "4"};
        try {
            manager.update("id = ?", "name = ?, password", newValues);
        }catch (SQLException e){
            //do nothing
        }
        assertTrue(manager.isExists(2));//TODO are equals?
    }

    @Test
    public void isExist(){
        insertValues("1|1|1");
        insertValues("2|2|2");
        assertTrue(manager.isExists(2));
        assertFalse(manager.isExists(3));
    }

    @Test
    public void list(){
        String [] tables = null;
        try {
            tables = manager.list();
        }catch (SQLException e){
            assertTrue(false);
        }
        String [] expectedTables = {"user", "test", "enotherTest"};
        assertArrayEquals(tables, expectedTables);
    }

    @Test
    public void tableWidth(){
        int width = 0;
        try {
            width = manager.tableWidth();
        }catch (SQLException e){
            assertTrue(false);
        }
        int expectedWidth = 3;
        assertEquals(expectedWidth, width);
    }

    @Test
    public void tableHight(){
        int rows = 0;
        try{
            rows = manager.tableHight();
        }catch (SQLException e){
            assertTrue(false);
        }
        int expectedRows = 0;
        assertEquals(expectedRows, rows);

        insertValues("1|1|1");
        insertValues("2|2|2");
        try{
            rows = manager.tableHight();
        }catch (SQLException e){
            assertTrue(false);
        }
        expectedRows = 2;
        assertEquals(expectedRows, rows);
    }

    @Test
    public void  getColumnNames(){
        String [] columnNames = null;
        try{
            columnNames = manager.getColumnNames(manager.tableWidth());
        }catch (SQLException e){
            assertTrue(false);
        }
        String [] expectedNames = {"id", "name", "password"};
        assertArrayEquals(expectedNames, columnNames);
    }

    @Test
    public void find(){
        insertValues("1|1|1");
        insertValues("2|2|2");
        String [] rows = null;
        try{
            rows = manager.find();
        }catch (SQLException e){
            assertTrue(false);
        }
        String [] expectedRows = {"1|1|1", "2|2|2"};
        assertArrayEquals(expectedRows, rows);
    }





    static private void clearTable() {
        Clear clear = new Clear(manager, view);
        String confirmation = "y";
        InputStream iStream = new ByteArrayInputStream(confirmation.getBytes());
        System.setIn(iStream);
        clear.process();
    }

    private void insertValues(String insertedValue) {
        Insert insert = new Insert(manager, view);
        InputStream inputStream = new ByteArrayInputStream(insertedValue.getBytes());
        System.setIn(inputStream);
        insert.process();
    }
}