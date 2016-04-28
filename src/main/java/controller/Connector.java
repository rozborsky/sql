package controller;

import model.DBManager;
import view.Console;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by roman on 27.04.2016.
 */
public class Connector {

    private String propertyUrl = null;

    public Connection create (Connection connection, Map workParameters, Console wiev){
        Properties property = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
        boolean isConnect = false;

        try{
            property.load(input);
        }catch (Exception e){
            wiev.error("Can't find property file ", e);
            System.exit(0);
        }

        this.propertyUrl = property.getProperty("url");

        do{
            wiev.write("\nFor the database connection, enter the information in the format 'database_name|user_name|password'");
            String [] insertedValues;
            insertedValues = wiev.read().split("\\|");
            if (insertedValues.length != 3){
                continue;
            }
            workParameters.put("DBName", insertedValues[0]);
            workParameters.put("userName", insertedValues[1]);
            workParameters.put("password", insertedValues[2]);
            workParameters.put("propertyUrl", propertyUrl);

            DBManager dbManager = new DBManager(connection, workParameters);
            try{
                connection = dbManager.connection(connection);
                wiev.write(String.format("\nConnect to the database '%s' succesful\n", workParameters.get("DBName")));
                isConnect = true;
            }catch (SQLException e){
                wiev.error(String.format("\nCan't connect to the database '%s' \n", workParameters.get("DBName")), e);
                wiev.write("\n");
            }
        }while(isConnect == false);
        return connection;
    }
}
