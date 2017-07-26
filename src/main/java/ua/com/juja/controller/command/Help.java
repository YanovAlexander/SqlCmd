package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.view.View;

public class Help implements Command {

    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public String format() {
        return "help";
    }

    @Override
    public void process(InputString userInput) {
        userInput.validateParameters(format());
        view.write("\t+----------------------------COMMANDS------------------------------");

        view.write("\t| connect|database|username|password");
        view.write("\t|\t-> To get to the database, with which it is necessary to work");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| tables");
        view.write("\t|\t-> To get a list of all database tables");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| databases");
        view.write("\t|\t -> To get a list of all databases");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| find|tableName");
        view.write("\t|\t-> To retrieve table contents 'tableName'");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| clear|tableName");
        view.write("\t|\t-> To clear the entire table with the name 'tableName'");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| insertEntry|tableName|column1|value1|column2|value2...columnN|valueN");
        view.write("\t|\t-> To create an entry in the table named 'tableName'");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| updateEntry|tableName|ID");
        view.write("\t|\t-> update the entry in the table 'tableName' using the ID");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| createDatabase|databaseName");
        view.write("\t|\t -> Create new database named 'databaseName'");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| createTable|tableName");
        view.write("\t|\t -> Create new table named 'tableName'");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| deleteDatabase|databaseName");
        view.write("\t|\t -> Delete database named 'databaseName'");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| deleteTable|tableName");
        view.write("\t|\t -> Delete table named 'tableName'");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| disconnect");
        view.write("\t|\t -> disconnect from current database");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| exit");
        view.write("\t|\t-> To terminate the application");
        view.write("\t+------------------------------------------------------------------");

        view.write("\t| help");
        view.write("\t|\t-> To display this list on the screen");
        view.write("\t+------------------------------------------------------------------");
    }
}
