package rozborskyRoman.controller;


import rozborskyRoman.model.DBManager;
import rozborskyRoman.view.InputOutput;

import java.sql.SQLException;

/**
 * Created by roman on 25.03.2016.
 */
public class TableParameters {
    private int width;
    private int height;
    private String [] columns = new String[0];
    private DBManager manager;

   public TableParameters(DBManager manager, InputOutput view){
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
            this.width = manager.getTableWidth();
    }

    private void tableHight() throws SQLException {
            this.height = manager.getTableHight();
    }

    private void getColumnNames() throws SQLException {
            this.columns = manager.getColumnNames();
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