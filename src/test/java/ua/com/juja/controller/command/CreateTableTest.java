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
        InputString userInput = new InputString("createTable|");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongParameters(){
        //when
        InputString userInput = new InputString("createTables|");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess(){
        //when
        InputString userInput = new InputString("createTable|users(id SERIAL NOT NULL PRIMARY KEY," +
                "username varchar(225) NOT NULL UNIQUE, password varchar(225))");
        command.process(userInput);
        //then
        verify(manager).createTable("users(id SERIAL NOT NULL PRIMARY KEY," +
                "username varchar(225) NOT NULL UNIQUE, password varchar(225))");
        verify(view).write("Table with name users created successful !");
    }

    @Test
    public void testProcessWithEmptyParameters() {
        //when
        try {
            InputString userInput = new InputString("createTable|users|ss");
            command.process(userInput);
            fail("IllegalArgumentException expected");
        }catch (IllegalArgumentException e){
            //then
            assertEquals("Invalid number of parameters separated by '|', expected 2, but was: 3", e.getMessage());
        }
    }

}
