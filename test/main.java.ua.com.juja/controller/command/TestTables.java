package main.java.ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.mockito.ArgumentCaptor;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.Tables;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class TestTables {
    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Tables(view, manager);
    }

    @Test
    public void CanProcessWithoutParameter(){
        //when
        boolean result = command.canProcess("tables");

        //then
        assertTrue(result);
    }

    @Test
    public void CantProcessWithParameters(){
        //when
        boolean result = command.canProcess("tables|users");

        //then
        assertFalse(result);

    }

    @Test
    public void CantProcessWithWrongCommand(){
        //when
        boolean result = command.canProcess("tables1");

        //then
        assertFalse(result);
    }
}
