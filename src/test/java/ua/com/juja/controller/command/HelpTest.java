package ua.com.juja.controller.command;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class HelpTest {
    private View view;
    private Command command;


    @Before
    public void setup() {
        view = mock(View.class);
        command = new Help(view);
    }

    @Test
    public void testCanProcessWithoutParameters() {
        //when
        InputString userInput = new InputString("help");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void testCantProcessWithWrongCommand(){
        //when
        InputString userInput = new InputString("helpCommand");
        boolean result = command.canProcess(userInput);
        //then
        assertFalse(result);
    }

    @Test
    public void testProcess(){
        //when
        InputString userInput = new InputString("help");
        command.process(userInput);
        //then
        verify(view).write("\t+----------------------------COMMANDS------------------------------");
    }
}
