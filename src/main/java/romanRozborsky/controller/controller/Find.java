package romanRozborsky.controller.controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;

/**
 * Created by roman on 27.04.2016.
 */
public class Find extends Command {
    private Console view;
    private DBManager manager;
    private String line = "_____________________________________________________________________";

    public Find(DBManager manager, Console view){
        this.manager = manager;
        this.view = view;
    }

    @Override
    protected String format() {
        return "getRows";
    }

    @Override
    public void process() {
        TableParameters table = new TableParameters(manager, view);
        int numberOfRows = table.getHeight();
        String [] rows;

        if (numberOfRows != 0) {
            view.write("\n\n" + line);
            view.write(manager.getTable());
            view.write(line);
            try {
                rows = manager.getRows();
                for (int i = 0; i < rows.length; i++) {
                    view.write(formatRow(rows[i]));
                    view.write(line);
                }
            }catch (SQLException e){
                view.error("Cant show table '" + manager.getTable() + "' ", e);
            }

        }else{
            view.write("\n\n" + line);
            view.write("table '" + manager.getTable() + "' is empty");
            view.write(line);
        }
    }

    private String formatRow(String value){
        String [] part = value.split("\\|");
        String row = "";
        for (int i = 0; i < part.length; i++) {
            row += String.format("%-21s",part[i]);;
        }
        return row;
    }

    @Override
    protected String description() {
        return "'getRows' - to obtain the contents of the current table";
    }
}