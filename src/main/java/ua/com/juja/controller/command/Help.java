package ua.com.juja.controller.command;

import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class Help implements Command {

    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        doHelp();
    }

    private void doHelp() {
        view.write("-------------------COMMANDS-----------------");

        view.write("\tconnect|database|username|password");
        view.write("\t\t-> To get to the database, with which it is necessary to work");

        view.write("\ttables");
        view.write("\t\t-> To get a list of all database tables");

        view.write("\t databaseList");
        view.write("\t\t -> To get a list of all databases");

        view.write("\tfind|tableName");
        view.write("\t\t-> To retrieve table contents 'tableName'");

        view.write("\tclear|tableName");
        view.write("\t\t-> To clear the entire table with the name 'tableName'");

        view.write("\tcreate|tableName|column1|value1|column2|value2...columnN|valueN");
        view.write("\t\t-> To create an entry in the table named 'tableName'"); //TODO если ввел команду, переспросить

        view.write("\t createDatabase|databaseName");
        view.write("\t\t -> Create new database named 'databaseName'");

        view.write("\texit");
        view.write("\t\t-> To terminate the application");

        view.write("\thelp");
        view.write("\t\t-> To display this list on the screen");
    }
}
