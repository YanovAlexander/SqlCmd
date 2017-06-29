package ua.com.juja.controller.command;

import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class UnsuportedCommand implements Command {
    private View view;

    public UnsuportedCommand(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write("Unsupported command :" + command);
    }
}
