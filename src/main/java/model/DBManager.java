package model;

import controller.TableParameters;

import java.sql.*;
import java.util.Map;

/**
 * Created by roman on 18.04.2016.
 */
public class DBManager {
    private Connection connection;
    private String url;
    private String database;
    private String user;
    private String password;
    Map workParameters;

    public DBManager(Connection connection, Map workParameters){
        this.workParameters = workParameters;
        this.connection = connection;
        this.url = (String)workParameters.get("propertyUrl");
        this.database = (String)workParameters.get("DBName");
        this.user = (String) workParameters.get("userName");
        this.password = (String) workParameters.get("password");
    }

    public Connection connection(Connection connection) throws SQLException{
        if (connection != null){
            this.connection = null;
        }
        try{
            connection = DriverManager.getConnection(url + database, user, password);
            this.connection = connection;
            return connection;
        } catch (SQLException e){
            throw  e;
        }
    }

    public boolean clear() throws SQLException{
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM " + database + "." + workParameters.get("table"));
            return true;
        }catch (SQLException e){
            throw  e;
        }
    }

    public boolean delete(TableParameters tableParameters, String command) throws SQLException {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM " + database + "." + workParameters.get("table") + " WHERE " + tableParameters.getColumns()[0] + " = " + command);
            return true;
        } catch (SQLException e){
            System.out.println("del");
            throw e;
        }
    }

    public boolean insert(String enteredValues, String columns) throws SQLException {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("INSERT INTO " + database + "." + workParameters.get("table") + " (" + columns + ") " +
                    "VALUES ('" + enteredValues + "')");
            return true;
        }catch (SQLException e){
            throw e;
        }
    }

    public boolean update(String idColunm, String changedColumns, TableParameters tableParameters, String [] enteredData) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("UPDATE " + database + "." + workParameters.get("table") +
                " SET " + changedColumns + " = ? WHERE " + idColunm)){
            int j;
            for (int i = 0; i < tableParameters.getWidth(); i++) {
                if (i == 0){
                    j = tableParameters.getWidth();
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
        try(PreparedStatement statement = connection.prepareStatement("SELECT name FROM " + database + "." + workParameters.get("table") +
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
                    "WHERE table_schema = 'public' AND table_name   = '" + workParameters.get("table") + "'")){
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
            ResultSet countRows = statement.executeQuery("SELECT COUNT(*) FROM " + database + "." + workParameters.get("table"))){
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
                    "WHERE table_schema = 'public' AND table_name = '" + workParameters.get("table") + "'")){
            int i = 0;
            while(resultSet.next()){
                columns[i++] = resultSet.getString(1);
            }
            return columns;
        } catch (SQLException e){
            throw e;
        }
    }
    public String [] find(TableParameters tableParameters) throws SQLException {
        String [] rows = new String[tableParameters.getHeigth()];
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM " + database + "." + workParameters.get("table") +
                     " ORDER BY " + tableParameters.getColumns()[0])) {
            String row;
            int numerOfRow = 0;
            while (result.next()) {
                row = "";
                for (int i = 1; i <= tableParameters.getWidth(); i++) {
                    row += result.getString(i);
                    if (i < tableParameters.getWidth()){
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
}
