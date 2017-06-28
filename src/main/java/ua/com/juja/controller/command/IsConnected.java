package main.java.ua.com.juja.controller.command;

import main.java.ua.com.juja.model.DatabaseManager;
import main.java.ua.com.juja.view.View;

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
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process(String command) {
        view.write(String.format("Вы не можете пользоваться командой '%s', " +
                "пока не подключитесь с помощью команды connect|database|username|password", command));
    }
}
