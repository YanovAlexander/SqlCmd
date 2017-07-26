package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.util.HashSet;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class DatabaseListTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DatabaseList(view, manager);
    }

    @Test
    public void testCanProcessWithRightParameters(){
        //when
        InputString userInput = new InputString("databases");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand(){
        //when
        InputString userInput = new InputString("dabaseLList");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }

    @Test
    public void testProcessPrintWithData(){
        //when
        HashSet<String> databases = new HashSet<>(Arrays.asList("test1", "test2"));
        when(manager.databasesList()).thenReturn(databases);
        InputString userInput = new InputString("databaseList");
        command.process(userInput);
        //then
        shouldPrint("[-------------------DATABASES----------------, - test2, - test1, --------------------------------------------]");
    }

    @Test
    public void testProcessPrintEmptyDatabaseList(){
        //when
        HashSet<String> databases = new HashSet<>();
        when(manager.databasesList()).thenReturn(databases);
        InputString userInput = new InputString("databaseList");
        command.process(userInput);
        //then
        shouldPrint("[-------------------DATABASES----------------, --------------------------------------------]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
