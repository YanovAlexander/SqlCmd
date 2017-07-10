package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by Alexandero on 10.07.2017.
 */
public class UpdateEntryTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new UpdateEntry(view, manager);
    }

    @Test
    public void testCanProcess() {
        //when
        boolean result = command.canProcess(new InputValidation("updateEntry|"));
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand() {
        //when
        boolean result = command.canProcess(new InputValidation("updateEntry1|"));
        //then
        assertFalse(result);
    }

    @Test
    public void testProcessWithWrongFormat() {
        //when
        try {
            command.process(new InputValidation("username|user|password|100"));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Invalid number of parameters separated by '|', expected 3, but was: 4", e.getMessage());
        }
    }

    @Test
    public void testProcessWithEmptyParameters() {
        //when
        try {
            command.process(new InputValidation("insertEntry|"));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number of parameters separated by '|', expected 3, but was: 1", e.getMessage());
        }
    }
}
