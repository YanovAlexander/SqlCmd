package ua.com.juja.model;

import ua.com.juja.model.DataSet;
import ua.com.juja.model.DataSetImpl;
import ua.com.juja.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

/**
 * Created by Alexandero on 02.06.2017.
 */
public abstract class DatabaseManagerTest {

    private DatabaseManager manager;

    public abstract DatabaseManager getDatabaseManager();

    private static final String TABLE_NAME = "users";
    //TODO delete this fucking test

    @Before
    public void setup() {
        manager = getDatabaseManager();
        manager.connect("postgres", "postgres", "pass");
        manager.getTableData("users");
        manager.getTableData("test");
    }

    @Test
    public void selectAllTablesName() {
        Set<String> tableNames = manager.getTableNames();
        assertEquals("[users, test, mytable, mytable22, sss]", tableNames.toString());
    }

    @Test
    public void testClear() {
        //given
        DataSet input = new DataSetImpl();
        input.put("username", "Bob");
        input.put("password", "*****");
        input.put("id", 1);

        manager.create(TABLE_NAME, input);
        List<DataSet> users = manager.getTableData("users");

        //when
        List<DataSet> tests = manager.getTableData(TABLE_NAME);
        manager.clear("users");

        //then
        DataSet user = users.get(0);
        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[1, Bob, *****]", user.getValues().toString());
    }

    @Test
    public void testGetTabbleData() {
        manager.clear("users");

        DataSet input = new DataSetImpl();
        input.put("id", 15);
        input.put("username", "Allen");
        input.put("password", "pass");

        manager.create("users", input);
        List<DataSet> users = manager.getTableData("users");
        assertEquals(1, users.size());

        DataSet user = users.get(0);
        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[15, Allen, pass]", user.getValues().toString());
    }

    @Test
    public void testUpdateTableData() {
        manager.clear("users");

        DataSet input = new DataSetImpl();
        input.put("id", 100);
        input.put("username", "Allen");
        input.put("password", "pass");
        manager.create("users", input);

        DataSet newValue = new DataSetImpl();
        newValue.put("password", "passWORD");
        manager.update("users", 100, newValue);

        List<DataSet> users = manager.getTableData("users");
        assertEquals(1, users.size());

        DataSet user = users.get(0);
        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[100, Allen, passWORD]", user.getValues().toString());
    }

    @Test
    public void testGetColumnNames() {
        //given
        manager.clear("users");

        //when
        Set<String> columnNames = manager.getTableColumns("users");

        //then
        assertEquals("[id, username, password]", columnNames.toString());
    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }
}