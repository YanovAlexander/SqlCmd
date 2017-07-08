package main.java.ua.com.juja.controller.command;

import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.Exit;
import ua.com.juja.controller.command.ExitException;
import org.junit.Test;
import ua.com.juja.controller.command.util.InputValidation;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Alexandero on 17.06.2017.
 */
public class ExitTest {

    private ViewTest view = new ViewTest();

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
        assertEquals("Good Bye !\n", view.getContent());
    }


}
