package controller.command;

import main.java.ua.com.juja.controller.command.Command;
import main.java.ua.com.juja.controller.command.Exit;
import main.java.ua.com.juja.controller.command.ExitException;
import main.java.ua.com.juja.view.View;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Alexandero on 17.06.2017.
 */
public class ExitTest {

    private TestView view = new TestView();

    @Test
    public void exitCanProcessTest() {
        //given
        Command command = new Exit(view);

        //when
        boolean exit = command.canProcess("exit");

        //then
        assertTrue(exit);
    }

    @Test
    public void exitCantProcessQWETest() {
        //given
        Command command = new Exit(view);

        //when
        boolean exit = command.canProcess("qwe");

        //then
        assertFalse(exit);
    }

    @Test
    public void exitProcessTest() {
        //given
        Command command = new Exit(view);

        //when
        try {
            command.process("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            //do nothing
        }
        //then
        assertEquals("Good Bye !\n", view.getContent());
    }


}
