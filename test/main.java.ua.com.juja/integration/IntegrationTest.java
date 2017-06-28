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
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате :" +
                " connect|database|username|password\r\n" +
                "Сущеуствующие команды\r\n" +
                "\tconnect|database|username|password\r\n" +
                "\t\tдля получения к базе данных, с которой необъходимо работать\r\n" +
                "\ttables\r\n" +
                "\t\tдля получения списка всех таблиц базы\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tдля получения содержимого таблицы 'tableName'\r\n" +
                "\tclear|tableName\r\n" +
                "\t\tдля очистки всей таблицы с названием 'tableName'\r\n" +
                "\tcreate|tableName|column1|value1|column2|value2...columnN|valueN\r\n" +
                "\t\tдля создания записи в таблице с названием 'tableName'\r\n" +
                "\texit\r\n" +
                "\t\tдля завершения работы приложения\r\n" +
                "\thelp\r\n" +
                "\t\tдля вывода этого списка на экран\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Good Bye !\r\n", getData());
    }

    @Test
    public void testExit() {
        //given
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате :" +
                " connect|database|username|password\r\n" +
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
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате :" +
                " connect|database|username|password\r\n" +
                "Вы не можете пользоваться командой 'tables', пока не подключитесь с помощью команды " +
                "connect|database|username|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
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
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате :" +
                " connect|database|username|password\r\n" +
                "Вы не можете пользоваться командой 'find|users', пока не подключитесь с помощью команды " +
                "connect|database|username|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
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
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате :" +
                " connect|database|username|password\r\n" +
                "Вы не можете пользоваться командой 'asdf', пока не подключитесь с помощью команды " +
                "connect|database|username|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
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
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате : " +
                "connect|database|username|password\r\n" +
                "Успех\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Несуществующая команда :asdf\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Good Bye !\r\n", getData());
    }

    @Test
    public void testFindAfterConnect() {
        //given
        in.add("connect|postgres|postgres|pass");
        in.add("find|users");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате : " +
                "connect|database|username|password\r\n" +
                "Успех\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "------------------------\r\n" +
                "|id|username|password|\r\n" +
                "------------------------\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Good Bye !\r\n", getData());
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
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате : " +
                "connect|database|username|password\r\n" +
                "Успех\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "[users, test]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Успех\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "[ratata]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
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
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате :" +
                " connect|database|username|password\r\n" +
                "Неудача! по причине: Неверное колличество параметров разделенных знаком \"|\" ," +
                "4 is required, but indicated : 2\r\n" +
                "Повтори попытку.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Good Bye !\r\n", getData());
    }

    @Test
    public void testFindAfterConnectWithData() {
        //given
        in.add("connect|postgres|postgres|pass");
        in.add("clear|users");
        in.add("create|users|id|13|username|Beckcham|password|******");
        in.add("create|users|id|15|username|Martial|password|+++++");
        in.add("find|users");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате :" +
                " connect|database|username|password\r\n" +
                "Успех\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Таблица users была успешно очищена.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Запись {names = [id, username, password], values = [13, Beckcham, ******], }" +
                " была успешно создана в таблице users.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Запись {names = [id, username, password], values = [15, Martial, +++++], } " +
                "была успешно создана в таблице users.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "------------------------\r\n" +
                "|id|username|password|\r\n" +
                "------------------------\r\n" +
                "|13|Beckcham|******|\r\n" +
                "|15|Martial|+++++|\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Good Bye !\r\n", getData());
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
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате :" +
                " connect|database|username|password\r\n" +
                "Успех\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Несуществующая команда :clear\r\n" +
                "Введи команду (или help для помощи):\r\n" +
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
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате :" +
                " connect|database|username|password\r\n" +
                "Успех\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Неудача! по причине: Формат комманды 'clear|tableName', а ты ввел : clear|papspsps|asasasa\r\n" +
                "Повтори попытку.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
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
        assertEquals("Привет ползьватель !\r\n" +
                "Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате :" +
                " connect|database|username|password\r\n" +
                "Успех\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Неудача! по причине: Должно быть четное колличество параметров в формате " +
                "create|tableName|column1|value1|column2|value2...columnN|valueN, а указано :" +
                " 'create|users|bugaga'\r\n" +
                "Повтори попытку.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
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
