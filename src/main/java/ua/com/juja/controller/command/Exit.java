package main.java.ua.com.juja.controller.command;

import main.java.ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class Exit implements Command {

    private View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) {
        view.write("Good Bye !");
        throw new ExitException();
    }
}
