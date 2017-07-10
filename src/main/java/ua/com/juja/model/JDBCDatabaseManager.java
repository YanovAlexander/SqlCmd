package ua.com.juja.model;

import java.sql.*;
import java.util.*;

public class JDBCDatabaseManager implements DatabaseManager {

    //TODO: handle SQLExceptions
    //TODO: use PreparedStatement

    private Connection connection;
    private String database;
    private String userName;
    private String password;
    private boolean isConnected;

    @Override
    public Set<String> getTableNames() {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables " +
                     "WHERE table_schema = 'public'"))
        {
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        List<DataSet> result = new LinkedList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName))
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnSize = rsmd.getColumnCount();
            while (rs.next()) {
                DataSet dataSet = new DataSetImpl();
                result.add(dataSet);
                for (int i = 1; i <= columnSize; i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }

            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    public int getSize(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet rsCount = stmt.executeQuery("SELECT COUNT (*) FROM public." + tableName))
        {
            rsCount.next();
            int size = rsCount.getInt(1);
            return size;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void connect(String databaseName, String userName, String password) {
        if (userName != null && password != null){
            this.userName = userName;
            this.password = password;
        }
        if (!"".equals(databaseName)){
            isConnected = true;
        }
        this.database = databaseName;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add JDBC jar to project.", e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + databaseName,
                    userName, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(
                    String.format("Can't get connection for model :%s user:%s", databaseName, userName), e);
        }
    }

    @Override
    public void clear(String tableName) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE from public." + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(String tableName, DataSet input) {
        try (Statement stmt = connection.createStatement()) {

            String tableNames = getNamesFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            //TODO: use PreparedStatement
            stmt.executeUpdate("INSERT INTO " + tableName + "(" + tableNames + ") "
                    + "VALUES (" + values + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        String tableNames = getNamesFormated(newValue, "%s = ?,");
        String sql = "UPDATE PUBLIC." + tableName + " SET " + tableNames + " WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setInt(index, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns " +
                     "WHERE table_schema = 'public'  AND table_name = '" + tableName + "'"))
        {
            while (rs.next()) {
                tables.add(rs.getString("column_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createDatabase(String databaseName) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> databasesList() {
        Set<String> result = new LinkedHashSet<>();
        String sql = "SELECT datname FROM pg_database WHERE datistemplate = false;";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteTable(String tableName) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDatabase(String databaseName) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP DATABASE " + databaseName);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createTable(String tableName) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName);
        } catch (SQLException e) {
            throw new DatabaseManagerException("It is impossible cause : ", e);
        }
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

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    @Override
    public String getDatabaseName() {
        return database;
    }

    private String getNamesFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }
}