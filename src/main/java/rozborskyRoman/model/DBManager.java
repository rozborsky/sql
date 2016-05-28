package rozborskyRoman.model;

import java.sql.*;

/**
 * Created by roman on 18.04.2016.
 */
public class DBManager {
    private Connection connection;
    private String url;
    private String database;
    private String userName;
    private String password;
    private String table = null;

    public DBManager(String database, String userName, String password, String url) {
        this.database = database;
        this.userName = userName;
        this.password = password;
        this.url = url;
    }

    public Connection createConnection() throws SQLException {
        if (connection != null) {
            this.connection = null;
        }
        try {
            connection = DriverManager.getConnection(url + database, userName, password);
            return connection;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean clear() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM public." + table);
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean delete(String command) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM public." + table +
                    " WHERE " + getColumnNames()[0] + " = " + command);
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean insert(String enteredValues, String columns) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO public." + table + " (" + columns + ") " +
                    "VALUES ('" + enteredValues + "')");
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean update(String idColumn, String changedColumns, String[] enteredData) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE public." + table +
                " SET " + changedColumns + " = ? WHERE " + idColumn)) {
            int j;
            for (int i = 0; i < getTableWidth(); i++) {
                if (i == 0) {
                    j = getTableWidth();
                } else {
                    j = i;
                }
                try {
                    int value;
                    value = Integer.parseInt(enteredData[i]);
                    statement.setInt(j, value);
                } catch (Exception e) {
                    statement.setString(j, enteredData[i]);
                }
            }
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean isExists(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT name FROM public." + table +
                " WHERE id=" + id)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public String[] list() throws SQLException {
        String[] tables = new String[0];
        try (Statement statement = connection.createStatement();
             ResultSet number = statement.executeQuery("SELECT COUNT(*) FROM information_schema.tables " +
                     "WHERE table_schema = 'public'")) {
            number.next();
            int numberTables = number.getInt("COUNT");
            if (numberTables != 0) {
                try (ResultSet result = statement.executeQuery("SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema='public' AND table_type='BASE TABLE'")) {
                    tables = new String[numberTables];
                    int count = 0;
                    while (result.next()) {
                        tables[count++] = result.getString("table_name");
                    }
                } catch (SQLException e) {
                    throw e;
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return tables;
    }


    public int getTableWidth() throws SQLException {
        int columns;
        try (Statement statement = connection.createStatement();
             ResultSet countColumns = statement.executeQuery("SELECT COUNT(*) FROM information_schema.columns " +
                     "WHERE table_schema = 'public' AND table_name   = '" + table + "'")) {
            countColumns.next();
            columns = countColumns.getInt(1);
            return columns;
        } catch (SQLException e) {
            throw e;
        }
    }

    public int getTableHight() throws SQLException {
        int rows;
        try (Statement statement = connection.createStatement();
             ResultSet countRows = statement.executeQuery("SELECT COUNT(*) FROM public." + table)) {
            countRows.next();
            rows = countRows.getInt(1);
            return rows;
        } catch (SQLException e) {
            throw e;
        }
    }

    public String[] getColumnNames() throws SQLException {
        int width = getTableWidth();
        String[] columns = new String[width];
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT column_name FROM information_schema.columns " +
                     "WHERE table_schema = 'public' AND table_name = '" + table + "'")) {
            int i = 0;
            while (resultSet.next()) {
                columns[i++] = resultSet.getString(1);
            }
            return columns;
        } catch (SQLException e) {
            throw e;
        }
    }

    public String[] getRows() throws SQLException {
        String[] rows = new String[getTableHight()];
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM public." + table +
                     " ORDER BY " + getColumnNames()[0])) {
            String row;
            int numerOfRow = 0;
            while (result.next()) {
                row = "";
                for (int i = 1; i <= getTableWidth(); i++) {
                    row += result.getString(i);
                    if (i < getTableWidth()) {
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

    public void setTable(String table) {
        this.table = table;
    }

    public String getDatabase() {
        return database;
    }

    public String getTable() {
        return table;
    }
}