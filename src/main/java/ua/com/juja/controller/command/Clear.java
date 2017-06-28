package main.java.ua.com.juja.controller.command;

import main.java.ua.com.juja.model.DatabaseManager;
import main.java.ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class Clear implements Command {
    private View view;
    private DatabaseManager manager;

    public Clear(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException("Формат комманды 'clear|tableName', а ты ввел : " + command);
        }
        manager.clear(data[1]);

        view.write(String.format("Таблица %s была успешно очищена.", data[1] ));
    }
}
