package main.java.ua.com.juja.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.controller.Main;
import ua.com.juja.controller.command.Command;
import ua.com.juja.controller.command.Connect;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class TestConnect {
    private Command command;
    private DatabaseManager manager;
    private View view;
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Connect(view, manager);
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }


    @Test
    public void TestProcessWithParameters() {
        //when
        boolean result = command.canProcess("connect|postgres|postgres|pass");

        //then
        assertTrue(result);
    }

    @Test
    public void TestProcessWithRightParameters(){
        //given
        in.add("connect|postgres|postgres|pass");
        in.add("exit");

        //when
        Main.main(new String[0]);

        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "Connected successful\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Good Bye !\r\n", getData());
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}
