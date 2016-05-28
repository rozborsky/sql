package rozborskyRoman.controller;

import rozborskyRoman.model.DBManager;
import rozborskyRoman.view.InputOutput;

import java.sql.SQLException;

public class Delete extends Command {
    private InputOutput view;
    private DBManager manager;

    public Delete(DBManager manager, InputOutput view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void process() throws SQLException {
        String command;
        do {
            try {
                view.write("\nInsert " + manager.getColumnNames()[0] + " row, that should be removed, " +
                        "'back' to enter another command or 'exit' to close program");
                command = view.read();
                if (command.equals("back")) {
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(command);
                } catch (Exception e) {
                    view.write("Insert correct id");
                    continue;
                }
                if (manager.isExists(id)) {
                    if (manager.delete(command)) {
                        view.write("Row was removed");
                        return;
                    }
                } else {
                    if (!command.equals("back")) {
                        view.write("String does not exist");
                    }
                }
            } catch (Exception e) {
                String message = (String.format("Cant delete row in table '%s'\n", manager.getTable()));
                throw new SQLException(message + e.getMessage());
            }
        } while (true);
    }

    @Override
    protected String format() {
        return "delete";
    }

    protected String description() {
        return "'delete' - to delete row";
    }
}