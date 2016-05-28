package rozborskyRoman.controller;

import rozborskyRoman.model.DBManager;
import org.junit.Before;
import org.junit.Test;
import rozborskyRoman.view.Console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by roman on 05.05.2016.
 */
public class InsertTest {
    private final ByteArrayOutputStream outString = new ByteArrayOutputStream();
    Console view;
    DBManager manager;
    Find find;
    PrepareTable prepareTable;

    @Before
    public void setup() {
        prepareTable = new PrepareTable();
        manager = prepareTable.getManager();
        view = prepareTable.getView();
        manager.setTable("user");

        System.setOut(new PrintStream(outString));
        find = new Find(manager, view);
    }

    @Test
    public void notCorrectId() throws SQLException {
        prepareTable.clearTable();
        prepareTable.insertValues("a|1|1");
        int row = manager.getTableHight();
        assertEquals(0, row);
    }

    @Test
    public void correctId() throws SQLException {
        prepareTable.clearTable();
        prepareTable.insertValues("1|1|1\r\n");
        prepareTable.insertValues("2|2|2\r\n");
        find.process();
        String expectedString = "Are you sure you want to clear the table 'user'? Yes - press 'y', no - press 'n'\r\n" +
                "Table 'user' was cleared\r\n" +
                "\n" +
                "Insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\r\n" +
                "\n" +
                "Table 'user' was updated\r\n" +
                "\n" +
                "Insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\r\n" +
                "\n" +
                "Table 'user' was updated\r\n" +
                "\n" +
                "\n" +
                "_____________________________________________________________________\r\n" +
                "user\r\n" +
                "_____________________________________________________________________\r\n" +
                "1                    1                    1                    \r\n" +
                "_____________________________________________________________________\r\n" +
                "2                    2                    2                    \r\n" +
                "_____________________________________________________________________\r\n";

        assertEquals(expectedString, outString.toString());
    }
}