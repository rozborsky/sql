package rozborskyRoman.controller;

import rozborskyRoman.model.DBManager;
import org.junit.Before;
import org.junit.Test;
import rozborskyRoman.view.Console;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by roman on 04.05.2016.
 */
public class ClearTest {
    Console view;
    DBManager manager;
    PrepareTable prepareTable;

    @Before
    public void setup() throws SQLException {
        prepareTable = new PrepareTable();
        manager = prepareTable.getManager();
        view = prepareTable.getView();
        manager.setTable("user");

        prepareTable.clearTable();
        prepareTable.insertValues("1|1|1\r\n");
        prepareTable.insertValues("2|2|2\r\n");
        prepareTable.insertValues("3|3|3\r\n");
    }

    @Test
    public void proces() throws SQLException {
        assertTrue("Can't start test - table is empty", manager.getTableHight() >= 1);

        Clear clear = new Clear(manager, view);
        String confirmation = "y\r\n";
        InputStream iStream = new ByteArrayInputStream(confirmation.getBytes());
        System.setIn(iStream);
        clear.process();

        int hight = manager.getTableHight();
        assertEquals(0, hight);
    }
}