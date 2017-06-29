package main.java.ua.com.juja.controller.command;

import ua.com.juja.controller.command.Clear;
import ua.com.juja.controller.command.Command;
import ua.com.juja.model.DataSet;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
public class TestClear {

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
        when(manager.getTableColumns("users"))
                .thenReturn((new LinkedHashSet<String>(Arrays.asList("id", "username", "password"))));

        //when
        command.process("clear|users");

        //then

        verify(manager).clear("users");
        verify(view).write("Table users was successfully cleaned.");
    }


    @Test
    public void clearCanProcessTest() {

        //when
        boolean result = command.canProcess("clear|users");

        //then
        assertTrue(result);
    }

    @Test
    public void clearCantProcessWithoutParametersTest() {

        //when
        boolean result = command.canProcess("clear");

        //then
        assertFalse(result);
    }

    @Test
    public void clearCantProcessWithQWETest() {

        //when
        boolean result = command.canProcess("qwe|users");

        //then
        assertFalse(result);
    }

    @Test
    public void clearCantProcessWithErrorParametersLessThenTwo() {

        //when
        try {
            command.process("clear");
            fail();
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Format of the command 'clear|tableName', but you type : clear", e.getMessage());
        }
    }


    @Test
    public void clearCantProcessWithErrorParametersMoreThenTwo() {
        //when
        try {
            command.process("clear|users|tables");
            fail();
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Format of the command 'clear|tableName', but you type : clear|users|tables", e.getMessage());
        }
    }

}
