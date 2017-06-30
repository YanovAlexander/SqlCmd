package ua.com.juja.model;

import java.util.List;
import java.util.Set;

/**
 * Created by Alexandero on 13.06.2017.
 */
public interface DatabaseManager {
    Set<String> getTableNames();

    List<DataSet> getTableData(String tableName);

    int getSize(String tableName);

    void connect(String databaseName, String userName, String password);

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    Set<String> getTableColumns(String tableName);

    void createDatabase(String databaseName);

    Set<String > databasesList();

    void deleteTable(String tableName);

    void deleteDatabase(String databaseName);

    void createTable(String tableName);

    boolean isConnected();
}
