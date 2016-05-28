package rozborskyRoman.controller;

import rozborskyRoman.model.DBManager;

import org.junit.Before;
import org.junit.Test;
import rozborskyRoman.view.Console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by roman on 02.05.2016.
 */
public class ListTest {
    private final ByteArrayOutputStream outString = new ByteArrayOutputStream();
    DBManager manager;
    Console view;
    PrepareTable prepareTable;

    @Before
    public void setup() {
        prepareTable = new PrepareTable();
        manager = prepareTable.getManager();
        view = prepareTable.getView();
        System.setOut(new PrintStream(outString));
    }

    @Test
    public void process() {
        String[] tables = {"user", "test", "enotherTest"};
        List list = new List(manager, tables, view);
        list.process();
        String expectedString = "\nAvailable tables:\r\n[user, test, enotherTest]\r\n";
        assertEquals(expectedString, outString.toString());
    }
}
