package rozborskyRoman.controller;


import rozborskyRoman.model.DBManager;
import org.junit.Before;
import org.junit.Test;
import rozborskyRoman.view.Console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.Assert.assertFalse;

/**
 * Created by roman on 05.05.2016.
 */
public class DeleteTest {
    private final ByteArrayOutputStream outString = new ByteArrayOutputStream();
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
    }

    @Test
    public void process(){
        prepareTable.insertValues("1|1|1");
        prepareTable.insertValues("2|2|2");
        prepareTable.insertValues("3|3|3");
        Delete delete = new Delete(manager, view);
        String idRow = "3";
        InputStream iStream = new ByteArrayInputStream(idRow.getBytes());
        System.setIn(iStream);
        delete.process();
        assertFalse(manager.isExists(3));
    }
}