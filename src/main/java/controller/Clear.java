package controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;
import java.util.Map;

public class Clear extends Command {
    private Console view;
    private DBManager dbManager;
    Map workParameters;

    public Clear(DBManager dbManager, Map workParameters, Console view){
        this.dbManager = dbManager;
        this.workParameters = workParameters;
        this.view = view;
    }

    @Override
    public void process() {
        String command;
        do {
            view.write("Are you sure you want to clear the table '" + workParameters.get("table") + "'? Yes - press 'y',"
                    + " no - press 'n'");
            command = view.read();
            if (command.equals("y")){
                try{
                    if (dbManager.clear()) {
                        view.write("Table '" + workParameters.get("table") + "' was cleared");
                    }
                }catch (SQLException e){
                    view.error(String.format("Can't clear table %s", workParameters.get("table")), e);
                }
            }
        }while (!"n".equals(command) && !"back".equals(command));
    }

    @Override
    protected String format() {
        return "clear";
    }

    @Override
    protected String description() {
        return "'clear' - to clean up the current table";
    }
}