package main.java.ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.DeleteDatabase;
import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alexandero on 01.07.2017.
 */
public class DeleteDatabaseTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DeleteDatabase(view, manager);
    }

    @Test
    public void testCanProcess(){
        //when
        boolean result = command.canProcess(new InputValidation("deleteDatabase|"));
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand(){
        //when
        boolean result = command.canProcess(new InputValidation("dropDatabase|"));
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess(){
        //when
        when(view.read()).thenReturn("y");
        command.process(new InputValidation("deleteDatabase|test"));
        //then
        verify(view).write("Do you really want to delete database 'test' ? All data will delete ! If you sure press Y/N ?");
        verify(manager).deleteDatabase("test");
        verify(view).write("Database test delete successful !");
    }

    @Test
    public void testProcessActionCancelled(){
        //when
        when(view.read()).thenReturn("n");
        command.process(new InputValidation("deleteDatabase|test"));
        //then
        verify(view).write("Do you really want to delete database 'test' ? All data will delete ! If you sure press Y/N ?");
        verify(view).write("Action is Cancelled !");
    }

    @Test
    public void testWrongFormatOfCommand(){
        //when
        try{
            command.process(new InputValidation("deleteDatabase|test|test"));
            fail("Expected IllegalArgumentException");
        }catch (IllegalArgumentException e){
            assertEquals("Invalid number of parameters separated by '|'," +
                    " expected 2, but was: 3", e.getMessage());
        }
    }
}
