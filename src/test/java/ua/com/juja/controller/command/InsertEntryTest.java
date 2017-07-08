package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.InsertEntry;
import ua.com.juja.controller.command.util.InputValidation;
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
public class InsertEntryTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new InsertEntry(view, manager);
    }

    @Test
    public void testCanProcess() {
        //when
        boolean result = command.canProcess(new InputValidation("insertEntry|"));
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand() {
        //when
        boolean result = command.canProcess(new InputValidation("createQuery1|"));
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess() {
        //when
        command.process(new InputValidation("createQuery|users|id|26|userName|Alex|password|++++"));
        //then
        shouldPrint("[{names = [id, userName, password], values = [26, Alex, ++++], } was successfully created in table users.]");
    }

    @Test
    public void testProcessWithWrongFormat() {
        //when
        try {
            command.process(new InputValidation("insertEntry|users|name"));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Invalid command, you must enter and even number of parameters" +
                    " in the following format : " +
                    "insertEntry|tableName|column1|value1|column2|value2...columnN|valueN", e.getMessage());
        }
    }

    @Test
    public void testProcessWithEmptyParameters() {
        //when
        try {
            command.process(new InputValidation("insertEntry|"));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid command, you must enter and even number of parameters " +
                    "in the following format : " +
                    "insertEntry|tableName|column1|value1|column2|value2...columnN|valueN", e.getMessage());
        }
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
