package ua.com.juja.controller.command;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.view.View;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class UnsupportedCommandTest {
    private Command command;
    private View view;

    @Before
    public void setup(){
        view = mock(View.class);
        command = new UnsupportedCommand(view);
    }

    @Test
    public void canProcess(){
        //when
        InputString userInput = new InputString("unsupported");
        boolean result = command.canProcess(userInput);
        //then
        assertTrue(result);
    }

    @Test
    public void process(){
        //when
        InputString userInput = new InputString("unsupported");
        command.process(userInput);
        //then
       shouldPrint("[Unsupported command :unsupported]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        TestCase.assertEquals(expected, captor.getAllValues().toString());
    }
}
