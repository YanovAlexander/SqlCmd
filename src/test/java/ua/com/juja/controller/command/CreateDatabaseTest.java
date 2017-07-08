package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.CreateDatabase;
import ua.com.juja.controller.command.util.InputValidation;
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
        InputValidation input = new InputValidation("createDatabase|");
        boolean result = command.canProcess(input);
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCOmmand(){
        //when
        boolean result = command.canProcess(new InputValidation("creatDataB|"));
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess(){
        //when
        command.process(new InputValidation("createDatabase|test"));
        //then
        verify(manager).createDatabase("test");
        verify(view).write("Database with name test created successful !");
    }

    @Test
    public void testProcessWithWrongFormat(){
        //when
        try{
            command.process(new InputValidation("createDatabase|test|test"));
            fail("IllegalArgumentException expected");
        }catch (IllegalArgumentException e){
        //then
            assertEquals("Invalid number of parameters separated by '|', " +
                    "expected 2, but was: 3", e.getMessage());
        }
    }
}

