package romanRozborsky.controller.controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;

public class Delete extends Command {
    private Console view;
    private DBManager dbManager;

    public Delete(DBManager dbManager, Console view){
        this.dbManager = dbManager;
        this.view = view;
    }

    @Override
    public void process() {
        TableParameters table = new TableParameters(dbManager, view);
        String command;
        if (table.getColumns().length == 0){
            view.write("Table '" + dbManager.getTable() + "' is empty");
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