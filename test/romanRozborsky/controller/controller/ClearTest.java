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
    public void proces(){
        tableParameters = new TableParameters(manager, view);
        if (tableParameters.getHeight() == 0){
            System.err.println("Can't start test - table is empty");
            return;
        }
        prepareTable.clearTable();
        tableParameters = new TableParameters(manager, view);
        int hight = tableParameters.getHeight();
        assertEquals(0, hight);
    }
}