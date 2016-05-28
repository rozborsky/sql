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
    protected void request(String columns, String enteredValues) throws SQLException {
        try {
            if (manager.isExists(Integer.parseInt(enteredData[0]))) {
                String columnsInRequest = columns(manager.getColumnNames(), " = ?, ");
                int splitPosition = columnsInRequest.indexOf(',');
                String idColunm = columnsInRequest.substring(0, splitPosition);
                String changedColumns = columnsInRequest.substring(splitPosition + 2, columnsInRequest.length());

                manager.update(idColunm, changedColumns, enteredData);
                view.write(String.format("Table '%s' was updated", table));
            } else {
                view.write("Can't update the table, row with entered " + manager.getColumnNames()[0] + " is not exist");
            }
        } catch (SQLException e) {
            throw new SQLException((String.format("Cant update table '%s'\n", manager.getTable()) + e.getMessage()));
        }
    }

    @Override
    protected String description() {
        return "'update' - to update current table";
    }
}