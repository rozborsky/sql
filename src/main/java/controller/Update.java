package controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;
import java.util.Map;

public class Update extends Insert {

    public Update(DBManager dbManager, Map workParameters, Console wiev) {
        super(dbManager, workParameters, wiev);
        super.message = "To update table insert";
    }

    @Override
    protected String format() {
        String command = "update";
        return command;
    }

    @Override
     public void request(String columns, String enteredValues) {

        String columnsInRequest = super.columns(super.tableParameters.getColumns(), " = ?, ");
        int splitPosition = columnsInRequest.indexOf(',');
        String idColunm = columnsInRequest.substring(0, splitPosition);
        String changedColumns = columnsInRequest.substring(splitPosition + 2, columnsInRequest.length());

        if (dbManager.isExists(Integer.parseInt(enteredData[0]))){
            try{
                dbManager.update(idColunm, changedColumns, super.tableParameters, super.enteredData);
                wiev.write(String.format("Table %s was updated", workParameters.get("table")));
            }catch (SQLException e){
                wiev.error("Can't update the table\n", e);
            }
        }
        else {
            wiev.write("Can't update the table, row with entered " + super.tableParameters.getColumns()[0] + " not exist");
        }
    }

    @Override
    protected String description() {
        return "'update' - to update current tableParameters";
    }
}