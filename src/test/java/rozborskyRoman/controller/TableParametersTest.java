package rozborskyRoman.controller;


import rozborskyRoman.model.DBManager;
import org.junit.Before;
import org.junit.Test;
import rozborskyRoman.view.Console;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by roman on 03.05.2016.
 */
public class TableParametersTest {
    Console view;
    DBManager manager;
    TableParameters tableParameters;
    PrepareTable prepareTable;

    @Before
    public void setup() throws SQLException {
        prepareTable = new PrepareTable();
        manager = prepareTable.getManager();
        view = prepareTable.getView();
        manager.setTable("user");

        clearTable();
        insertValues();
        tableParameters = new TableParameters(manager, view);
    }


    @Test
    public void height(){
        int expectedHeight = 1;
        assertEquals(expectedHeight, tableParameters.getHeight());
    }

    @Test
    public void width(){
        int expectedWidth = 3;
        assertEquals(expectedWidth, tableParameters.getWidth());
    }

    @Test
    public void columnNames(){
        String [] columns = {"id", "name", "password"};
        assertArrayEquals(columns, tableParameters.getColumns());
    }

    private void insertValues() throws SQLException {
        Insert insert = new Insert(manager, view);
        String insertedValue = "";
        insertedValue += "1|1|1";
        System.setIn(new ByteArrayInputStream(insertedValue.getBytes()));
        insert.process();
    }

    private void clearTable() {
        Clear clear = new Clear(manager, view);
        String confirmation = "y";
        System.setIn(new ByteArrayInputStream(confirmation.getBytes()));
        clear.process();
    }
}
