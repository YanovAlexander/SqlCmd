package main.java.ua.com.juja.integration;

import org.junit.*;
import ua.com.juja.controller.Main;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.JDBCDatabaseManager;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;


/**
 * Created by Alexandero on 14.06.2017.
 */
public class IntegrationTest {

    private final static String DB_USERNAME = "postgres";
    private final static String DB_PASSWORD = "pass";
    private final static String DB_NAME = "postgres"; // db will be deleted, don't put db what you using
    private final static String TABLE_NAME = "testing";
    private final static String SQL_CREATE_TABLE = TABLE_NAME + "(id SERIAL PRIMARY KEY," +
            " username VARCHAR (225) UNIQUE NOT NULL," +
            " password VARCHAR (225) NOT NULL)";

    private final String LINE_SEPARATOR = System.lineSeparator();
    private static DatabaseManager manager;
    private ViewMock view;

    @BeforeClass
    public static void init(){
        manager = new JDBCDatabaseManager();
        manager.connect(DB_NAME, DB_USERNAME, DB_PASSWORD);
        manager.createTable(SQL_CREATE_TABLE);
    }

    @Before
    public  void setup() {
       view = new ViewMock();
    }

    @AfterClass
    public static void clearAfterAllTests(){
        manager.deleteTable(TABLE_NAME);
    }



    @Test
    public void testExit() {
        //given
        view.addIn("exit");

        //when
        Main.main(new String[0]);

        //then
        assertOutput("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "exit\n" +
                "Good Bye !\n");
    }

    @Test
    public void testTablesWithoutConnect() {
        //given
        view.addIn("tables");
        view.addIn("exit");

        //when
        Main.main(new String[0]);

        //then
        assertOutput("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "tables\n" +
                "You can not use the command 'tables', while not connect with the command connect|database|username|password\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "exit\n" +
                "Good Bye !\n");
    }

    @Test
    public void testTablesFindUsersWithoutConnect() {
        //given
        view.addIn("find|users");
        view.addIn("exit");

        //when
        Main.main(new String[0]);

        //then
        assertOutput("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "find|users\n" +
                "You can not use the command 'find|users', while not connect with the command connect|database|username|password\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "exit\n" +
                "Good Bye !\n");
    }

    @Test
    public void testUnsupportedWithoutConnect() {
        //given
        view.addIn("asdf");
        view.addIn("exit");

        //when
        Main.main(new String[0]);

        //then
        assertOutput("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "asdf\n" +
                "You can not use the command 'asdf', while not connect with the command connect|database|username|password\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "exit\n" +
                "Good Bye !\n");
    }

    @Test
    public void testUnsupportedWithConnect() {
        //given
        view.addIn("connect|postgres|postgres|pass");
        view.addIn("asdf");
        view.addIn("exit");

        //when
        Main.main(new String[0]);

        //then
        assertOutput("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "connect|postgres|postgres|pass\n" +
                "Connected successful\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "asdf\n" +
                "Unsupported command :asdf\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "exit\n" +
                "Good Bye !\n");
    }

    @Test
    public void testConnectAfterConnect() {
        //given
        view.addIn("connect|postgres|postgres|pass");
        view.addIn("tables");
        view.addIn("connect|test|postgres|pass");
        view.addIn("tables");
        view.addIn("exit");

        //when
        Main.main(new String[0]);

        //then
        assertOutput("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "connect|postgres|postgres|pass\n" +
                "Connected successful\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "tables\n" +
                "-------------------TABLES-------------------\n" +
                "- users\n" +
                "- test\n" +
                "- mytable\n" +
                "- mytable22\n" +
                "- sss\n" +
                "- testing\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "connect|test|postgres|pass\n" +
                "Connected successful\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "tables\n" +
                "-------------------TABLES-------------------\n" +
                "- ratata\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "exit\n" +
                "Good Bye !\n");
    }

    @Test
    public void testConnectWithError() {
        //given
        view.addIn("connect|postgres");
        view.addIn("exit");

        //when
        Main.main(new String[0]);

        //then
        assertOutput("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "connect|postgres\n" +
                "Error! Because of: Invalid number of parameters separated by '|', expected 4, but was: 2\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "exit\n" +
                "Good Bye !\n");
    }

    @Test
    public void testClearWithError() {
        //given
        view.addIn("connect|postgres|postgres|pass");
        view.addIn("clear");
        view.addIn("exit");

        //when
        Main.main(new String[0]);

        //then
        assertOutput("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "connect|postgres|postgres|pass\n" +
                "Connected successful\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "clear\n" +
                "Error! Because of: Invalid number of parameters separated by '|', expected 2, but was: 1\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "exit\n" +
                "Good Bye !\n");
    }

    @Test
    public void testClearUnsupported() {
        //given
        view.addIn("connect|postgres|postgres|pass");
        view.addIn("clear|papspsps|asasasa");
        view.addIn("exit");

        //when
        Main.main(new String[0]);

        //then
        assertOutput("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "connect|postgres|postgres|pass\n" +
                "Connected successful\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "clear|papspsps|asasasa\n" +
                "Error! Because of: Invalid number of parameters separated by '|', expected 2, but was: 3\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "exit\n" +
                "Good Bye !\n");
    }

    @Test
    public void testCreatUserWithError() {
        //given
        view.addIn("connect|postgres|postgres|pass");
        view.addIn("insertEntry|users|bugaga");
        view.addIn("exit");

        //when
        Main.main(new String[0]);

        //then
        assertOutput("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "connect|postgres|postgres|pass\n" +
                "Connected successful\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "insertEntry|users|bugaga\n" +
                "Error! Because of: Invalid command, you must enter and even number of parameters in the following format : insertEntry|tableName|column1|value1|column2|value2...columnN|valueN\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "exit\n" +
                "Good Bye !\n");
    }


  private void assertOutput(String expected){
        assertEquals(expected.replaceAll("\\n", System.lineSeparator()).replaceAll("%s", "\n"), view.getOut());
  }
}
