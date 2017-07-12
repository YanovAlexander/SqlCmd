package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class Connect implements Command {

    private DatabaseManager manager;
    private View view;
    private final static Integer DATABASE_NAME = 1;
    private final static Integer USER_NAME = 2;
    private final static Integer PASSWORD = 3;

    public Connect(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "connect|databaseName|userName|password";
    }

    @Override
    public void process(InputValidation command) {
        command.validationParameters(format());
        String[] data = command.getParameters();
        String databaseName = data[DATABASE_NAME];
        String userName = data[USER_NAME];
        String password = data[PASSWORD];

        manager.connect(databaseName, userName, password);
        view.write("Connected successful");
    }
}
