package controller;

import view.*;

import java.util.Map;

/**
 * Created by roman on 22.03.2016.
 */
public class List extends Command {

    private String[] tables;
    private String dbName;
    private Console wiev;

    public List(Map workParameters, String[] tables, Console wiev){
        this.dbName = (String)workParameters.get("DBName");
        this.tables = tables;
        this.wiev = wiev;
    }

    @Override
    protected String format() {
        String command = "list";
        return command;
    }

    @Override
    public void process() {
        showTables();
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
            tableList += "]\n";
            wiev.write("\nAvailable tables:");
            wiev.write(tableList);
        }
    }

    @Override
    protected String description() {
        return "'list' - for a list of all database tables";
    }
}