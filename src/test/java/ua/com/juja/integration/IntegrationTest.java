package ua.com.juja.integration;

import org.junit.*;
import ua.com.juja.controller.Main;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.PostgresManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private final static String DB_USERNAME = "postgres"; //change only username and password
    private final static String DB_PASSWORD = "pass";
    private final static String DB_NAME = "testing"; // db will be deleted, don't put db what you using, don't change db
    private final static String DB_NAME_SECOND = "testingdb";
    private final static String TABLE_NAME = "testing";
    private final static String SQL_CREATE_TABLE = TABLE_NAME + "(id SERIAL PRIMARY KEY," +
            " username VARCHAR (225) UNIQUE NOT NULL," +
            " password VARCHAR (225) NOT NULL)";

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;
    private static DatabaseManager manager;

    @BeforeClass
    public static void init() {
        manager = new PostgresManager();
        try {
            manager.connect("", DB_USERNAME, DB_PASSWORD);
        } catch (RuntimeException e) {
            throw new RuntimeException("Please enter the correct  DB_USERNAME and DB_PASSWORD " +
                    "to run integration tests");
        }
        manager.createDatabase(DB_NAME);
        manager.createDatabase(DB_NAME_SECOND);
        manager.connect(DB_NAME, DB_USERNAME, DB_PASSWORD);
        manager.createTable(SQL_CREATE_TABLE);
    }

    @Before
    public void setup() {
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @AfterClass
    public static void clearAfterAllTests() {
        try {
            manager.connect("", DB_USERNAME, DB_PASSWORD);
            manager.deleteDatabase(DB_NAME);
            manager.deleteDatabase(DB_NAME_SECOND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testHelp() {
        //given
        in.add("help");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "\t+----------------------------COMMANDS------------------------------\n" +
                "\t| connect|database|username|password\n" +
                "\t|\t-> To get to the database, with which it is necessary to work\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| tables\n" +
                "\t|\t-> To get a list of all database tables\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| databases\n" +
                "\t|\t -> To get a list of all databases\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| find|tableName\n" +
                "\t|\t-> To retrieve table contents 'tableName'\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| clear|tableName\n" +
                "\t|\t-> To clear the entire table with the name 'tableName'\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| insertEntry|tableName|column1|value1|column2|value2...columnN|valueN\n" +
                "\t|\t-> To create an entry in the table named 'tableName'\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| updateEntry|tableName|ID\n" +
                "\t|\t-> update the entry in the table 'tableName' using the ID\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| createDatabase|databaseName\n" +
                "\t|\t -> Create new database named 'databaseName'\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| createTable\n" +
                "\t|\t -> Create new table named 'tableName'\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| deleteDatabase|databaseName\n" +
                "\t|\t -> Delete database named 'databaseName'\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| deleteTable|tableName\n" +
                "\t|\t -> Delete table named 'tableName'\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| disconnect\n" +
                "\t|\t -> disconnect from current database\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| exit\n" +
                "\t|\t-> To terminate the application\n" +
                "\t+------------------------------------------------------------------\n" +
                "\t| help\n" +
                "\t|\t-> To display this list on the screen\n" +
                "\t+------------------------------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }


    @Test
    public void testExit() {
        //given
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testTablesWithoutConnect() {
        //given
        in.add("tables");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "You can not use the command 'tables', while not connect with the command connect|database|username|password\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testTablesFindUsersWithoutConnect() {
        //given
        in.add("find|users");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "You can not use the command 'find|users', while not connect with the command connect|database|username|password\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testTablesAfterConnect() {
        //when
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("tables");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //then
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "-------------------TABLES-------------------\n" +
                "- testing\n" +
                "- usersssss\n" +
                "- usersssssfff\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testFindAfterConnect() {
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("find|" + DB_NAME);
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "+--+--------+--------+\n" +
                "|id|username|password|\n" +
                "+--+--------+--------+\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testFindAfterConnectWithAddingData() {
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("insertEntry|testing|id|10|username|Alexandero|password|+++000");
        in.add("find|" + DB_NAME);
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : " +
                "connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "{names = [id, username, password], values = [10, Alexandero, +++000], }" +
                " was successfully created in table testing.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "+--+----------+--------+\n" +
                "|id|username  |password|\n" +
                "+--+----------+--------+\n" +
                "|10|Alexandero|+++000  |\n" +
                "+--+----------+--------+\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testUnsupportedWithoutConnect() {
        //given
        in.add("asdf");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands\n" +
                "You can not use the command 'asdf', while not connect with the" +
                " command connect|database|username|password\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testUnsupportedWithConnect() {
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("asdf");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Unsupported command: asdf\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testConnectAfterConnect() {
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("tables");
        in.add("connect|" + DB_NAME_SECOND + "|" + DB_USERNAME + "|" + DB_PASSWORD );
        in.add("tables");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "-------------------TABLES-------------------\n" +
                "- testing\n" +
                "- usersssss\n" +
                "- usersssssfff\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "-------------------TABLES-------------------\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testConnectWithError() {
        //given
        in.add("connect|postgres");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands\n" +
                "Error! Because of: Invalid number of parameters separated by '|', expected 4, but was: 2\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testClearWithError() {
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("clear");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Error! Because of: Invalid number of parameters separated by '|', expected 2, but was: 1\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testClearUnsupported() {
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("clear|papspsps|asasasa");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Error! Because of: Invalid number of parameters separated by '|', expected 2, but was: 3\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testInsertEntryWithError() {
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("insertEntry|users|bugaga");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Error! Because of: Invalid command, you must enter and even number of parameters in the following format: insertEntry|tableName|column1|value1|column2|value2...columnN|valueN\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testCreateTable(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("createTable");
        in.add("usersssss");
        in.add("id");
        in.add("name");
        in.add("password");
        in.add("finish");
        in.add("tables");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Enter name of creating table(name should start from letter) or type 'cancel' for exit to main menu\n" +
                "Name of new table : usersssss\n" +
                "Enter the name for PRIMARY KEY column(often it's an identifier):\n" +
                "Name of PRIMARY KEY column : id\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Name of column: name\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Name of column: password\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Table usersssss created successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "-------------------TABLES-------------------\n" +
                "- testing\n" +
                "- usersssss\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());

    }


    @Test
    public void testCreateTableWithColumnsStartFromNumber(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("createTable");
        in.add("12usersssss");
        in.add("usersssssfff");
        in.add("4id");
        in.add("id");
        in.add("43name");
        in.add("33password");
        in.add("password");
        in.add("finish");
        in.add("tables");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Enter name of creating table(name should start from letter) or type 'cancel' for exit to main menu\n" +
                "Name should start from letter, but you type '1'\n" +
                "Enter name of creating table(name should start from letter) or type 'cancel' for exit to main menu\n" +
                "Name of new table : usersssssfff\n" +
                "Enter the name for PRIMARY KEY column(often it's an identifier):\n" +
                "Name should start from letter, but you type '4'\n" +
                "Enter the name for PRIMARY KEY column(often it's an identifier):\n" +
                "Name of PRIMARY KEY column : id\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Name should start from letter, but you type '4'\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Name should start from letter, but you type '3'\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Name of column: password\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Table usersssssfff created successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "-------------------TABLES-------------------\n" +
                "- testing\n" +
                "- usersssss\n" +
                "- usersssssfff\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testCreateTableWithColumnsStartFromEmptyString(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("createTable");
        in.add("");
        in.add("usersssss");
        in.add("");
        in.add("id");
        in.add("name");
        in.add("");
        in.add("password");
        in.add("finish");
        in.add("tables");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Enter name of creating table(name should start from letter) or type 'cancel' for exit to main menu\n" +
                "Enter name for create table, you enter empty string\n" +
                "Enter name of creating table(name should start from letter) or type 'cancel' for exit to main menu\n" +
                "Name of new table : usersssss\n" +
                "Enter the name for PRIMARY KEY column(often it's an identifier):\n" +
                "Enter name for column of the table, you enter empty string\n" +
                "Enter the name for PRIMARY KEY column(often it's an identifier):\n" +
                "Name of PRIMARY KEY column : id\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Name of column: name\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Enter name for column of the table, you enter empty string\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Name of column: password\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Table usersssss created successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "-------------------TABLES-------------------\n" +
                "- testing\n" +
                "- usersssss\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testCreateTableWithCancelInColumn(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("createTable");
        in.add("userssssssss");
        in.add("id");
        in.add("name");
        in.add("password");
        in.add("cancel");
        in.add("tables");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Enter name of creating table(name should start from letter) or type 'cancel' for exit to main menu\n" +
                "Name of new table : userssssssss\n" +
                "Enter the name for PRIMARY KEY column(often it's an identifier):\n" +
                "Name of PRIMARY KEY column : id\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Name of column: name\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Name of column: password\n" +
                "Enter the name for next column or 'finish' to create table with entered columns or type 'cancel' for exit to main menu\n" +
                "Exit to main menu!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "-------------------TABLES-------------------\n" +
                "- testing\n" +
                "- usersssss\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }


    @Test
    public void testCreateTableWithCancelInColumnPrimaryKey(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("createTable");
        in.add("usersssss");
        in.add("cancel");
        in.add("tables");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Enter name of creating table(name should start from letter) or type 'cancel' for exit to main menu\n" +
                "Name of new table : usersssss\n" +
                "Enter the name for PRIMARY KEY column(often it's an identifier):\n" +
                "Exit to main menu!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "-------------------TABLES-------------------\n" +
                "- testing\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }


    @Test
    public void testCreateTableWithCancelInTableName(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("createTable");
        in.add("cancel");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format :" +
                " connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Enter name of creating table(name should start from letter) or type" +
                " 'cancel' for exit to main menu\n" +
                "Exit to main menu!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }


    @Test
    public void testDeleteDatabase(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("createDatabase|newdatabasetest");
        in.add("deleteDatabase|newdatabasetest");
        in.add("y");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Database with name newdatabasetest created successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Do you really want to delete database 'newdatabasetest'? All data will delete! If you sure press Y/N?\n" +
                "Database newdatabasetest delete successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testDeleteDatabaseWhichAlreadyConnected(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("deleteDatabase|" + DB_NAME);
        in.add("y");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Do you really want to delete database 'testing'? All data will delete! If you sure press Y/N?\n" +
                "You cant delete database to which already connected!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testDisconnectFromDB(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("disconnect");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : " +
                "connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Disconnected\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }


    @Test
    public void testUpdateEntry(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("insertEntry|testing|id|30|username|alex|password|987789");
        in.add("find|testing");
        in.add("updateEntry|testing|30");
        in.add("username|alexandero|password|8888");
        in.add("find|testing");
        in.add("clear|testing");
        in.add("find|testing");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "{names = [id, username, password], values = [30, alex, 987789], } was successfully created in table testing.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "+--+--------+--------+\n" +
                "|id|username|password|\n" +
                "+--+--------+--------+\n" +
                "|30|alex    |987789  |\n" +
                "+--+--------+--------+\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Enter data of entry in format 'column1|value1|column2|value2|....|columnN|valueN':\n" +
                "Update is successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "+--+----------+--------+\n" +
                "|id|username  |password|\n" +
                "+--+----------+--------+\n" +
                "|30|alexandero|8888    |\n" +
                "+--+----------+--------+\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Table testing was successfully cleaned.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "+--+--------+--------+\n" +
                "|id|username|password|\n" +
                "+--+--------+--------+\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testUpdateEntryWithError(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("insertEntry|testing|id|30|username|alex|password|987789");
        in.add("find|testing");
        in.add("updateEntry|testing|30");
        in.add("id|45|username|alexandero|password|8888");
        in.add("find|testing");
        in.add("clear|testing");
        in.add("tables");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "{names = [id, username, password], values = [30, alex, 987789], } was successfully created in table testing.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "+--+--------+--------+\n" +
                "|id|username|password|\n" +
                "+--+--------+--------+\n" +
                "|30|alex    |987789  |\n" +
                "+--+--------+--------+\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Enter data of entry in format 'column1|value1|column2|value2|....|columnN|valueN':\n" +
                "Error! Because of: Cant update , please try again (cant update Primary Key field)\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "+--+--------+--------+\n" +
                "|id|username|password|\n" +
                "+--+--------+--------+\n" +
                "|30|alex    |987789  |\n" +
                "+--+--------+--------+\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Table testing was successfully cleaned.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "-------------------TABLES-------------------\n" +
                "- testing\n" +
                "- usersssss\n" +
                "--------------------------------------------\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

    @Test
    public void testDatabaseListWithError(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("databases|blabla");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Error! Because of: Invalid number of parameters separated by '|', expected 1, but was: 2\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

  @Test
    public void testInsertWithWrongFormat(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("insertEntry|testing|id|10|user|hhh|pas|5656565");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Error! Because of: Cant insert to testing\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }


  @Test
    public void testConnectionWithErrorWrongParameters(){
        //given
        in.add("connect|" + DB_NAME + "|alex_test|" + DB_PASSWORD);
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Error! Because of: Can't get connection for model :testing user:alex_test FATAL: role \"alex_test\" does not exist\n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }

  @Test
    public void testClearNotExistTable(){
        //given
        in.add("connect|" + DB_NAME + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("clear|notexisttable");
        in.add("connect|" + "|" + DB_USERNAME + "|" + DB_PASSWORD);
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Welcome!\n" +
                "Enter the database name, username, and password of the user in format : connect|database|username|password  or use 'help' to list all commands\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Error! Because of: Wrong command, please try again. \n" +
                "Please try again.\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Connected successful!\n" +
                "Type command (or use 'help' to list all commands):\n" +
                "Good Bye!\n", getData());
    }


    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8").replaceAll("\r\n", "\n");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}
