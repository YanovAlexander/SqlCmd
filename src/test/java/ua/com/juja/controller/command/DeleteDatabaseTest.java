package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.util.InputString;
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
        InputString userInput = new InputString("deleteDatabase|");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand(){
        //when
        InputString userInput = new InputString("dropDatabase|");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess(){
        //when
        when(view.read()).thenReturn("y");
        InputString userInput = new InputString("deleteDatabase|test");
        command.process(userInput);
        //then
        verify(view).write("Do you really want to delete database 'test' ? All data will delete ! If you sure press Y/N ?");
        verify(manager).deleteDatabase("test");
        verify(view).write("Database test delete successful !");
    }

    @Test
    public void testProcessActionCancelled(){
        //when
        when(view.read()).thenReturn("n");
        InputString userInput = new InputString("deleteDatabase|test");
        command.process(userInput);
        //then
        verify(view).write("Do you really want to delete database 'test' ? All data will delete ! If you sure press Y/N ?");
        verify(view).write("Action is Cancelled !");
    }

    @Test
    public void testWrongFormatOfCommand(){
        //when
        try{
            InputString userInput = new InputString("deleteDatabase|test|test");
            command.process(userInput);
            fail("Expected IllegalArgumentException");
        }catch (IllegalArgumentException e){
            assertEquals("Invalid number of parameters separated by '|'," +
                    " expected 2, but was: 3", e.getMessage());
        }
    }
}
