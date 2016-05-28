package rozborskyRoman.integration;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by roman on 06.05.2016.
 */

public class IntegrationTest {
    private final ByteArrayOutputStream outString = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(outString));
    }

    @Test
    public void exit() {
        String insertedCommands =
                "public|postgres|mainuser\n" +
                        "exit";
        enterCommand(insertedCommands);
        MainForIntegrationTest.main(new String[0]);
        String expectedStringExit = "SQLCMD manager\n" +
                "\n" +
                "To connect to the database, enter the information in the format 'database_name|user_name|password'\n" +
                "\n" +
                "Connect to the database 'public' successful\n" +
                "\n" +
                "Insert 'list' to show available tables, 'help' for help or 'exit' to close program\n" +
                "Bye!\n" +
                "_____________________________________________________________________\n";
        assertEquals(expectedStringExit, outString.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    public void helpExit() {
        String insertedCommands =
                "public|postgres|mainuser\n" +
                        "help\n" +
                        "exit";
        enterCommand(insertedCommands);
        String expectedStringExit = "SQLCMD manager\n" +
                "\n" +
                "To connect to the database, enter the information in the format 'database_name|user_name|password'\n" +
                "\n" +
                "Connect to the database 'public' successful\n" +
                "\n" +
                "Insert 'list' to show available tables, 'help' for help or 'exit' to close program\n" +
                "\n" +
                "\n" +
                "_____________________________________________________________________\n" +
                "HELP\n" +
                "Available commands:\n" +
                "_____________________________________________________________________\n" +
                "'list' - for a list of all database tables\n" +
                "_____________________________________________________________________\n" +
                "'find' - to obtain the contents of the current table\n" +
                "_____________________________________________________________________\n" +
                "'insert' - to write to the current table\n" +
                "_____________________________________________________________________\n" +
                "'update' - to update current table\n" +
                "_____________________________________________________________________\n" +
                "'delete' - to delete row\n" +
                "_____________________________________________________________________\n" +
                "'clear' - to clean up the current table\n" +
                "_____________________________________________________________________\n" +
                "'help' - to read help\n" +
                "_____________________________________________________________________\n" +
                "'exit' - for exit from the program\n" +
                "_____________________________________________________________________\n" +
                "Insert 'list' to show available tables, 'help' for help or 'exit' to close program\n" +
                "Bye!\n" +
                "_____________________________________________________________________\n";
        MainForIntegrationTest.main(new String[0]);
        assertEquals(expectedStringExit, outString.toString().replaceAll("\r\n", "\n"));
    }

    private void enterCommand(String insertedCommands) {
        InputStream inputStream = new ByteArrayInputStream(insertedCommands.getBytes());
        System.setIn(inputStream);
    }

    @Test
    public void helpListTestFindExit() {
        String insertedCommands =
                "public|postgres|mainuser\n" +
                        "help\n" +
                        "list\n" +
                        "test\n" +
                        "find\n" +
                        "exit";
        enterCommand(insertedCommands);
        String expectedStringExit = "SQLCMD manager\n" +
                "\n" +
                "To connect to the database, enter the information in the format 'database_name|user_name|password'\n" +
                "\n" +
                "Connect to the database 'public' successful\n" +
                "\n" +
                "Insert 'list' to show available tables, 'help' for help or 'exit' to close program\n" +
                "\n" +
                "\n" +
                "_____________________________________________________________________\n" +
                "HELP\n" +
                "Available commands:\n" +
                "_____________________________________________________________________\n" +
                "'list' - for a list of all database tables\n" +
                "_____________________________________________________________________\n" +
                "'find' - to obtain the contents of the current table\n" +
                "_____________________________________________________________________\n" +
                "'insert' - to write to the current table\n" +
                "_____________________________________________________________________\n" +
                "'update' - to update current table\n" +
                "_____________________________________________________________________\n" +
                "'delete' - to delete row\n" +
                "_____________________________________________________________________\n" +
                "'clear' - to clean up the current table\n" +
                "_____________________________________________________________________\n" +
                "'help' - to read help\n" +
                "_____________________________________________________________________\n" +
                "'exit' - for exit from the program\n" +
                "_____________________________________________________________________\n" +
                "Insert 'list' to show available tables, 'help' for help or 'exit' to close program\n" +
                "\n" +
                "Available tables:\n" +
                "[user, test, enotherTest]\n" +
                "\n" +
                "Choose table\n" +
                "\n" +
                "To work with table 'test' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "\n" +
                "_____________________________________________________________________\n" +
                "table 'test' is empty\n" +
                "_____________________________________________________________________\n" +
                "\n" +
                "To work with table 'test' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "Bye!\n" +
                "_____________________________________________________________________\n";
        MainForIntegrationTest.main(new String[0]);
        assertEquals(expectedStringExit, outString.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    public void ListUserHelpExit() {
        String insertedCommands =
                "public|postgres|mainuser\n" +
                        "list\n" +
                        "user\n" +
                        "clear\n" +
                        "y\n" +
                        "find\n" +
                        "insert\n" +
                        "1|name1|password1\n" +
                        "insert\n" +
                        "2|name2|password2\n" +
                        "insert\n" +
                        "3|name3|password3\n" +
                        "insert\n" +
                        "4|name4|password4\n" +
                        "find\n" +
                        "delete\n" +
                        "2\n" +
                        "update\n" +
                        "3|newname|newpassword\n" +
                        "help\n" +
                        "find\n" +
                        "exit";
        enterCommand(insertedCommands);
        String expectedStringExit = "SQLCMD manager\n" +
                "\n" +
                "To connect to the database, enter the information in the format 'database_name|user_name|password'\n" +
                "\n" +
                "Connect to the database 'public' successful\n" +
                "\n" +
                "Insert 'list' to show available tables, 'help' for help or 'exit' to close program\n" +
                "\n" +
                "Available tables:\n" +
                "[user, test, enotherTest]\n" +
                "\n" +
                "Choose table\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "Are you sure you want to clear the table 'user'? Yes - press 'y', no - press 'n'\n" +
                "Table 'user' was cleared\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "\n" +
                "_____________________________________________________________________\n" +
                "table 'user' is empty\n" +
                "_____________________________________________________________________\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "Insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\n" +
                "\n" +
                "Table 'user' was updated\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "Insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\n" +
                "\n" +
                "Table 'user' was updated\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "Insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\n" +
                "\n" +
                "Table 'user' was updated\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "Insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\n" +
                "\n" +
                "Table 'user' was updated\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "\n" +
                "_____________________________________________________________________\n" +
                "user\n" +
                "_____________________________________________________________________\n" +
                "1                    name1                password1            \n" +
                "_____________________________________________________________________\n" +
                "2                    name2                password2            \n" +
                "_____________________________________________________________________\n" +
                "3                    name3                password3            \n" +
                "_____________________________________________________________________\n" +
                "4                    name4                password4            \n" +
                "_____________________________________________________________________\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "Insert id row, that should be removed, 'back' to enter another command or 'exit' to close program\n" +
                "Row was removed\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "To update table insert values in format id|name|password, 'back' to enter another command or 'exit' to close program\n" +
                "Table 'user' was updated\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "\n" +
                "_____________________________________________________________________\n" +
                "HELP\n" +
                "Available commands:\n" +
                "_____________________________________________________________________\n" +
                "'list' - for a list of all database tables\n" +
                "_____________________________________________________________________\n" +
                "'find' - to obtain the contents of the current table\n" +
                "_____________________________________________________________________\n" +
                "'insert' - to write to the current table\n" +
                "_____________________________________________________________________\n" +
                "'update' - to update current table\n" +
                "_____________________________________________________________________\n" +
                "'delete' - to delete row\n" +
                "_____________________________________________________________________\n" +
                "'clear' - to clean up the current table\n" +
                "_____________________________________________________________________\n" +
                "'help' - to read help\n" +
                "_____________________________________________________________________\n" +
                "'exit' - for exit from the program\n" +
                "_____________________________________________________________________\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "\n" +
                "\n" +
                "_____________________________________________________________________\n" +
                "user\n" +
                "_____________________________________________________________________\n" +
                "1                    name1                password1            \n" +
                "_____________________________________________________________________\n" +
                "3                    newname              newpassword          \n" +
                "_____________________________________________________________________\n" +
                "4                    name4                password4            \n" +
                "_____________________________________________________________________\n" +
                "\n" +
                "To work with table 'user' insert command, to see available tables write 'back' or 'exit' to live program, to read help insert 'help'\n" +
                "Bye!\n" +
                "_____________________________________________________________________\n";
        MainForIntegrationTest.main(new String[0]);
        assertEquals(expectedStringExit, outString.toString().replaceAll("\r\n", "\n"));
    }
}