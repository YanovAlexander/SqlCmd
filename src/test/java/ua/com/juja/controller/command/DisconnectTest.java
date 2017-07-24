package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DisconnectTest {

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DisconnectFromDB(view, manager);
    }

    @Test
    public void disconnectCanProcessTest() {
        //when
        InputString userInput = new InputString("disconnect");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void clearCantProcessWithoutParametersTest() {
        //when
        InputString userInput = new InputString("disssconnect11");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }

    @Test
    public void clearCantProcessWithQWETest() {
        //when
        InputString userInput = new InputString("qwe|users");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }
    
}
