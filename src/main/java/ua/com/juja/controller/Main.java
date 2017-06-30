package ua.com.juja.controller;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.JDBCDatabaseManager;
import ua.com.juja.view.Console;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 13.06.2017.
 */
public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF); //Disable log4j

        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();

        MainController controller = new MainController(view, manager);
        controller.run();
    }
}
