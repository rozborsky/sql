package controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;
import java.util.Map;

public class Delete extends Command {
    private Console view;
    private DBManager dbManager;
    private Map workParameters;

    public Delete(DBManager dbManager, Map workParameters, Console view){
        this.dbManager = dbManager;
        this.workParameters = workParameters;
        this.view = view;
    }

    @Override
    public void process() {
        TableParameters table = new TableParameters(dbManager, view);
        String command;
        if (table.getColumns().length == 0){
            view.write("Table " + workParameters.get("table") + " is empty");
            return;
        }
        else{
            do {
                view.write("\nInsert "+ table.getColumns()[0] + " row, that should be removed, " +
                        "'back' to enter another command or 'exit' to close program");
                command = view.read();
                try{
                    int id = Integer.parseInt(command);
                    if (dbManager.isExists(id)){
                        try {
                            if (dbManager.delete(table, command)){
                                view.write("Row was removed");
                            }
                        }catch (SQLException e){
                            view.error("Cant delete row", e);
                        }
                    }
                    else{
                        if (!command.equals("back")){
                            view.write("String does not exist");
                        }
                    }
                }catch(Exception e){
                    view.write("Insert correct id");
                }
            }while(!command.equals("back"));
        }
    }

    @Override
    protected String format() {
        return "delete";
    }

    protected String description() {
        return "'delete' - to delete row";
    }
}