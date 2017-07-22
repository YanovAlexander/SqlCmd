package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class CreateDatabase implements Command {
    private View view;
    private DatabaseManager manager;
    private final static Integer DATABASE_NAME = 1;

    public CreateDatabase(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "createDatabase|databaseName";
    }

    @Override
    public void process(InputString userInput) {
        userInput.validationParameters(format());
        String[] data = userInput.getParameters();
        String databaseName = data[DATABASE_NAME];

        manager.createDatabase(databaseName);
        view.write("Database with name "+ databaseName + " created successful !" );
    }
}
