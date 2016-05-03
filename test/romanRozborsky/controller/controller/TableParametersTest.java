package romanRozborsky.controller.controller;

import model.DBManager;
import org.junit.Before;
import org.junit.Test;
import view.Console;

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

    @Before
    public void setup(){
        view = new Console();
        manager = new DBManager("public", "postgres", "mainuser", "jdbc:postgresql://localhost:5432/");
        manager.setTable("user");

        try {
            manager.connection();
        }catch (SQLException e){
            //do noting
        }
        tableParameters = new TableParameters(manager, view);

        clearTable();
        insertValues();
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

    private void insertValues() {
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
