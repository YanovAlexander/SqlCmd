package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;


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
        userInput.validateParameters(format());

        manager.disconnectFromDB();
        view.write("Disconnected");
    }
}
