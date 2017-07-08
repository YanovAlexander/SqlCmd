package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class DeleteDatabase implements Command {
    private View view;
    private DatabaseManager manager;

    public DeleteDatabase(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "deleteDatabase|databaseName";
    }

    @Override
    public void process(InputValidation command) {
        command.validationParameters(format());
        String[] data = command.getParameters();
        String databaseName = data[1];

        view.write("Do you really want to delete database '" + databaseName + "' ? All data will delete ! " +
                "If you sure press Y/N ?");
        if (view.read().equalsIgnoreCase("Y")){
            manager.deleteDatabase(databaseName);
            view.write("Database " + databaseName + " delete successful !");
        }
        view.write("Action is Cancelled !");
    }
}
