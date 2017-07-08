package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class DatabaseList implements Command {
    private View view;
    private DatabaseManager manager;


    public DatabaseList(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "databaseList";
    }

    @Override
    public void process(InputValidation command) {
        command.validationParameters(format());
        view.write("-------------------DATABASES----------------");
        for (String database : manager.databasesList()){
            view.write("- " + database);
        }
        view.write("--------------------------------------------");
    }
}
