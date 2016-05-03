package romanRozborsky.controller.controller;
import static org.junit.Assert.*;
import model.DBManager;
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
    private Connector connector = new Connector();

    @Test
    public void getUrl() throws InvocationTargetException, IllegalAccessException {
        Method [] methods = connector.getClass().getDeclaredMethods();
        methods[1].setAccessible(true);
        String resultUrl = (String) methods[1].invoke(connector, view);
        String expectedUrl = "jdbc:postgresql://localhost:5432/";
        assertEquals(resultUrl, expectedUrl);
    }

    @Test
    public void create(){
        String connectParameters = "public|postgres|mainuser";
        System.setIn(new ByteArrayInputStream(connectParameters.getBytes()));
        DBManager manager = connector.create(view);
        assertNotNull(manager);
    }
}
