package ua.com.juja.controller.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class CreateDatabase implements Command {
    private View view;
    private DatabaseManager manager;

    public CreateDatabase(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }


    @Override
    public boolean canProcess(String command) {
        return command.startsWith("createDatabase|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException(String.format("There must be an even number of parameters in the format " +
                    "'createDatabasename|tableName',  but indicated " + command));
        }
        String databaseName = data[1];
        manager.createDatabase(databaseName);
        view.write("Database with name "+ databaseName + "created successful !" );
    }
}
