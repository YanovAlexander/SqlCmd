package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.util.InputString;
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
        InputString userInput = new InputString("createDatabase|");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand(){
        //when
        InputString userInput = new InputString("creatDataB|");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess(){
        //when
        InputString userInput = new InputString("createDatabase|test");
        this.command.process(userInput);
        //then
        verify(manager).createDatabase("test");
        verify(view).write("Database with name test created successful !");
    }

    @Test
    public void testProcessWithWrongFormat(){
        //when
        try{
            InputString userInput = new InputString("createDatabase|test|test");
            command.process(userInput);
            fail("IllegalArgumentException expected");
        }catch (IllegalArgumentException e){
        //then
            assertEquals("Invalid number of parameters separated by '|', " +
                    "expected 2, but was: 3", e.getMessage());
        }
    }
}

