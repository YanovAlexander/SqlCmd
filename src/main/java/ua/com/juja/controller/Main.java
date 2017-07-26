package ua.com.juja.controller;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.PostgresManager;
import ua.com.juja.view.Console;
import ua.com.juja.view.View;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO); //Disable log4j

        View view = new Console();
        DatabaseManager manager = new PostgresManager();

        MainController controller = new MainController(view, manager);
        controller.run();
    }
}
