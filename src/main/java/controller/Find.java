package controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by roman on 27.04.2016.
 */
public class Find extends Command {
    private Console wiev;
    private Map workParameters;
    private DBManager dbManager;
    private String line = "_____________________________________________________________________";

    public Find(DBManager dbManager,  Map workParameters, Console wiev){
        this.dbManager = dbManager;
        this.workParameters = workParameters;
        this.wiev = wiev;
    }

    @Override
    protected String format() {
        String command = "find";
        return command;
    }

    @Override
    public void process() {
        TableParameters table = new TableParameters(dbManager, wiev);
        int numberOfRows = table.getHeigth();
        String [] rows;

        if (numberOfRows != 0) {
            wiev.write("\n\n" + line);
            wiev.write((String)  workParameters.get("table"));
            wiev.write(line);
            try {
                rows = dbManager.find(table);
                for (int i = 0; i < rows.length; i++) {
                    wiev.write(formatRow(rows[i]));
                    wiev.write(line);
                }
            }catch (SQLException e){
                wiev.error("Cant show table '" + workParameters.get("table") + "' ", e);
            }

        }else{
            wiev.write("\n\n" + line);
            wiev.write("table '" + workParameters.get("table") + "' is empty");
            wiev.write(line);
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
        return "'find' - to obtain the contents of the current table";
    }
}