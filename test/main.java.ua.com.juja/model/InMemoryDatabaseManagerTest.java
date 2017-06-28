package main.java.ua.com.juja.model;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.InMemoryDatabaseManager;

/**
 * Created by Alexandero on 13.06.2017.
 */
public class InMemoryDatabaseManagerTest extends DatabaseManagerTest {


    @Override
    public DatabaseManager getDatabaseManager() {
        return new InMemoryDatabaseManager();
    }
}
