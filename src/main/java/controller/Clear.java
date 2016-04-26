package controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;
import java.util.Map;

public class Clear extends Command {
    private Console wiev;
    private DBManager dbManager;
    private String table;

    public Clear(DBManager dbManager, Map workParameters, Console wiev){
        this.dbManager = dbManager;
        this.table = (String)workParameters.get("table");
        this.wiev = wiev;
    }

    @Override
    public void process() {
        String command;
        do {
            wiev.write("Are you sure you want to clear the table '" + table + "'? Yes - press 'y',"
                    + " no - press 'n'");
            command = wiev.read();
            if (command.equals("y")){
                try{
                    if (dbManager.clear()) {
                        wiev.write("Table '" + table + "' was cleared");
                    }
                }catch (SQLException e){
                    wiev.error(String.format("Can't clear table %s", table), e);
                }
            }
        }while (!"n".equals(command) && !"back".equals(command));
    }

    @Override
    protected String format() {
        String command = "clear";
        return command;
    }

    @Override
    protected String description() {
        return "'clear' - to clean up the current table";
    }
}