package romanRozborsky.controller.controller;
import static org.junit.Assert.*;
import model.DBManager;
import org.junit.Before;
import org.junit.Test;
import view.Console;

import java.io.*;

/**
 * Created by roman on 02.05.2016.
 */
public class ListTest {
    private final ByteArrayOutputStream outString = new ByteArrayOutputStream();
    DBManager manager;
    Console view;
    PrepareTable prepareTable;

    @Before
    public void setup(){
        prepareTable = new PrepareTable();
        manager = prepareTable.getManager();
        view = prepareTable.getView();
        System.setOut(new PrintStream(outString));
    }

    @Test
        public void process(){
        String [] tables = {"user", "test", "enotherTest"};
        List list = new List(manager, tables, view);
        list.process();
        String expectedString = "\nAvailable tables:\r\n[user, test, enotherTest]\r\n";
        assertEquals(expectedString, outString.toString());
    }
}
