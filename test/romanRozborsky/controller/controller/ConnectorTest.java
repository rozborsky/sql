package romanRozborsky.controller.controller;
import static org.junit.Assert.*;
import model.DBManager;
import org.junit.Before;
import org.junit.Test;
import view.Console;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by roman on 01.05.2016.
 */
public class ConnectorTest {
    private Console view = new Console();
    private Connector connector;
    private PrepareTable prepareTable;;

    @Before
    public void setup(){
        prepareTable = new PrepareTable();
        connector = new Connector();
        view = prepareTable.getView();
    }

    @Test
    public void getUrl() throws InvocationTargetException, IllegalAccessException {
        Method [] methods = connector.getClass().getDeclaredMethods();
        methods[0].setAccessible(true);
        String resultUrl = (String) methods[0].invoke(connector, view);
        String expectedUrl = "jdbc:postgresql://localhost:5432/";
        assertEquals(resultUrl, expectedUrl);
    }

    @Test
    public void create(){
        String connectParameters = "public|postgres|mainuser";
        System.setIn(new ByteArrayInputStream(connectParameters.getBytes()));
        DBManager manager = connector.createConnection(view);
        assertNotNull(manager);
    }
}