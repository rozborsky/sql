package controller;

import model.DBManager;
import view.Console;

import java.sql.*;
import java.util.Map;

/**
 * Created by roman on 18.03.2016.
 */
public class Insert extends Command {
    protected Console wiev;
    protected String message = "Insert";
    protected String [] enteredData;
    protected String dbName;
    protected String tableName;
    protected DBManager dbManager;
    protected TableParameters table;
    protected Map workParameters;

    public Insert(DBManager dbManager, Map workParameters, Console wiev){
        this.workParameters = workParameters;
        this.wiev = wiev;
        this.dbManager = dbManager;
    }

    @Override
    public void process() {
        this.dbName = (String) workParameters.get("DB_name");
        this.tableName = (String)  workParameters.get("table");
        table = new TableParameters(dbManager, wiev );

        String showColumns = columns(table.getColumns(), "|");

        if (table.getWidth() == 0){
            wiev.write("Table '" + tableName + "' has't columns. You cant work with table");
        }
        else {
            insert_data(wiev, showColumns);
        }
    }

    private void insert_data( Console wiev, String showColumns) {
        do{
            wiev.write("\n" + message + " values in format " + showColumns + ", 'back' to enter another command " +
            "or 'exit' to close program");
            enteredData = wiev.read().split("\\|");
            if (enteredData[0].equals("exit")){
                Command exit = new Exit(wiev);
                exit.process();
            }
            if (enteredData[0].equals("back")){
                return;
            }
            try{
                Integer.parseInt(enteredData[0]);
            }catch (Exception e){
                wiev.write("Insert correct id");
            }

        String columns = columns(table.getColumns(), ", ");
        String enteredValues = columns(enteredData, "', '");

        request(enteredValues, columns);
        }while((enteredData.length != table.getWidth()) || enteredData[0].equals("back"));
    }

    protected void request(String enteredValues, String columns) {
        try{
            if(dbManager.insert(enteredValues, columns)){
                wiev.write(String.format("\nTable %s was updated", tableName));
            }
        }catch (SQLException e){
            wiev.error("Can't insert values into the '" + tableName + "' ", e);
        }

    }

    protected String columns(String [] array, String format) {
        String columns = "";
        for (int i = 0; i < array.length; i++){
            columns += array[i];
            if (i + 1 != array.length){
                columns += format;
            }
        }
        return columns;
    }

    @Override
    protected String format() {
        String command = "insert";
        return command;
    }

    @Override
    protected String description() {
        return "'insert' - to write to the current table";
    }
}