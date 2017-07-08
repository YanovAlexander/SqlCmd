package main.java.ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.DatabaseList;
import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.util.HashSet;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Alexandero on 01.07.2017.
 */
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
    public void testCanProcess(){
        //when
        boolean result = command.canProcess(new InputValidation("databaseList"));
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand(){
        //when
        boolean result = command.canProcess(new InputValidation("dabaseLList"));
        //then
        assertFalse(result);
    }

    @Test
    public void testProcessPtint(){
        //when
        when(manager.databasesList()).thenReturn(new HashSet<>(Arrays.asList("test1", "test2")));
        command.process(new InputValidation("databaseList"));
        //then
        shouldPrint("[-------------------DATABASES----------------, - test2, - test1, --------------------------------------------]");
    }

    @Test
    public void testProcessPtintEmptyDatabaseList(){
        //when
        when(manager.databasesList()).thenReturn(new HashSet<>());
        command.process(new InputValidation("databaseList"));
        //then
        shouldPrint("[-------------------DATABASES----------------, --------------------------------------------]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
