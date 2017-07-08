package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class CreateTable implements Command {
    private View view;
    private DatabaseManager manager;

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
    public void process(InputValidation command) {
     command.validationParameters(format());
     String[] data = command.getParameters();
     String input =  data[1];
     String tableName = input.split("\\(")[0];

     manager.createTable(input);
     view.write("Table with name " + tableName + " created successful !");
    }
}
