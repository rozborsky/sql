package rozborskyRoman.controller;


import rozborskyRoman.model.DBManager;
import rozborskyRoman.view.InputOutput;

import java.sql.SQLException;

public class Delete extends Command {
    private InputOutput view;
    private DBManager manager;

    public Delete(DBManager manager, InputOutput view){
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void process() {
        TableParameters table = new TableParameters(manager, view);
        String command;
        do {
            view.write("\nInsert "+ table.getColumns()[0] + " row, that should be removed, " +
                    "'back' to enter another command or 'exit' to close program");
            command = view.read();
            try{
                int id = Integer.parseInt(command);
                if (manager.isExists(id)){
                    try {
                        if (manager.delete(command)){
                            view.write("Row was removed");
                            return;
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

    @Override
    protected String format() {
        return "delete";
    }

    protected String description() {
        return "'delete' - to delete row";
    }
}