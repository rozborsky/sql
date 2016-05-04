package romanRozborsky.controller.controller;

import model.DBManager;
import view.Console;

import java.sql.*;

/**
 * Created by roman on 18.03.2016.
 */
public class Insert extends Command {
    protected Console view;
    protected String message = "Insert";
    protected String [] enteredData;
    protected String table;
    protected DBManager manager;
    protected TableParameters tableParameters;

    public Insert(DBManager manager, Console view){
        this.view = view;
        this.manager = manager;
    }

    @Override
    public void process() {
        this.table = manager.getTable();
        tableParameters = new TableParameters(manager, view);

        String showColumns = columns(tableParameters.getColumns(), "|");
        if (tableParameters.getWidth() == 0){
            view.write("Table '" + table + "' has't columns. You cant work with table");
        }
        else {
            insert_data(view, showColumns);
        }
    }

    private void insert_data(Console wiev, String showColumns) {
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

        String columns = columns(tableParameters.getColumns(), ", ");
        String enteredValues = columns(enteredData, "', '");

        request(enteredValues, columns);
        }while((enteredData.length != tableParameters.getWidth()) || enteredData[0].equals("back"));
    }

    protected void request(String enteredValues, String columns) {
        try{
            if(manager.insert(enteredValues, columns)){
                view.write(String.format("\nTable %s was updated", table));
            }
        }catch (SQLException e){
            view.error("Can't insert values into the '" + table + "' ", e);
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
        return "insert";
    }

    @Override
    protected String description() {
        return "'insert' - to write to the current table";
    }
}