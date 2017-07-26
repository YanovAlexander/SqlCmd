package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.util.Set;

public class DatabaseList implements Command {
    private View view;
    private DatabaseManager manager;


    public DatabaseList(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "databases";
    }

    @Override
    public void process(InputString userInput) {
        userInput.validateParameters(format());
        Set<String> databasesSet = manager.databasesList();
        view.write("-------------------DATABASES----------------");
        for (String database : databasesSet){
            view.write("- " + database);
        }
        view.write("--------------------------------------------");
    }
}
