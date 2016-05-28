package rozborskyRoman.controller;

import org.junit.Before;
import org.junit.Test;
import rozborskyRoman.view.Console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by roman on 03.05.2016.
 */
public class HelpTest {
    private final ByteArrayOutputStream outString = new ByteArrayOutputStream();
    Help help;

    @Before
    public void start() {
        System.setOut(new PrintStream(outString));
        Console view = new Console();
        help = new Help(view);

        Command[] commands = new Command[]{
                new List(null, null, null),
                new Find(null, null),
                new Insert(null, null),
                new Update(null, null),
                new Delete(null, null),
                new Clear(null, null),
                help,
                new Exit(view)};
        help.addCommands(commands);
    }

    @Test
    public void process() throws Exception {
        String expectedString = "\n" + "\n_____________________________________________________________________\n" +
                "HELP\n" +
                "Available commands:\n" +
                "_____________________________________________________________________\r\n" +
                "'list' - for a list of all database tables\r\n" +
                "_____________________________________________________________________\r\n" +
                "'find' - to obtain the contents of the current table\r\n" +
                "_____________________________________________________________________\r\n" +
                "'insert' - to write to the current table\r\n" +
                "_____________________________________________________________________\r\n" +
                "'update' - to update current table\r\n" +
                "_____________________________________________________________________\r\n" +
                "'delete' - to delete row\r\n" +
                "_____________________________________________________________________\r\n" +
                "'clear' - to clean up the current table\r\n" +
                "_____________________________________________________________________\r\n" +
                "'help' - to read help\r\n" +
                "_____________________________________________________________________\r\n" +
                "'exit' - for exit from the program\r\n" +
                "_____________________________________________________________________\r\n";
        help.process();
        assertEquals(expectedString, outString.toString());
    }
}
