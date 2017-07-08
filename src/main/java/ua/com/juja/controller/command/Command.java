package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputValidation;

/**
 * Created by Alexandero on 14.06.2017.
 */
public interface Command {

    void process(InputValidation command);

    String format();

    default boolean canProcess(InputValidation command){
        String[] splitFormat = format().split("\\|");
        String[] parameters = command.getParameters();
        return parameters[0].equals(splitFormat[0]);
    }
}
