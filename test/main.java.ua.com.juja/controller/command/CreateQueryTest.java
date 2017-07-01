package main.java.ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.CreateQuery;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alexandero on 01.07.2017.
 */
public class CreateQueryTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new CreateQuery(view, manager);
    }

    @Test
    public void testCanProcess() {
        //when
        boolean result = command.canProcess("createQuery|");
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand() {
        //when
        boolean result = command.canProcess("createQuery1|");
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess() {
        //when
        command.process("createQuery|users|id|26|userName|Alex|password|++++");
        //then
        shouldPrint("[{names = [id, userName, password], values = [26, Alex, ++++], } was successfully created in table users.]");
    }

    @Test
    public void testProcessWithWrongFormat() {
        //when
        try {
            command.process("createQuery|users|name");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("There must be an even number of parameters in the format" +
                    " createQuery|tableName|column1|value1|column2|value2...columnN|valueN, but indicated :" +
                    " 'createQuery|users|name'", e.getMessage());
        }
    }

    @Test
    public void testProcessWithEmptyParameters() {
        //when
        try {
            command.process("createQuery|");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("There must be an even number of parameters in the format " +
                    "createQuery|tableName|column1|value1|column2|value2...columnN|valueN," +
                    " but indicated : 'createQuery|'", e.getMessage());
        }
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
