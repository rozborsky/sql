package rozborskyRoman.controller;


import rozborskyRoman.model.DBManager;
import rozborskyRoman.view.InputOutput;

import java.sql.SQLException;

public class Update extends Insert {

    public Update(DBManager manager, InputOutput view) {
        super(manager, view);
        super.message = "To update table insert";
    }

    @Override
    protected String format() {
        return "update";
    }

    @Override
    protected void request(String columns, String enteredValues) throws SQLException{

        if (manager.isExists(Integer.parseInt(enteredData[0]))) {
            String columnsInRequest = columns(manager.getColumnNames(), " = ?, ");
            int splitPosition = columnsInRequest.indexOf(',');
            String idColunm = columnsInRequest.substring(0, splitPosition);
            String changedColumns = columnsInRequest.substring(splitPosition + 2, columnsInRequest.length());

            try {
                manager.update(idColunm, changedColumns, enteredData);
                view.write(String.format("Table '%s' was updated", table));
            } catch (SQLException e) {
                view.error("Can't update the table\n", e);
            }
        } else {
            view.write("Can't update the table, row with entered " + manager.getColumnNames()[0] + " is not exist");
        }
    }

    @Override
    protected String description() {
        return "'update' - to update current table";
    }
}