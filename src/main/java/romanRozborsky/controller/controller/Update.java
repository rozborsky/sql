package romanRozborsky.controller.controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;

public class Update extends Insert {

    public Update(DBManager dbManager, Console view) {
        super(dbManager, view);
        super.message = "To update table insert";
    }

    @Override
    protected String format() {
        return "update";
    }

    @Override
     public void request(String columns, String enteredValues) {

        if (manager.isExists(Integer.parseInt(enteredData[0]))){
            String columnsInRequest = columns(tableParameters.getColumns(), " = ?, ");
            int splitPosition = columnsInRequest.indexOf(',');
            String idColunm = columnsInRequest.substring(0, splitPosition);
            String changedColumns = columnsInRequest.substring(splitPosition + 2, columnsInRequest.length());

            try{
                manager.update(idColunm, changedColumns, enteredData);
                view.write(String.format("Table %s was updated", table));
            }catch (SQLException e){
                view.error("Can't update the table\n", e);
            }
        }
        else {
            view.write("Can't update the table, row with entered " + tableParameters.getColumns()[0] + " not exist");
        }
    }

    @Override
    protected String description() {
        return "'update' - to update current table";
    }
}