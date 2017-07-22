package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 10.07.2017.
 */
public class DisconnectFromDB implements Command{

    private DatabaseManager manager;
    private View view;

    public DisconnectFromDB(View view, DatabaseManager manager) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public String format() {
        return "disconnect";
    }

    @Override
    public void process(InputString userInput) {
        userInput.validationParameters(format());

        manager.disconnectFromDB();
        view.write("Disconnected");
    }
}
