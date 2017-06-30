package ua.com.juja.model;

import java.util.*;

/**
 * Created by Alexandero on 13.06.2017.
 */
public class InMemoryDatabaseManager implements DatabaseManager {

    private Map<String, List<DataSet>> tables = new LinkedHashMap<>();

    @Override
    public Set<String> getTableNames() {
        return tables.keySet();
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        return get(tableName);
    }

    @Override
    public int getSize(String tableName) {
        return get(tableName).size();
    }



    @Override
    public void connect(String databaseName, String userName, String password) {
        //do nothing
    }

    @Override
    public void clear(String tableName) {

       get(tableName).clear();
    }

    private List<DataSet> get(String tableName) {
        if (!tables.containsKey(tableName)){
            tables.put(tableName, new LinkedList<DataSet>());
        }
        return tables.get(tableName);
    }

    @Override
    public void create(String tableName, DataSet input) {
        get(tableName).add(input);
    }


    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (DataSet dataSet : get(tableName)) {
            if (dataSet.get("id").equals(id)) {
                dataSet.updateFrom(newValue);
            }
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) {
        return new LinkedHashSet<>(Arrays.asList("id","username","password"));
    }

    @Override
    public void createDatabase(String databaseName) {

    }

    @Override
    public Set<String> databasesList() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
