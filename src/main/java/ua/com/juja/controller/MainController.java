package ua.com.juja.controller;

import ua.com.juja.controller.command.*;
import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController {

    private List<Command> commands;
    private View view;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new ArrayList<>(Arrays.asList(
                new Connect(view, manager),
                new Help(view),
                new Exit(view),
                new IsConnected(view, manager),
                new Find(view, manager),
                new Tables(view, manager),
                new Clear(view, manager),
                new InsertEntry(view, manager),
                new UpdateEntry(view, manager),
                new CreateDatabase(view, manager),
                new CreateTable(view, manager),
                new DatabaseList(view, manager),
                new DeleteTable(view, manager),
                new DeleteDatabase(view, manager),
                new DisconnectFromDB(view, manager),
                new UnsupportedCommand(view)
    ));
}

    public void run() {
        view.write("Welcome!");
        view.write("Enter the database name, username, and password of the user in format : " +
                "connect|database|username|password  or use 'help' to list all commands");

        try {
           doCommand();
        } catch (ExitException e) {
            /*NOP*/
        }
    }

    private void doCommand() {
        while (true) {
            InputString entry = new InputString(view.read());

            for (Command command : commands) {
                try {
                    if (command.canProcess(entry)) {
                        command.process(entry);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException) {
                        throw e;
                    }
                    printError(e);
                    break;
                }
            }
            view.write("Type command (or use 'help' to list all commands):");
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + cause.getMessage();
        }
        view.write("Error! Because of: " + message);
        view.write("Please try again.");
    }
}
