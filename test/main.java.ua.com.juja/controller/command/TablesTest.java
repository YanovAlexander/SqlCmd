package main.java.ua.com.juja.controller.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.Tables;
import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.util.HashSet;
import java.util.Arrays;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class TablesTest {
    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Tables(view, manager);
    }

    @Test
    public void testCanProcessWithoutParameter() {
        //when
        boolean result = command.canProcess(new InputValidation("tables"));
        //then
        assertTrue(result);
    }

    @Test
    public void testCanProcessWithParameters() {
        //when
        boolean result = command.canProcess(new InputValidation("tables|users"));
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand() {
        //when
        boolean result = command.canProcess(new InputValidation("tables1"));
        //then
        assertFalse(result);
    }

    @Test
    public void testPrintTables() {
        //when
        when(manager.getTableNames()).thenReturn(new HashSet<>(Arrays.asList("users", "test")));
        command.process(new InputValidation("tables"));
        //then
        shouldPrint("[-------------------TABLES-------------------, - test, - users, --------------------------------------------]");
    }

    @Test
    public void testPrintEmptyTables() {
        //when
        when(manager.getTableNames()).thenReturn(new HashSet<>());
        command.process(new InputValidation("tables"));
        //then
        shouldPrint("[-------------------TABLES-------------------, --------------------------------------------]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        Assert.assertEquals(expected, captor.getAllValues().toString());
    }
}
