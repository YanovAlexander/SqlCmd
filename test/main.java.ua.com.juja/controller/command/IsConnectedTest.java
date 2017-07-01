package main.java.ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.IsConnected;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alexandero on 01.07.2017.
 */
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
        command.process("createQuery");
        //then
        verify(view).write("You can not use the command 'createQuery'," +
                " while not connect with the command connect|database|username|password");
    }

    @Test
    public void testProcessCommandHelp(){
        //when
        command.process("help");
        //then
        verify(view).write("You can not use the command 'help'," +
                " while not connect with the command connect|database|username|password");
    }




}
