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
 * Created by roman on 05.05.2016.
 */
public class DeleteTest {
    DBManager manager;
    Console view;

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
    public void process(){
        Delete delete = new Delete(manager, view);
        String idRow = "3";
        InputStream iStream = new ByteArrayInputStream(idRow.getBytes());
        System.setIn(iStream);
        delete.process();
        assertFalse(manager.isExists(3));
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