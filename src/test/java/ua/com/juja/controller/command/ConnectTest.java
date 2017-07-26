package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
    public void testCanProcessWithRightCommand() {
        //when
        InputString userInput = new InputString("connect|");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void testCannotProcessWithWrongCommand() {
        //when
        InputString userInput = new InputString("connection|");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }

    @Test
    public void testProcessWithRightParameters() {
        //when
        InputString userInput = new InputString("connect|postgres|postgres|pass");
        command.process(userInput);
        //then
        verify(view).write("Connected successful!");
    }

    @Test
    public void testCantProcessWithWrongUserAndPassword() {
        //when
        try {
            InputString userInput = new InputString("connect|dbName|User");
            command.process(userInput);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Invalid number of parameters separated by '|', expected 4, but was: 3", e.getMessage());
        }
    }
}
