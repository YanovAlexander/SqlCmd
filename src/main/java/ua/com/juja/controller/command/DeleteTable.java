package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class DeleteTable implements Command {
    private View view;
    private DatabaseManager manager;
    private final static Integer TABLE_NAME = 1;

    public DeleteTable(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "deleteTable|tableName";
    }

    @Override
    public void process(InputString userInput) {
        userInput.validationParameters(format());
        String[] data = userInput.getParameters();
        String tableName = data[TABLE_NAME];

        view.write("Do you really want to delete table '" + tableName + " ? All data will delete ! Press Y/N ?");
        if (view.read().equalsIgnoreCase("Y")) {
            manager.deleteTable(tableName);
            view.write("Table " + tableName + " delete successful !");
        } else {
            view.write("The action is canceled!");
        }
    }
}
