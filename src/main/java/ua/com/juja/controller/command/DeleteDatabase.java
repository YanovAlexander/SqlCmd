package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class DeleteDatabase implements Command {
    private View view;
    private DatabaseManager manager;
    private final static Integer DATABASE_NAME = 1;

    public DeleteDatabase(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "deleteDatabase|databaseName";
    }

    @Override
    public void process(InputString userInput) {
        userInput.validationParameters(format());
        String[] data = userInput.getParameters();
        String databaseName = data[DATABASE_NAME];

        view.write("Do you really want to delete database '" + databaseName + "'? All data will delete! " +
                "If you sure press Y/N?");
        if (view.read().equalsIgnoreCase("Y")) {
            if (databaseName.equals(manager.getDatabaseName())){
                view.write("You cant delete database to which already connected!");
                return;
            }
            manager.deleteDatabase(databaseName);
            view.write("Database " + databaseName + " delete successful!");
        } else {
            view.write("Action is Cancelled!");
        }
    }
}
