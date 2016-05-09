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
    PrepareTable prepareTable;

    @Before
    public void setup(){
        prepareTable = new PrepareTable();
        manager = prepareTable.getManager();
        view = prepareTable.getView();
        manager.setTable("user");

        prepareTable.clearTable();
        prepareTable.insertValues("1|1|1");
        prepareTable.insertValues("2|2|2");
        prepareTable.insertValues("3|3|3");
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
}