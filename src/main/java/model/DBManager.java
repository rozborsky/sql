package model;

import controller.Command;
import controller.TableParameters;
import view.Console;

import java.sql.*;
import java.util.Map;

/**
 * Created by roman on 18.04.2016.
 */
public class DBManager {
    private Connection connection;
    private String url;
    private String database;
    private String table;
    private String user;
    private String password;

    public DBManager(Connection connection, Map workParameters){
        this.connection = connection;
        this.url = (String)workParameters.get("propertyUrl");
        this.database = (String)workParameters.get("DB_name");
        this.table = (String)workParameters.get("table");
        this.user = (String) workParameters.get("user_name");
        this.password = (String) workParameters.get("password");
    }

    public boolean connection() throws SQLException{
        if (connection != null){
            connection = null;
        }
        try{
            this.connection = DriverManager.getConnection(url + database, user, password);
            return true;
        } catch (SQLException e){
            throw  e;
        }
    }

    public boolean clear() throws SQLException{
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM " + database + "." + table);
            return true;
        }catch (SQLException e){
            throw  e;
        }
    }

    public boolean delete(TableParameters table, String command) throws SQLException {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM " + database + "." + table + " WHERE " + table.getColumns() + " = " + command);
            return true;
        } catch (SQLException e){
            throw e;
        }
    }

    public boolean insert(String enteredValues, String columns) throws SQLException {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("INSERT INTO " + database + "." + table + " (" + columns + ") " +
                    "VALUES ('" + enteredValues + "')");
            return true;
        }catch (SQLException e){
            throw e;
        }
    }

    public boolean update(String idColunm, String changedColumns, TableParameters table, String [] enteredData) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("UPDATE " + database + "." + table +
                " SET " + changedColumns + " = ? WHERE " + idColunm)){
            int j;
            for (int i = 0; i < table.getWidth(); i++) {
                if (i == 0){
                    j = table.getWidth();
                }
                else{
                    j = i;
                }
                try{
                    int value;
                    value = Integer.parseInt(enteredData[i]);
                    statement.setInt(j, value);
                } catch (Exception e) {
                    statement.setString(j, enteredData[i]);
                }
            }
            statement.execute();
            return true;
        }catch (SQLException e){
           throw e;
        }
    }

    public boolean isExists(int id){
        try(PreparedStatement statement = connection.prepareStatement("SELECT name FROM " + database + "." + table +
                " WHERE id=" + id)){
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return true;
                }
                else {
                    return false;
                }
            }
        }catch (SQLException e){
            return false;
        }
    }

    public String [] list() throws SQLException {
        String[] tables = new String[0];
        try (Statement statement = connection.createStatement();
             ResultSet number = statement.executeQuery("SELECT COUNT(*) FROM information_schema.tables " +
                     "WHERE table_schema = 'public'")){
            number.next();
            int numberTables = number.getInt("COUNT");
            if (numberTables != 0){
                try(ResultSet result = statement.executeQuery("SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema='public' AND table_type='BASE TABLE'")){
                    tables = new String [numberTables];
                    int count = 0;
                    while(result.next()){
                        tables [count++] = result.getString("table_name");
                    }
                }catch (SQLException e){
                    throw e;
                }
            }
        } catch (SQLException e){
            throw e;
        }
        return tables;
    }


    public int tableWidth() throws SQLException {
        int columns;
        try(Statement statement = connection.createStatement();
            ResultSet countColumns = statement.executeQuery("SELECT COUNT(*) FROM information_schema.columns " +
                    "WHERE table_schema = 'public' AND table_name   = '" + table + "'")){
            countColumns.next();
            columns = countColumns.getInt(1);
            return columns;
        }catch (SQLException e){
            throw e;
        }
    }

    public int tableHight() throws SQLException {
        int rows;
        try(Statement statement = connection.createStatement();
            ResultSet countRows = statement.executeQuery("SELECT COUNT(*) FROM " + database + "." + table)){
            countRows.next();
            rows = countRows.getInt(1);
            return rows;
        }catch (SQLException e){
            throw e;
        }
    }

    public String [] getColumnNames(int width) throws SQLException {
        String [] columns  = new String[width];
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT column_name FROM information_schema.columns " +
                    "WHERE table_schema = 'public' AND table_name = '" + table + "'")){
            int i = 0;
            while(resultSet.next()){
                columns[i++] = resultSet.getString(1);
            }
            return columns;
        } catch (SQLException e){
            throw e;
        }
    }
    public String [] find(TableParameters table) throws SQLException {
        String [] rows = new String[table.getHeigth()];
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM " + database + "." + table +
                     " ORDER BY " + table.getColumns()[0])) {
            String row;
            int numerOfRow = 0;
            while (result.next()) {
                row = "";
                for (int i = 1; i <= table.getWidth(); i++) {
                    row += result.getString(i);
                    if (i < table.getWidth()){
                        row += "|";
                    }
                }
                rows[numerOfRow++] = row;
            }
            return rows;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static class Find extends Command {
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
                wiev.write("table '" + workParameters.get("table") + "' isExists empty");
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
}
