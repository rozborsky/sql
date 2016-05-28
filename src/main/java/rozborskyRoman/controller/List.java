package rozborskyRoman.controller;

import rozborskyRoman.model.DBManager;
import rozborskyRoman.view.*;

/**
 * Created by roman on 22.03.2016.
 */
public class List extends Command {
    private String[] tables;
    private DBManager manager;
    private InputOutput view;

    public List(DBManager manager, String[] tables, InputOutput view) {
        this.manager = manager;
        this.tables = tables;
        this.view = view;
    }

    @Override
    protected String format() {
        return "list";
    }

    @Override
    public void process() {
        showTables();
    }

    private void showTables() {
        if (tables.length == 0) {
            view.write("\nDatabase '" + manager.getDatabase() + "' hasn't tables");
        } else {
            String tableList = "[";
            for (int i = 0; i < tables.length; i++) {
                tableList += tables[i];
                if (i != tables.length - 1) {
                    tableList += ", ";
                }
            }
            tableList += "]";
            view.write("\nAvailable tables:");
            view.write(tableList);
        }
    }

    @Override
    protected String description() {
        return "'list' - for a list of all database tables";
    }
}