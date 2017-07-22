package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by Alexandero on 17.06.2017.
 */
public class ClearTest {

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(view, manager);
    }

    @Test
    public void testPrintTableData() {
        //given
        LinkedHashSet<String> table = new LinkedHashSet<>(Arrays.asList("id", "username", "password"));
        when(manager.getTableColumns("users")).thenReturn(table);
        //when
        InputString userInput = new InputString("clear|users");
        command.process(userInput);
        //then
        verify(manager).clear("users");
        verify(view).write("Table users was successfully cleaned.");
    }


    @Test
    public void clearCanProcessTest() {
        //when
        InputString userInput = new InputString("clear|users");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void clearCantProcessWithoutParametersTest() {
        //when
        InputString userInput = new InputString("clear");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void clearCantProcessWithQWETest() {
        //when
        InputString userInput = new InputString("qwe|users");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }

    @Test
    public void clearCantProcessWithErrorParametersLessThenTwo() {
        //when
        try {
            InputString userInput = new InputString("clear");
            command.process(userInput);
            fail();
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Invalid number of parameters separated by '|', expected 2, but was: 1", e.getMessage());
        }
    }


    @Test
    public void clearCantProcessWithErrorParametersMoreThenTwo() {
        //when
        try {
            InputString userInput = new InputString("clear|users|tables");
            command.process(userInput);
            fail();
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Invalid number of parameters separated by '|', expected 2, but was: 3", e.getMessage());
        }
    }
}
