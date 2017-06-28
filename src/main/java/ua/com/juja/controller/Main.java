package main.java.ua.com.juja.controller;

import main.java.ua.com.juja.model.DatabaseManager;
import main.java.ua.com.juja.model.JDBCDatabaseManager;
import main.java.ua.com.juja.view.Console;
import main.java.ua.com.juja.view.View;

/**
 * Created by Alexandero on 13.06.2017.
 */
public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();

        MainController controller = new MainController(view, manager);
        controller.run();
    }
}
