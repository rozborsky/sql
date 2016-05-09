package romanRozborsky.controller.controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;

/**
 * Created by roman on 25.03.2016.
 */
public class TableParameters {
    private int width;
    private int height;
    private String [] columns = new String[0];
    private DBManager manager;

   public TableParameters(DBManager manager, Console view){
        this.manager = manager;
       try{
            tableHight();
            tableWidth();
            getColumnNames();
       }catch (SQLException e){
           view.error("Can't work with table", e);
       }
    }

    private void tableWidth() throws SQLException {
            this.width = manager.tableWidth();
    }

    private void tableHight() throws SQLException {
            this.height = manager.tableHight();
    }

    private void getColumnNames() throws SQLException {
            this.columns = manager.getColumnNames(width);
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public String [] getColumns() {
        return columns;
    }
}