package controller;

import model.DBManager;
import view.*;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by roman on 22.03.2016.
 */
public class List extends Command {

    private String[] tables;
    private String dbName;
    private Map workParameters;
    private Console wiev;
    private DBManager dbManager;

    public List(DBManager dbManager, Map workParameters, Console wiev){
        this.dbManager = dbManager;
        this.workParameters = workParameters;
        this.wiev = wiev;
        this.dbName = (String)workParameters.get("DB_name");
    }

    @Override
    protected String format() {
        String command = "list";
        return command;
    }

    @Override
    public void process() {
        availableTables();
        showTables();
        String command;
        do {
            boolean isExecute = false;
            wiev.write("\nTo work with table insert tablename or 'back' to back previous menu");
            command =  wiev.read();
            if (checkTable(command)){
                new TableController(workParameters, wiev);
                isExecute = true;
            }
            if (!command.equals("back") && !command.equals("help") && !command.equals("exit")){
                if(isExecute != true) {
                    wiev.write("This table does not exist");
                }
                continue;
            }
            for (Command checkCommand : MainController.commands) {
                if (checkCommand.canProcess(command)){
                    checkCommand.process();
                }
            }
        }while (!"back".equals(command));
    }

    private void availableTables(){
        try {
            this.tables = dbManager.list();
        } catch (SQLException e) {
            wiev.error("",e);
        }
    }

    private void showTables(){
        if (tables.length == 0){
            wiev.write("\nDatabase '" + dbName + "' hasn't tables");
        }
        else{
            String tableList = "[";
            for (int i = 0; i < tables.length; i++) {
                tableList += tables[i];
                if (i != tables.length - 1){
                    tableList += ", ";
                }
            }
            tableList += "]";
            wiev.write("\nAvailable tables:");
            wiev.write(tableList);
        }
    }

    private boolean checkTable(String command){
        for (int i = 0; i < tables.length; i++) {
            if (tables[i].equals(command)){
                workParameters.put("table", command);
                return true;
            }
        }
        return false;
    }


    @Override
    protected String description() {
        return "'list' - for a list of all database tables";
    }
}