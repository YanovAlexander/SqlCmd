package ua.com.juja.controller.command;

import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.Exit;
import ua.com.juja.controller.command.ExitException;
import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.view.View;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Alexandero on 17.06.2017.
 */
public class ExitTestWithMockito {

    private View view = Mockito.mock(View.class);

    @Test
    public void exitCanProcessTest() {
        //given
        Command command = new Exit(view);

        //when
        boolean exit = command.canProcess(new InputValidation("exit"));

        //then
        assertTrue(exit);
    }

    @Test
    public void exitCantProcessQWETest() {
        //given
        Command command = new Exit(view);

        //when
        boolean exit = command.canProcess(new InputValidation("qwe"));

        //then
        assertFalse(exit);
    }

    @Test
    public void exitProcessTest() {
        //given
        Command command = new Exit(view);

        //when
        try {
            command.process(new InputValidation("exit"));
            fail("Expected ExitException");
        } catch (ExitException e) {
            //do nothing
        }
        //then
        Mockito.verify(view).write("Good Bye !");
    }

}
