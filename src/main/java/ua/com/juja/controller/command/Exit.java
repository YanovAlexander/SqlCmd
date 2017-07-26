package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.view.View;

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
    public void process(InputString userInput) {
        view.write("Good Bye!");
        throw new ExitException();
    }
}
