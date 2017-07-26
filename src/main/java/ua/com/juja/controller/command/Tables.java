package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.util.Set;

public class Tables implements Command {

    private View view;
    private DatabaseManager manager;

    public Tables(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "tables";
    }

    @Override
    public void process(InputString userInput) {
        Set<String> tableNames = manager.getTableNames();
        view.write("-------------------TABLES-------------------");
        for (String message : tableNames ){
            view.write("- " + message);
        }
        view.write("--------------------------------------------");
    }
}
