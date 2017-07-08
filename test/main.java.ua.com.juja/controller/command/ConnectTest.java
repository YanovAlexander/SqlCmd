package main.java.ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.Connect;
import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class ConnectTest {
    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Connect(view, manager);
    }

    @Test
    public void testCanProcess() {
        //when
        boolean result = command.canProcess(new InputValidation("connect|"));
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand() {
        //when
        boolean result = command.canProcess(new InputValidation("connection|"));
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess() {
        //when
        command.process(new InputValidation("connect|postgres|postgres|pass"));
        //then
        verify(view).write("Connected successful");
    }

    @Test
    public void testCantProcessWithWrongUserAndPassword() {
        //when
        try {
            command.process(new InputValidation("connect|dbName|User"));
            fail("Expected IllegalArgumentException");
        }catch (IllegalArgumentException e){
         //then
            assertEquals("Invalid number of parameters separated by '|', expected 4, but was: 3", e.getMessage());
        }
    }
}
