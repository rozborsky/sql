package controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;
import java.util.Map;

public class Delete extends Command {
    private Console wiev;
    private DBManager dbManager;
    private Map workParameters;

    public Delete(DBManager dbManager, Map workParameters, Console wiev){
        this.dbManager = dbManager;
        this.workParameters = workParameters;
        this.wiev = wiev;
    }

    @Override
    public void process() {
        TableParameters table = new TableParameters(dbManager, wiev);
        String command;
        if (table.getColumns().length == 0){
            wiev.write("Table " + workParameters.get("table") + " is empty");
            return;
        }
        else{
            do {
                wiev.write("\nInsert "+ table.getColumns()[0] + " row, that should be removed, " +
                        "'back' to enter another command or 'exit' to close program");
                command = wiev.read();
                try{
                    int id = Integer.parseInt(command);
                    if (dbManager.isExists(id)){
                        try {
                            if (dbManager.delete(table, command)){
                                wiev.write("Row was removed");
                            }
                        }catch (SQLException e){
                            wiev.error("Cant delete row", e);
                        }
                    }
                    else{
                        if (!command.equals("back")){
                            wiev.write("String does not exist");
                        }
                    }
                }catch(Exception e){
                    wiev.write("Insert correct id");
                }
            }while(!command.equals("back"));
        }
    }

    @Override
    protected String format() {
        String command = "delete";
        return command;
    }

    protected String description() {
        return "'delete' - to delete row";
    }
}