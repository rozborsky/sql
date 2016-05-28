package rozborskyRoman.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rozborskyRoman.controller.PrepareTable;
import rozborskyRoman.view.Console;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by roman on 03.05.2016.
 */
public class DBManagerTest {
    static DBManager manager;
    static Connection connection;
    static Console view;
    static PrepareTable prepareTable;

    @BeforeClass
    static public void process() {
        prepareTable = new PrepareTable();
        manager = prepareTable.getManager();
        view = prepareTable.getView();

        manager.setTable("user");
    }

    @Before
    public void before() throws SQLException {
        prepareTable.clearTable();
    }


    @Test
    public void connection() {
        connection = null;
        try {
            connection = manager.createConnection();
        } catch (SQLException e) {
            //do nothing
        }
        assertNotNull(connection);
    }

    @Test
    public void clear() throws SQLException {
        prepareTable.insertValues("1|1|1");
        prepareTable.insertValues("2|2|2");
        prepareTable.insertValues("3|3|3");
        int rows = 3;
        try {
            manager.clear();
            rows = manager.getTableHight();
        } catch (SQLException e) {
            assertTrue(false);
        }
        assertEquals(0, rows);
    }

    @Test
    public void delete() throws SQLException {
        prepareTable.insertValues("1|1|1");
        prepareTable.insertValues("2|2|2");
        prepareTable.insertValues("3|3|3");
        try {
            manager.delete("2");
        } catch (SQLException e) {
            assertTrue(false);
        }
        boolean result = manager.isExists(1) && !manager.isExists(2) && manager.isExists(3);
        assertTrue(result);
    }

    @Test
    public void insert() throws SQLException {
        prepareTable.insertValues("1|1|1");
        prepareTable.insertValues("2|2|2");
        String enteredValues = "3', '3', '3";
        String columns = "id, name, password";
        try {
            manager.insert(enteredValues, columns);
        } catch (SQLException e) {
            assertTrue(false);
        }
        assertTrue(manager.isExists(3));
    }

    @Test
    public void update() throws SQLException {
        prepareTable.insertValues("1|1|1");
        prepareTable.insertValues("2|2|2");
        prepareTable.insertValues("3|3|3");
        String[] newValues = {"2", "4", "4"};
        try {
            manager.update("id = ?", "name = ?, password", newValues);
        } catch (SQLException e) {
            //do nothing
        }
        assertTrue(manager.isExists(2));//TODO are equals?
    }

    @Test
    public void isExist() throws SQLException {
        prepareTable.insertValues("1|1|1");
        prepareTable.insertValues("2|2|2");
        assertTrue(manager.isExists(2));
        assertFalse(manager.isExists(3));
    }

    @Test
    public void list() {
        String[] tables = null;
        try {
            tables = manager.list();
        } catch (SQLException e) {
            assertTrue(false);
        }
        String[] expectedTables = {"user", "test", "enotherTest"};
        assertArrayEquals(tables, expectedTables);
    }

    @Test
    public void tableWidth() {
        int width = 0;
        try {
            width = manager.getTableWidth();
        } catch (SQLException e) {
            assertTrue(false);
        }
        int expectedWidth = 3;
        assertEquals(expectedWidth, width);
    }

    @Test
    public void tableHight() throws SQLException {
        int rows = 0;
        try {
            rows = manager.getTableHight();
        } catch (SQLException e) {
            assertTrue(false);
        }
        int expectedRows = 0;
        assertEquals(expectedRows, rows);

        prepareTable.insertValues("1|1|1");
        prepareTable.insertValues("2|2|2");
        try {
            rows = manager.getTableHight();
        } catch (SQLException e) {
            assertTrue(false);
        }
        expectedRows = 2;
        assertEquals(expectedRows, rows);
    }

    @Test
    public void getColumnNames() {
        String[] columnNames = null;
        try {
            columnNames = manager.getColumnNames();
        } catch (SQLException e) {
            assertTrue(false);
        }
        String[] expectedNames = {"id", "name", "password"};
        assertArrayEquals(expectedNames, columnNames);
    }

    @Test
    public void find() throws SQLException {
        prepareTable.insertValues("1|1|1");
        prepareTable.insertValues("2|2|2");
        String[] rows = null;
        try {
            rows = manager.getRows();
        } catch (SQLException e) {
            assertTrue(false);
        }
        String[] expectedRows = {"1|1|1", "2|2|2"};
        assertArrayEquals(expectedRows, rows);
    }
}