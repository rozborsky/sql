package rozborskyRoman.controller;

import rozborskyRoman.model.DBManager;
import rozborskyRoman.view.InputOutput;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by roman on 27.04.2016.
 */
public class Connector {

    private String url = null;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find jdbc driver ");
            throw new ExitException();
        }
    }

    public DBManager createConnection(InputOutput view) {
        url = getUrl(view);
        boolean isConnect = false;
        DBManager manager = null;

        do {
            view.write("To connect to the database, enter the information in the format 'database_name|user_name|password'\n");
            String[] insertedValues;
            insertedValues = view.read().split("\\|");

            if (insertedValues.length != 3) {
                continue;
            }
            String database = insertedValues[0];
            String userName = insertedValues[1];
            String password = insertedValues[2];

            manager = new DBManager(database, userName, password, url);

            try {
                manager.createConnection();
                view.write(String.format("Connect to the database '%s' successful\n", database));
                isConnect = true;
            } catch (SQLException e) {
                view.error(String.format("Can't connect to the database '%s' \n", database), e);
            }
        } while (!isConnect);
        return manager;
    }

    private String getUrl(InputOutput view) {
        InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties property = new Properties();

        try {
            property.load(input);
        } catch (Exception e) {
            view.error("Can't find property file ", e);
            throw new ExitException();
        }
        return property.getProperty("url");
    }
}