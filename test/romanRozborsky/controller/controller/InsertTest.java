package romanRozborsky.controller.controller;

import model.DBManager;
import org.junit.Before;
import org.junit.Test;
import view.Console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by roman on 05.05.2016.
 */
public class InsertTest {
    private final ByteArrayOutputStream outString = new ByteArrayOutputStream();
    Console view;
    DBManager manager;
    TableParameters tableParameters;
    Find find;

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
        System.setOut(new PrintStream(outString));
        find = new Find(manager, view);
    }

    @Test
    public void notCorrectId(){
        clearTable();
        insertValues("a|1|1");
        tableParameters = new TableParameters(manager, view);
        int row = tableParameters.getHeight();
        assertEquals(0, row);
    }

    @Test
    public void correctId(){
        clearTable();
        insertValues("1|1|1");
        insertValues("2|2|2");
        tableParameters = new TableParameters(manager, view);
        int row = tableParameters.getHeight();
        find.process();
        String expectedString = "Are you sure you want to clear the table 'user'? Yes - press 'y', no - press 'n'\r\n" +
                "Table 'user' was cleared\r\n" +
                "\n" +
                "Insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\r\n" +
                "\n" +
                "Table user was updated\r\n" +
                "\n" +
                "Insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\r\n" +
                "\n" +
                "Table user was updated\r\n" +
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