package ua.com.juja.model;

import java.sql.*;
import java.util.*;

public class PostgresManager implements DatabaseManager {

    private Connection connection;
    private String database;
    private String userName;
    private String password;
    private boolean isConnected;

    private static final String HOST = "localhost";
    private static final String PORT = "5432";

    @Override
    public Set<String> getTableNames() {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT table_name FROM information_schema.tables " +
                     "WHERE table_schema = 'public'"))
        {
            while (resultSet.next()) {
                tables.add(resultSet.getString("table_name"));
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        List<DataSet> result = new LinkedList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM public." + tableName))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnSize = metaData.getColumnCount();
            while (resultSet.next()) {
                DataSet dataSet = new DataSetImpl();
                result.add(dataSet);
                for (int i = 1; i <= columnSize; i++) {
                    dataSet.put(metaData.getColumnName(i), resultSet.getObject(i));
                }

            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void connect(String databaseName, String userName, String password) {
        if (userName != null && password != null) {
            this.userName = userName;
            this.password = password;
        }
        if (!"".equals(databaseName)) {
            isConnected = true;
        }
        this.database = databaseName;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new DatabaseManagerException("Please add JDBC jar to project.", e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
            String url = String.format("jdbc:postgresql://%s:%s/", HOST, PORT);
            connection = DriverManager.getConnection(url + databaseName,
                    userName, password);
        } catch (SQLException e) {
            connection = null;
            throw new DatabaseManagerException(
                    String.format("Can't get connection for model :%s user:%s", databaseName, userName), e);
        }
    }

    @Override
    public void clear(String tableName) {
        executeUpdateQuery("DELETE from public." + tableName);
    }

    private void executeUpdateQuery(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void create(String tableName, DataSet input) {
        try (Statement statement = connection.createStatement()) {

            String tableNames = getNamesFormatted(input, "%s,");
            String values = getValuesFormatted(input, "'%s',");

            statement.executeUpdate("INSERT INTO " + tableName + "(" + tableNames + ") "
                    + "VALUES (" + values + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        String tableNames = getNamesFormatted(newValue, "%s = ?,");
        String sql = "UPDATE PUBLIC." + tableName + " SET " + tableNames + " WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            int index = 1;
            for (Object value : newValue.getValues()) {
                preparedStatement.setObject(index, value);
                index++;
            }
            preparedStatement.setInt(index, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM information_schema.columns " +
                     "WHERE table_schema = 'public'  AND table_name = '" + tableName + "'"))
        {
            while (resultSet.next()) {
                tables.add(resultSet.getString("column_name"));
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createDatabase(String databaseName) {
       executeUpdateQuery("CREATE DATABASE " + databaseName);
    }

    @Override
    public Set<String> databasesList() {
        Set<String> result = new LinkedHashSet<>();
        String sql = "SELECT datname FROM pg_database WHERE datistemplate = false;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql))
        {
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteTable(String tableName) {
     executeUpdateQuery("DROP TABLE IF EXISTS " + tableName);
    }

    @Override
    public void deleteDatabase(String databaseName) {
      executeUpdateQuery("DROP DATABASE " + databaseName);
    }

    @Override
    public void createTable(String tableName) {
       executeUpdateQuery("CREATE TABLE IF NOT EXISTS " + tableName);
    }

    @Override
    public void disconnectFromDB() {
        isConnected = false;
        connect("", userName, password);
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    private String getValuesFormatted(DataSet input, String format) {
        StringBuilder values = new StringBuilder();
        for (Object value : input.getValues()) {
            values.append(String.format(format, value));
        }
        values.deleteCharAt(values.length() - 1);
        return values.toString();
    }

    @Override
    public String getDatabaseName() {
        return database;
    }

    private String getNamesFormatted(DataSet newValue, String format) {
        StringBuilder names = new StringBuilder();
        for (String name : newValue.getNames()) {
            names.append(String.format(format, name));
        }
        names.deleteCharAt(names.length() - 1);
        return names.toString();
    }
}