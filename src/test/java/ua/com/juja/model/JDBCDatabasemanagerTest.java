package ua.com.juja.model;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.JDBCDatabaseManager;

/**
 * Created by Alexandero on 13.06.2017.
 */
public class JDBCDatabasemanagerTest extends DatabaseManagerTest {
    @Override
    public DatabaseManager getDatabaseManager() {
        return new JDBCDatabaseManager();
    }
}
