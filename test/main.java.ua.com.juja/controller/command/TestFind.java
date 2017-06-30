package main.java.ua.com.juja.controller.command;

import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.Find;
import ua.com.juja.model.DataSet;
import ua.com.juja.model.DataSetImpl;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Alexandero on 17.06.2017.
 */
public class TestFind {

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(view, manager);
    }

    @Test
    public void testPrintTableData() {
        //given
        when(manager.getTableColumns("users"))
                .thenReturn(new LinkedHashSet<String>(Arrays.asList("id", "username", "password")));

        DataSet user1 = new DataSetImpl();
        user1.put("id", 10);
        user1.put("username", "Allan");
        user1.put("password", "*****");

        DataSet user2 = new DataSetImpl();
        user2.put("id", 11);
        user2.put("username", "Martial");
        user2.put("password", "+++===");

        when(manager.getTableData("users")).thenReturn(Arrays.asList(user1, user2));

        //when
        command.process("find|users");

        //then

        shouldPrint("[+--+--------+--------+\n" +
                "|id|username|password|\n" +
                "+--+--------+--------+\n" +
                "|10|Allan   |*****   |\n" +
                "+--+--------+--------+\n" +
                "|11|Martial |+++===  |\n" +
                "+--+--------+--------+]");
    }


    @Test
    public void findCanProcessTest() {

        //when
        boolean result = command.canProcess("find|users");

        //then
        assertTrue(result);
    }

    @Test
    public void findCantProcessWithoutParametersTest() {

        //when
        boolean result = command.canProcess("find");

        //then
        assertFalse(result);
    }

    @Test
    public void findCantProcessWithQWETest() {

        //when
        boolean result = command.canProcess("qwe");

        //then
        assertFalse(result);
    }


    @Test
    public void testPrintEmptyTableData() {

        when(manager.getTableColumns("users"))
                .thenReturn(new LinkedHashSet<String>(Arrays.asList("id", "username", "password")));


        when(manager.getTableData("users")).thenReturn(new ArrayList<>());

        //when
        command.process("find|users");

        //then

        shouldPrint("[+--+--------+--------+\n" +
                "|id|username|password|\n" +
                "+--+--------+--------+]");
    }

    @Test
    public void testPrintTableDataWithOneID() {
        //given
        when(manager.getTableColumns("users"))
                .thenReturn((new LinkedHashSet<String>(Arrays.asList("id"))));

        DataSet user1 = new DataSetImpl();
        user1.put("id", 10);

        DataSet user2 = new DataSetImpl();
        user2.put("id", 11);

        when(manager.getTableData("users")).thenReturn(Arrays.asList(user1, user2));

        //when
        command.process("find|users");

        //then

        shouldPrint("[+--+\n" +
                "|id|\n" +
                "+--+\n" +
                "|10|\n" +
                "+--+\n" +
                "|11|\n" +
                "+--+]");
    }

    @Test
    public void testErrorWhenWrongCommandFind() {

        when(manager.getTableColumns("users"))
                .thenReturn(new LinkedHashSet<>(Arrays.asList("id", "username", "password")));


        when(manager.getTableData("users")).thenReturn(new ArrayList<>());

        //when
        try {
            command.process("find|users|wtf");
            fail("Expected exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Format of the command 'find|tableName', but indicated : find|users|wtf", e.getMessage());
        }
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

}
