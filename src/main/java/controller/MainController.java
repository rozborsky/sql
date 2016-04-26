package controller;

import model.*;
import view.Console;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MainController {
    private Map workParameters = new HashMap<>();
    private Connection connection = null;
    private Console wiev = null;
    private String propertyUrl = null;
    public static Command[] commands;

    static{
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            System.out.println("Can't find jdbc driver ");
            System.exit(0);
        }
    }

    public MainController(){
        this.wiev = new Console();
        wiev.write("SQLCMD manager");

        DBManager dbManager = new DBManager(connection, workParameters);
        connection = connection(dbManager);

        commands = new Command[]{
                new List(dbManager, workParameters, wiev),
                new DBManager.Find(dbManager, workParameters, wiev),
                new Insert(dbManager, workParameters, wiev),
                new Update(dbManager, workParameters, wiev),
                new Delete(dbManager, workParameters, wiev),
                new Clear(dbManager, workParameters, wiev),
                new Help(wiev),
                new Exit(wiev)};

        String command;

        while(true){
            wiev.write("Insert 'list' for show available tables, 'help' for help  or 'exit' to close program");
            command = wiev.read();

            if (!command.equals("help") && !command.equals("list") && !command.equals("exit")) {
                wiev.write("Wrong command\n");
                continue;
            }

            for (Command availableCommand : commands) {
                if(availableCommand.canProcess(command)){
                    availableCommand.process();
                }
            }
            wiev.write("\n");
        }
    }

    public Connection connection(DBManager dbManager){
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
            workParameters.put("DB_name", insertedValues[0]);
            workParameters.put("user_name", insertedValues[1]);
            workParameters.put("password", insertedValues[2]);
            workParameters.put("propertyUrl", propertyUrl);

            try{
                isConnect = dbManager.connection();
                wiev.write(String.format("\nConnect to the '%s' database isExists succesful\n", workParameters.get("DB_name")));
            }catch (SQLException e){
                wiev.error(String.format("\nCan't connect to the database '%s' \n", workParameters.get("DB_name")), e);
                wiev.write("\n");
            }
        }while(isConnect == false);
        return connection;
    }
}