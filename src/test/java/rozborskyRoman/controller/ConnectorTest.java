package rozborskyRoman.controller;

import rozborskyRoman.model.DBManager;
import org.junit.Before;
import org.junit.Test;
import rozborskyRoman.view.Console;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertNotNull;

/**
 * Created by roman on 01.05.2016.
 */
public class ConnectorTest {
    private Console view = new Console();
    private Connector connector;
    private PrepareTable prepareTable;

    @Before
    public void setup() {
        prepareTable = new PrepareTable();
        connector = new Connector();
        view = prepareTable.getView();
    }

    @Test
    public void create() {
        String connectParameters = "public|postgres|mainuser\r\n";
        System.setIn(new ByteArrayInputStream(connectParameters.getBytes()));
        DBManager manager = connector.createConnection(view);
        assertNotNull(manager);
    }
}