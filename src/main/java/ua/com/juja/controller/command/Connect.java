package ua.com.juja.controller.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class Connect implements Command {

    private static String COMMAND_SAMPLE = "connect|postgres|postgres|pass";

    private DatabaseManager manager;
    private View view;

    public Connect(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");

        if (data.length != count()) {
            throw new IllegalArgumentException(
                    String.format("Invalid number of parameters separeted be a sign \"|\" ," +
                            "%s is required, but indicated : %s", count(), data.length));
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        manager.connect(databaseName, userName, password);

        view.write("Connected successful");
    }

    private int count() {
        return COMMAND_SAMPLE.split("\\|").length;
    }


}
