package ua.com.juja.controller.command;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ua.com.juja.controller.command.util.InputString;
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

public class FindTest {

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup() {
        Logger.getRootLogger().setLevel(Level.INFO); //Disable log4j
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(view, manager);
    }

    @Test
    public void testPrintTableData() {
        //given
        LinkedHashSet<String> table = new LinkedHashSet<>(Arrays.asList("id", "username", "password"));
        when(manager.getTableColumns("users")).thenReturn(table);

        DataSet user1 = new DataSetImpl();
        user1.put("id", 10);
        user1.put("username", "Allan");
        user1.put("password", "*****");

        DataSet user2 = new DataSetImpl();
        user2.put("id", 11);
        user2.put("username", "Martial");
        user2.put("password", "+++===");

        List<DataSet> tableData = Arrays.asList(user1, user2);
        when(manager.getTableData("users")).thenReturn(tableData);
        //when
        InputString userInput = new InputString("find|users");
        command.process(userInput);
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
        InputString userInput = new InputString("find|users");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void findCantProcessWithoutParametersTest() {
        //when
        InputString userInput = new InputString("find");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void findCantProcessWithQWETest() {
        //when
        InputString userInput = new InputString("qwe");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }


    @Test
    public void testPrintEmptyTableData() {
        LinkedHashSet<String> table = new LinkedHashSet<>(Arrays.asList("id", "username", "password"));
        when(manager.getTableColumns("users")).thenReturn(table);
        when(manager.getTableData("users")).thenReturn(new ArrayList<>());
        //when
        InputString userInput = new InputString("find|users");
        command.process(userInput);
        //then

        shouldPrint("[+--+--------+--------+\n" +
                "|id|username|password|\n" +
                "+--+--------+--------+]");
    }

    @Test
    public void testPrintTableDataWithOneID() {
        //given
        LinkedHashSet<String> table = new LinkedHashSet<>(Arrays.asList("id"));
        when(manager.getTableColumns("users"))
                .thenReturn(table);

        DataSet user1 = new DataSetImpl();
        user1.put("id", 10);

        DataSet user2 = new DataSetImpl();
        user2.put("id", 11);

        List<DataSet> tableData = Arrays.asList(user1, user2);
        when(manager.getTableData("users")).thenReturn(tableData);
        //when
        InputString userInput = new InputString("find|users");
        command.process(userInput);
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
        LinkedHashSet<String> table = new LinkedHashSet<>(Arrays.asList("id", "username", "password"));
        when(manager.getTableColumns("users"))
                .thenReturn(table);
        ArrayList<DataSet> tableData = new ArrayList<>();
        when(manager.getTableData("users")).thenReturn(tableData);
        //when
        try {
            InputString userInput = new InputString("find|users|wtf");
            command.process(userInput);
            fail("Expected exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number of parameters separated by '|', expected 2, but was: 3", e.getMessage());
        }
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
