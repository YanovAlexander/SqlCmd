package main.java.ua.com.juja.integration;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ua.com.juja.controller.Main;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;


/**
 * Created by Alexandero on 14.06.2017.
 */
public class IntegrationTest {

    private final String lineSeparator = System.lineSeparator();
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;
    private DatabaseManager databaseManager;

    @Before
    public void setup() {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF); //Disable log4j
        databaseManager = new JDBCDatabaseManager();
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }



    @Test
    public void testExit() {
        //given
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!" + lineSeparator +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands" + lineSeparator +
                "Good Bye !" + lineSeparator, getData());
    }

    @Test
    public void testTablesWithoutConnect() {
        //given
        in.add("tables");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!" + lineSeparator +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands" + lineSeparator +
                "You can not use the command 'tables', while not connect with the command " +
                "connect|database|username|password" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Good Bye !" + lineSeparator, getData());
    }

    @Test
    public void testTablesFindUsersWithoutConnect() {
        //given
        in.add("find|users");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!" + lineSeparator +
                "Enter the database name, username, and password of the user in format : " +
                "connect|database|username|password  or use 'help' to list all commands" + lineSeparator +
                "You can not use the command 'find|users', while not connect with the " +
                "command connect|database|username|password" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Good Bye !" + lineSeparator , getData());
    }

    @Test
    public void testUnsupportedWithoutConnect() {
        //given
        in.add("asdf");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!" + lineSeparator +
                "Enter the database name, username, and password of the user in format : " +
                "connect|database|username|password  or use 'help' to list all commands" + lineSeparator +
                "You can not use the command 'asdf', while not connect with the command " +
                "connect|database|username|password" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Good Bye !" + lineSeparator, getData());
    }

    @Test
    public void testUnsupportedWithConnect() {
        //given
        in.add("connect|postgres|postgres|pass");
        in.add("asdf");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!" + lineSeparator +
                "Enter the database name, username, and password of the user in format : " +
                "connect|database|username|password  or use 'help' to list all commands" + lineSeparator +
                "Connected successful" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Unsupported command :asdf" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Good Bye !" + lineSeparator, getData());
    }

    @Test
    public void testConnectAfterConnect() {
        //given
        in.add("connect|postgres|postgres|pass");
        in.add("tables");
        in.add("connect|test|postgres|pass");
        in.add("tables");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!" + lineSeparator +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands" + lineSeparator +
                "Connected successful" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "-------------------TABLES-------------------" + lineSeparator +
                "- users" + lineSeparator +
                "- test" + lineSeparator +
                "- mytable" + lineSeparator +
                "- mytable22"+ lineSeparator +
                "- sss"+ lineSeparator +
                "--------------------------------------------" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Connected successful" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "-------------------TABLES-------------------" + lineSeparator +
                "- ratata" + lineSeparator +
                "--------------------------------------------" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Good Bye !" + lineSeparator, getData());
    }

    @Test
    public void testConnectWithError() {
        //given
        in.add("connect|postgres");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!" + lineSeparator +
                "Enter the database name, username, and password of the user in format : " +
                "connect|database|username|password  or use 'help' to list all commands" + lineSeparator +
                "Error! Because of: Invalid number of parameters separeted be a sign \"|\" ," +
                "4 is required, but indicated : 2" + lineSeparator +
                "Please try again." + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Good Bye !" + lineSeparator, getData());
    }

    @Test
    public void testClearWithError() {
        //given
        in.add("connect|postgres|postgres|pass");
        in.add("clear");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!" + lineSeparator +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands" + lineSeparator +
                "Connected successful" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Unsupported command :clear" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Good Bye !" + lineSeparator, getData());
    }

    @Test
    public void testClearUnsupported() {
        //given
        in.add("connect|postgres|postgres|pass");
        in.add("clear|papspsps|asasasa");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!" + lineSeparator +
                "Enter the database name, username, and password of the user in format : " +
                "connect|database|username|password  or use 'help' to list all commands" + lineSeparator +
                "Connected successful" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Error! Because of: Format of the command 'clear|tableName', but you type :" +
                " clear|papspsps|asasasa" + lineSeparator +
                "Please try again." + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Good Bye !" + lineSeparator, getData());
    }

    @Test
    public void testCreatUserWithError() {
        //given
        in.add("connect|postgres|postgres|pass");
        in.add("insertEntry|users|bugaga");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!" + lineSeparator +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands" + lineSeparator +
                "Connected successful" + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Error! Because of: There must be an even number of parameters in the" +
                " format insertEntry|tableName|column1|value1|column2|value2...columnN|valueN, but indicated :" +
                " 'insertEntry|users|bugaga'" + lineSeparator +
                "Please try again." + lineSeparator +
                "Type command (or use 'help' to list all commands):" + lineSeparator +
                "Good Bye !" + lineSeparator, getData());
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
