package controller;

import model.DBManager;
import view.Console;

import java.sql.SQLException;

/**
 * Created by roman on 25.03.2016.
 */
public class TableParameters {
    private int width;
    private int heigth;
    private String [] columns = new String[0];
    private DBManager bd;
    private Console wiev;


   public TableParameters(DBManager bd, Console wiev){
        this.bd = bd;
        this.wiev = wiev;
        tableHight();
        tableWidth();
        getColumnNames();
    }

    private void tableWidth() {
        try{
            this.width = bd.tableWidth();
        }catch (SQLException e){
            wiev.error("", e);
        }
    }

    private void tableHight() {
        try{
            this.heigth = bd.tableHight();
        }catch (SQLException e){
            wiev.error("", e);
        }
    }

    private void getColumnNames(){
        try{
            this.columns = bd.getColumnNames(width);
        }catch (SQLException e){
            wiev.error("", e);
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeigth(){
        return heigth;
    }
    public String [] getColumns() {
        return columns;
    }
}