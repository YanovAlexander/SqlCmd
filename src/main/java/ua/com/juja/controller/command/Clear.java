package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class Clear implements Command {
    private View view;
    private DatabaseManager manager;
    private final static Integer TABLE_NAME = 1;

    public Clear(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "clear|table";
    }

    @Override
    public void process(InputValidation command) {
        command.validationParameters(format());
        String[] data = command.getParameters();
        String tableName = data[TABLE_NAME];

        manager.clear(tableName);
        view.write(String.format("Table %s was successfully cleaned.", tableName));
    }
}
