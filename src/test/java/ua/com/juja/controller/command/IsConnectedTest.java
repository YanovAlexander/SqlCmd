package ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class IsConnectedTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new IsConnected(view, manager);
    }

    @Test
    public void testProcessWrongCommand(){
        //when
        InputString userInput = new InputString("createQuery");
        command.process(userInput);
        //then
        verify(view).write("You can not use the command 'createQuery'," +
                " while not connect with the command connect|database|username|password");
    }

    @Test
    public void testProcessCommandHelp(){
        //when
        InputString userInput = new InputString("help");
        command.process(userInput);
        //then
        verify(view).write("You can not use the command 'help'," +
                " while not connect with the command connect|database|username|password");
    }
}
