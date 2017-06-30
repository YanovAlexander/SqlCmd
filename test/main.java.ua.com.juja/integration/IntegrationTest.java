package main.java.ua.com.juja.integration;

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

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;
    private DatabaseManager databaseManager;

    @Before
    public void setup() {
        databaseManager = new JDBCDatabaseManager();
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }


    @Test
    public void testHelp() {
        //given
        in.add("help");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "-------------------COMMANDS-----------------\r\n" +
                "\tconnect|database|username|password\r\n" +
                "\t\t-> To get to the database, with which it is necessary to work\r\n" +
                "\ttables\r\n" +
                "\t\t-> To get a list of all database tables\r\n" +
                "\t databaseList\r\n" +
                "\t\t -> To get a list of all databases\r\n" +
                "\tfind|tableName\r\n" +
                "\t\t-> To retrieve table contents 'tableName'\r\n" +
                "\tclear|tableName\r\n" +
                "\t\t-> To clear the entire table with the name 'tableName'\r\n" +
                "\tcreate|tableName|column1|value1|column2|value2...columnN|valueN\r\n" +
                "\t\t-> To create an entry in the table named 'tableName'\r\n" +
                "\t createDatabase|databaseName\r\n" +
                "\t\t -> Create new database named 'databaseName'\r\n" +
                "\texit\r\n" +
                "\t\t-> To terminate the application\r\n" +
                "\thelp\r\n" +
                "\t\t-> To display this list on the screen\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Good Bye !\r\n", getData());
    }

    @Test
    public void testExit() {
        //given
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands\r\n" +
                "Good Bye !\r\n", getData());
    }

    @Test
    public void testTablesWithoutConnect() {
        //given
        in.add("tables");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "You can not use the command 'tables', while not connect with the command connect|database|username|password\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Good Bye !\r\n", getData());
    }

    @Test
    public void testTablesFindUsersWithoutConnect() {
        //given
        in.add("find|users");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "You can not use the command 'find|users', while not connect with the command connect|database|username|password\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Good Bye !\r\n", getData());
    }

    @Test
    public void testUnsupportedWithoutConnect() {
        //given
        in.add("asdf");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "You can not use the command 'asdf', while not connect with the command connect|database|username|password\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Good Bye !\r\n", getData());
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
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "Connected successful\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Unsupported command :asdf\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Good Bye !\r\n", getData());
    }

//    @Test
//    public void testFindAfterConnect() {
//        //given
//        in.add("connect|postgres|postgres|pass");
//        in.add("find|users");
//        in.add("exit");
//
//        //when
//        Main.main(new String[0]);
//
//        //then
//        assertEquals("Welcome!\r\n" +
//                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
//                "Connected successful\r\n" +
//                "Type command (or use 'help' to list all commands):\r\n" +
//                "+--+--------+--------+\r\n" +
//                "|id|username|password|\r\n" +
//                "+--+--------+--------+\r\n" +
//                "Type command (or use 'help' to list all commands):\r\n" +
//                "Good Bye !\r\n", getData());
//    }

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
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "Connected successful\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "-------------------TABLES-------------------\r\n" +
                "- users\r\n" +
                "- test\r\n" +
                "--------------------------------------------\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Connected successful\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "-------------------TABLES-------------------\r\n" +
                "- ratata\r\n" +
                "--------------------------------------------\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Good Bye !\r\n", getData());
    }

    @Test
    public void testConnectWithError() {
        //given
        in.add("connect|postgres");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "Error! Because of: Invalid number of parameters separeted be a sign \"|\" ,4 is required, but indicated : 2\r\n" +
                "Please try again.\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Good Bye !\r\n", getData());
    }
//
//    @Test
//    public void testFindAfterConnectWithData() {
//        //given
//        in.add("connect|postgres|postgres|pass");
//        in.add("clear|users");
//        in.add("create|users|id|13|username|Beckcham|password|******");
//        in.add("create|users|id|15|username|Martial|password|+++++");
//        in.add("find|users");
//        in.add("exit");
//
//        //when
//        Main.main(new String[0]);
//
//        //then
//        assertEquals("Welcome!\r\n" +
//                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
//                "Connected successful\r\n" +
//                "Type command (or use 'help' to list all commands):\r\n" +
//                "Table users was successfully cleaned.\r\n" +
//                "Type command (or use 'help' to list all commands):\r\n" +
//                "{names = [id, username, password], values = [13, Beckcham, ******], } was successfully created in table users.\r\n" +
//                "Type command (or use 'help' to list all commands):\r\n" +
//                "{names = [id, username, password], values = [15, Martial, +++++], } was successfully created in table users.\r\n" +
//                "Type command (or use 'help' to list all commands):\r\n" +
//                "+--+--------+--------+\r\n" +
//                "|id|username|password|\r\n" +
//                "+--+--------+--------+\r\n" +
//                "|13|Beckcham|******  |\r\n" +
//                "+--+--------+--------+\r\n" +
//                "|15|Martial |+++++   |\r\n" +
//                "+--+--------+--------+\r\n" +
//                "Type command (or use 'help' to list all commands):\r\n" +
//                "Good Bye !\r\n", getData());
//    }

    @Test
    public void testClearWithError() {
        //given
        in.add("connect|postgres|postgres|pass");
        in.add("clear");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "Connected successful\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Unsupported command :clear\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Good Bye !\r\n", getData());
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
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "Connected successful\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Error! Because of: Format of the command 'clear|tableName', but you type : clear|papspsps|asasasa\r\n" +
                "Please try again.\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Good Bye !\r\n", getData());
    }

    @Test
    public void testCreatUserWithError() {
        //given
        in.add("connect|postgres|postgres|pass");
        in.add("create|users|bugaga");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\r\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\r\n" +
                "Connected successful\r\n" +
                "Type command (or use 'help' to list all commands):\r\n" +
                "Error! Because of: There must be an even number of parameters in the format create|tableName|column1|value1|column2|value2...columnN|valueN, but indicated : 'create|users|bugaga'\r\n" +
                "Please try again.\r\n" +
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
