package main.java.ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.CreateTable;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alexandero on 01.07.2017.
 */
public class CreateTableTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new CreateTable(view, manager);
    }

    @Test
    public void testCanProcess(){
        //when
        boolean result = command.canProcess("createTable|");
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongParameters(){
        //when
        boolean result = command.canProcess("createTables|");
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess(){
        //when
        command.process("createTable|users(id SERIAL NOT NULL PRIMARY KEY," +
                "username varchar(225) NOT NULL UNIQUE, password varchar(225))");
        //then
        verify(manager).createTable("users(id SERIAL NOT NULL PRIMARY KEY," +
                "username varchar(225) NOT NULL UNIQUE, password varchar(225))");
        verify(view).write("Table with name users created successful !");
    }

    @Test
    public void testProcessWithEmptyParameters() {
        //when
        try {
            command.process("createTable|users");
            fail("IllegalArgumentException expected");
        }catch (IllegalArgumentException e){
            //then
            assertEquals("There must be an even number of parameters in the format " +
                    "'createTable|tableName(id SERIAL NOT NULL PRIMARY KEY," +
                    "username varchar(225) NOT NULL UNIQUE, password varchar(225))', " +
                    " but indicated createTable|users", e.getMessage());
        }
    }

}
