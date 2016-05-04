package romanRozborsky.controller.controller;

import model.DBManager;
import view.Console;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by roman on 27.04.2016.
 */
public class Connector {

    private String url = null;

    public DBManager create (Console view){

        url = getUrl(view);

        boolean isConnect = false;
        DBManager dbManager = null;
        do{
            view.write("\nFor the database connection, enter the information in the format 'database_name|user_name|password'");
            String [] insertedValues;
            insertedValues = view.read().split("\\|");
            if (insertedValues.length != 3){
                continue;
            }
            String database = insertedValues[0];
            String userName = insertedValues[1];
            String password = insertedValues[2];

            dbManager = new DBManager(database, userName, password, url);
            try{
                dbManager.connection();
                view.write(String.format("\nConnect to the database '%s' succesful\n", database));
                isConnect = true;
            }catch (SQLException e){
                view.error(String.format("\nCan't connect to the database '%s' \n", database), e);
                view.write("\n");
            }
        }while(!isConnect);
        return dbManager;
    }

    private String getUrl(Console view) {
        InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties property = new Properties();
        try{
            property.load(input);
        }catch (Exception e){
            view.error("Can't find property file ", e);
            System.exit(0);
        }
        return property.getProperty("url");
    }
}
