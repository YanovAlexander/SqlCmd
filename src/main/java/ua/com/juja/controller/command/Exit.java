package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class Exit implements Command {

    private View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public String format() {
        return "exit";
    }

    @Override
    public void process(InputValidation command) {
        view.write("Good Bye !");
        throw new ExitException();
    }
}
