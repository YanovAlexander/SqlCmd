package main.java.ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.DeleteTable;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Alexandero on 01.07.2017.
 */
public class DeleteTableTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DeleteTable(view, manager);
    }

    @Test
    public void testCanProcess(){
        //when
        boolean result = command.canProcess("deleteTable|");
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand(){
        //when
        boolean result = command.canProcess("deleteTables|");
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess(){
        when(view.read()).thenReturn("y");
        command.process("deleteTable|test");
        verify(view).write("Do you really want to delete table 'test ? All data will delete ! Press Y/N ?");
        verify(manager).deleteTable("test");
        verify(view).write("Table test delete successful !");
    }

    @Test
    public void testProcessPressNo(){
        when(view.read()).thenReturn("n");
        command.process("deleteTable|test");
        verify(view).write("Do you really want to delete table 'test ? All data will delete ! Press Y/N ?");
        verify(view).write("The action is canceled!");
    }
}
