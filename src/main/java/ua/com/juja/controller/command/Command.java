package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;

/**
 * Created by Alexandero on 14.06.2017.
 */
public interface Command {

    void process(InputString userInput);

    String format();

    default boolean canProcess(InputString userInput){
        String[] splitFormat = format().split("\\|");
        String[] parameters = userInput.getParameters();
        return parameters[0].equals(splitFormat[0]);
    }
}
