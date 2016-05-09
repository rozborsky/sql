package romanRozborsky.controller.controller;

import model.DBManager;
import view.Console;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by roman on 09.05.2016.
 */
public class PrepareTable {
    private DBManager manager;
    private Console view;

    public PrepareTable(){
        createDBManager();
        createConsole();
    }

    public void clearTable() {
        Clear clear = new Clear(manager, view);
        String confirmation = "y";
        InputStream iStream = new ByteArrayInputStream(confirmation.getBytes());
        System.setIn(iStream);
        clear.process();
    }

    public void insertValues(String insertedValue) {
        Insert insert = new Insert(manager, view);
        InputStream inputStream = new ByteArrayInputStream(insertedValue.getBytes());
        System.setIn(inputStream);
        insert.process();
    }

    private void createDBManager(){
        manager = new DBManager("public", "postgres", "mainuser", "jdbc:postgresql://localhost:5432/");
        try {
            manager.createConnection();
        }catch (SQLException e){
            //do noting
        }
    }

    private void createConsole(){
        view = new Console();
    }

    public DBManager getManager(){
        return manager;
    }

    public Console getView(){
        return view;
    }
}
