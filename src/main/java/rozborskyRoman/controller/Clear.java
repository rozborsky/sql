package rozborskyRoman.controller;


import rozborskyRoman.model.DBManager;
import rozborskyRoman.view.InputOutput;

import java.sql.SQLException;

public class Clear extends Command {
    private InputOutput view;
    private DBManager manager;

    public Clear(DBManager manager, InputOutput view){
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void process() {
        String command;
        do {
            view.write("Are you sure you want to clear the table '" + manager.getTable() + "'? Yes - press 'y',"
                    + " no - press 'n'");
            command = view.read();
            if (command.equals("y")){
                try{
                    if (manager.clear()) {
                        view.write("Table '" + manager.getTable() + "' was cleared");
                        return;
                    }
                }catch (SQLException e){
                    view.error(String.format("Can't clear table '%s'", manager.getTable()), e);
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