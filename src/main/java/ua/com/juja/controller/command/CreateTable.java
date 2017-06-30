package ua.com.juja.controller.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class CreateTable implements Command {
    private View view;
    private DatabaseManager manager;

    public CreateTable(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("createTable|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException(String.format("There must be an even number of parameters in the format " +
                    "'createTable|tableName',  but indicated " + command));
        }
        String tableName = data[1];
        manager.createTable(tableName);
        view.write("Table with name " + tableName + " created successful !");
    }
}
