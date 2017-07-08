package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class IsConnected implements Command {
    private View view;
    private DatabaseManager manager;

    public IsConnected(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(InputValidation command) {
        return !manager.isConnected();
    }

    @Override
    public String format() {
        return null;
    }

    @Override
    public void process(InputValidation command) {
        view.write(String.format("You can not use the command '%s', " +
                "while not connect with the command connect|database|username|password", command));
    }
}
