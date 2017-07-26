package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;


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
    public void process(InputString userInput) {
        userInput.validateParameters(format());
        String[] data = userInput.getParameters();
        String tableName = data[TABLE_NAME];

        manager.clear(tableName);
        view.write(String.format("Table %s was successfully cleaned.", tableName));
    }
}
