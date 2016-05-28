package rozborskyRoman.controller;

import rozborskyRoman.model.DBManager;
import org.junit.Before;
import org.junit.Test;
import rozborskyRoman.view.Console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by roman on 05.05.2016.
 */
public class UpdateTest {
    private final ByteArrayOutputStream outString = new ByteArrayOutputStream();
    Console view;
    DBManager manager;
    Find find;
    PrepareTable prepareTable;

    @Before
    public void setup() throws SQLException {
        prepareTable = new PrepareTable();
        manager = prepareTable.getManager();
        view = prepareTable.getView();
        manager.setTable("user");

        System.setOut(new PrintStream(outString));
        find = new Find(manager, view);
        prepareTable.clearTable();
        prepareTable.insertValues("1|1|1\r\n");
        prepareTable.insertValues("2|2|2\r\n");
        prepareTable.insertValues("3|3|3\r\n");
    }

    @Test
    public void notExistId() throws SQLException {
        Update update = new Update(manager, view);
        String id = "4|4|4\r\n";
        InputStream inputStream = new ByteArrayInputStream(id.getBytes());
        System.setIn(inputStream);
        update.process();
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
                "To update table insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\r\n" +
                "Can't update the table, row with entered id is not exist\r\n";
        assertEquals(expectedString, outString.toString());
    }

    @Test
    public void existId() throws SQLException {
        Update update = new Update(manager, view);
        String id = "2|5|5\r\n";
        InputStream inputStream = new ByteArrayInputStream(id.getBytes());
        System.setIn(inputStream);
        update.process();
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
                "To update table insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\r\n" +
                "Table 'user' was updated\r\n" +
                "\n" +
                "\n" +
                "_____________________________________________________________________\r\n" +
                "user\r\n" +
                "_____________________________________________________________________\r\n" +
                "1                    1                    1                    \r\n" +
                "_____________________________________________________________________\r\n" +
                "2                    5                    5                    \r\n" +
                "_____________________________________________________________________\r\n" +
                "3                    3                    3                    \r\n" +
                "_____________________________________________________________________\r\n";
        assertEquals(expectedString, outString.toString());
    }
}