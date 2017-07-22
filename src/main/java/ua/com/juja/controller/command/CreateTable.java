package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class CreateTable implements Command {
    private View view;
    private DatabaseManager manager;
    private final static Integer TABLE_WITH_DATA = 1;
    private final static Integer TABLE_NAME = 0;

    public CreateTable(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "createTable|tableName(id SERIAL NOT NULL PRIMARY KEY," +
                "username varchar(225) NOT NULL UNIQUE, password varchar(225))";
    }

    @Override
    public void process(InputString userInput) {
     userInput.validationParameters(format());
     String[] data = userInput.getParameters();
     String input =  data[TABLE_WITH_DATA];
     String tableName = input.split("\\(")[TABLE_NAME];

     manager.createTable(input);
     view.write("Table with name " + tableName + " created successful !");
    }
}
