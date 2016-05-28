package rozborskyRoman.controller;

import rozborskyRoman.model.DBManager;
import rozborskyRoman.view.InputOutput;

import java.sql.SQLException;

/**
 * Created by roman on 27.04.2016.
 */
public class Find extends Command {
    private InputOutput view;
    private DBManager manager;
    private String line = "_____________________________________________________________________";

    public Find(DBManager manager, InputOutput view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    protected String format() {
        return "find";
    }

    @Override
    public void process() throws SQLException {
        int numberOfRows;
        try {
            numberOfRows = manager.getTableHight();

            if (numberOfRows != 0) {
                view.write("\n\n" + line);
                view.write(manager.getTable());
                view.write(line);

                String[] rows = manager.getRows();
                for (int i = 0; i < rows.length; i++) {
                    view.write(formatRow(rows[i]));
                    view.write(line);
                }
            } else {
                view.write("\n\n" + line);
                view.write("table '" + manager.getTable() + "' is empty");
                view.write(line);
            }
        } catch (SQLException e) {
            throw new SQLException((String.format("Cant show table '%s'\n", manager.getTable()) + e.getMessage()));
        }
    }

    private String formatRow(String value) {
        String[] part = value.split("\\|");
        String row = "";
        for (int i = 0; i < part.length; i++) {
            row += String.format("%-21s", part[i]);
            ;
        }
        return row;
    }

    @Override
    protected String description() {
        return "'find' - to obtain the contents of the current table";
    }
}