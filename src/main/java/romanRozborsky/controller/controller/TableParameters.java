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
    private Console view;


   public TableParameters(DBManager manager, Console view){
        this.manager = manager;
        this.view = view;
        tableHight();
        tableWidth();
        getColumnNames();
    }

    private void tableWidth() {
        try{
            this.width = manager.tableWidth();
        }catch (SQLException e){
            view.error("", e);
        }
    }

    private void tableHight() {
        try{
            this.height = manager.tableHight();
        }catch (SQLException e){
            view.error("", e);
        }
    }

    private void getColumnNames(){
        try{
            this.columns = manager.getColumnNames(width);
        }catch (SQLException e){
            view.error("", e);
        }
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