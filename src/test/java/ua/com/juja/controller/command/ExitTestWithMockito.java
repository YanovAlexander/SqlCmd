package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.view.View;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ExitTestWithMockito {

    private View view = Mockito.mock(View.class);

    @Test
    public void exitCanProcessTest() {
        //given
        Command command = new Exit(view);

        //when
        InputString userInput = new InputString("exit");
        boolean exit = command.canProcess(userInput);

        //then
        assertTrue(exit);
    }

    @Test
    public void exitCantProcessQWETest() {
        //given
        Command command = new Exit(view);

        //when
        InputString userInput = new InputString("qwe");
        boolean exit = command.canProcess(userInput);

        //then
        assertFalse(exit);
    }

    @Test
    public void exitProcessTest() {
        //given
        Command command = new Exit(view);

        //when
        try {
            InputString userInput = new InputString("exit");
            command.process(userInput);
            fail("Expected ExitException");
        } catch (ExitException e) {
            //do nothing
        }
        //then
        Mockito.verify(view).write("Good Bye!");
    }
}
