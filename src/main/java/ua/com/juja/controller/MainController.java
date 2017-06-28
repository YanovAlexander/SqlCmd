package main.java.ua.com.juja.controller;

import main.java.ua.com.juja.controller.command.*;
import main.java.ua.com.juja.model.DatabaseManager;
import main.java.ua.com.juja.view.View;

/**
 * Created by Alexandero on 13.06.2017.
 */
public class MainController {

    private Command[] commands;
    private View view;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new Command[]{
                new Connect(view, manager),
                new Help(view),
                new Exit(view),
                new IsConnected(view, manager),
                new Find(view, manager),
                new Tables(view, manager),
                new Clear(view, manager),
                new Create(view, manager),
                new UnsuportedCommand(view)};
    }

    public void run() {
        view.write("Привет ползьватель !");
        view.write("Введи, пожалуйста имя базы, имя пользователя и пароль пользователя в таком формате : " +
                "connect|database|username|password");

        try {
            while (true) {
                String input = view.read();

                for (Command command : commands) {
                    try {
                        if (command.canProcess(input)) {
                            command.process(input);
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
                view.write("Введи команду (или help для помощи):");
            }
        } catch (ExitException e) {
            //do nothing
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + cause.getMessage();
        }
        view.write("Неудача! по причине: " + message);
        view.write("Повтори попытку.");
    }
}
