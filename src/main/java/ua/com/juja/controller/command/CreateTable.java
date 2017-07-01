package ua.com.juja.controller.command;

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
    public boolean canProcess(String command) {
        return command.startsWith("createTable|");
    }

    @Override
    public void process(String command) {
     int vaildParameters = (command.split("\\(")).length;
     if (vaildParameters < 2){
         throw new IllegalArgumentException(String.format("There must be an even number of parameters in the format " +
                    "'createTable|tableName(id SERIAL NOT NULL PRIMARY KEY," +
                    "username varchar(225) NOT NULL UNIQUE, password varchar(225))',  but indicated " + command));
     }
     String input =  command.split("\\|")[1];
     String tableName = input.split("\\(")[0];

     manager.createTable(input);
     view.write("Table with name " + tableName + " created successful !");
    }
}
