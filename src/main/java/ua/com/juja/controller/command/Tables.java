package main.java.ua.com.juja.controller.command;

import main.java.ua.com.juja.model.DatabaseManager;
import main.java.ua.com.juja.view.View;

import java.util.Set;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class Tables implements Command {

    private View view;
    private DatabaseManager manager;

    public Tables(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("tables");
    }

    @Override
    public void process(String command) {
        doTables();
    }

    private void doTables() {
        Set<String> tableNames = manager.getTableNames();

        String message = tableNames.toString();

        view.write(message);
    }
}
