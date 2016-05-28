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
public class FindTest {
    private final ByteArrayOutputStream outString = new ByteArrayOutputStream();
    DBManager manager;
    Console view;
    PrepareTable prepareTable;

    @Before
    public void setup() throws SQLException {
        prepareTable = new PrepareTable();
        manager = prepareTable.getManager();
        view = prepareTable.getView();
        manager.setTable("user");
        System.setOut(new PrintStream(outString));

        prepareTable.clearTable();
    }

    @Test
    public void emptyTable() throws SQLException {
        Find find = new Find(manager, view);
        find.process();
        String expectedString = "Are you sure you want to clear the table 'user'? Yes - press 'y', no - press 'n'\r\n" +
                "Table 'user' was cleared\r\n" +
                "\n" +
                "\n" +
                "_____________________________________________________________________\r\n" +
                "table 'user' is empty\r\n" +
                "_____________________________________________________________________\r\n";
        assertEquals(expectedString, outString.toString());
    }

    @Test
    public void tableWithValues() throws SQLException {
        prepareTable.insertValues("1|1|1\r\n");
        prepareTable.insertValues("2|2|2\r\n");
        prepareTable.insertValues("3|3|3\r\n");
        Find find = new Find(manager, view);
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
                "_____________________________________________________________________\r\n" +
                "3                    3                    3                    \r\n" +
                "_____________________________________________________________________\r\n";
        assertEquals(expectedString, outString.toString());
    }
}