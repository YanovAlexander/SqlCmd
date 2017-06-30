package main.java.ua.com.juja.controller.command;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.*;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class TestHelp {
    private View view;
    private DatabaseManager manager;
    private Command command;
    private Command[] commands;


    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Help(view);
        commands = new Command[]{
                new Clear(view, manager),
                new Connect(view, manager),
                new Create(view, manager),
                new CreateDatabase(view, manager),
                new CreateTable(view, manager),
                new DatabaseList(view, manager),
                new DeleteDatabase(view, manager),
                new DeleteTable(view, manager),
                new Exit(view),
                new Find(view, manager),
                new Help(view),
                new Tables(view, manager),
        };
    }

    @Test
    public void testCanProcessWithoutParameters() {
        //when
        boolean result = command.canProcess("help");

        //then
        assertTrue(result);
    }

}
