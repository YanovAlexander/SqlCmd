package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteTableTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DeleteTable(view, manager);
    }

    @Test
    public void testCanProcessWithRightParameters() {
        //when
        InputString userInput = new InputString("deleteTable|");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand() {
        //when
        InputString userInput = new InputString("deleteTables|");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }

    @Test
    public void testProcessWithRightParameters() {
        //when
        when(view.read()).thenReturn("y");
        InputString userInput = new InputString("deleteTable|test");
        command.process(userInput);
        verify(view).write("Do you really want to delete table 'test'? All data will delete! Press Y/N?");
        verify(manager).deleteTable("test");
        //then
        verify(view).write("Table test delete successful!");
    }

    @Test
    public void testProcessPressNo() {
        //when
        when(view.read()).thenReturn("n");
        InputString userInput = new InputString("deleteTable|test");
        command.process(userInput);
        verify(view).write("Do you really want to delete table 'test'? All data will delete! Press Y/N?");
        //then
        verify(view).write("The action is canceled!");
    }

    @Test
    public void testDeleteTableWithWrongParameters() {
        //when
        try {
            InputString userInput = new InputString("deleteTable|test|one");
            command.process(userInput);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
         //then
            assertEquals("Invalid number of parameters separated by '|'," +
                    " expected 2, but was: 3", e.getMessage());
        }
    }
}
