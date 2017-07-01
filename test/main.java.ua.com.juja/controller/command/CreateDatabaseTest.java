package main.java.ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.CreateDatabase;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alexandero on 01.07.2017.
 */
public class CreateDatabaseTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new CreateDatabase(view, manager);
    }

    @Test
    public void testCanProcess(){
        //when
        boolean result = command.canProcess("createDatabase|");
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCOmmand(){
        //when
        boolean result = command.canProcess("creatDataB|");
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess(){
        //when
        command.process("createDatabase|test");
        //then
        verify(manager).createDatabase("test");
        verify(view).write("Database with name test created successful !");
    }

    @Test
    public void testProcessWithWrongFormat(){
        //when
        try{
            command.process("createDatabase|test|test");
            fail("IllegalArgumentException expected");
        }catch (IllegalArgumentException e){
        //then
            assertEquals("There must be an even number of parameters in the" +
                    " format 'createDatabasename|databaseName', " +
                    " but indicated createDatabase|test|test", e.getMessage());
        }
    }
}

