package romanRozborsky.controller.controller;

import model.DBManager;
import org.junit.Before;
import org.junit.Test;
import view.Console;

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
        clearTable();
        insertValues("1|1|1");
        insertValues("2|2|2");
        insertValues("3|3|3");
    }

    @Test
    public void proces(){
        tableParameters = new TableParameters(manager, view);
        if (tableParameters.getHeight() == 0){
            System.err.println("Can't start test - table is empty");
            return;
        }
        clearTable();
        tableParameters = new TableParameters(manager, view);
        int hight = tableParameters.getHeight();
        assertEquals(0, hight);
    }

    private void clearTable() {
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