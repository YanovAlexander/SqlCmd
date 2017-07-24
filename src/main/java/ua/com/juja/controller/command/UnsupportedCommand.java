package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class UnsupportedCommand implements Command {
    private View view;

    public UnsupportedCommand(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(InputString userInput) {
        return true;
    }

    @Override
    public String format() {
        return "";
    }

    @Override
    public void process(InputString userInput) {
        view.write("Unsupported command: " + userInput);
    }
}
