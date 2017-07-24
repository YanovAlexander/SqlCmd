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
        InputString userInput = new InputString("createTable");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongParameters(){
        //when
        InputString userInput = new InputString("createTable11");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }
}
