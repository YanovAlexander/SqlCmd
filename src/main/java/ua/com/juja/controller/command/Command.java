package ua.com.juja.controller.command;

/**
 * Created by Alexandero on 14.06.2017.
 */
public interface Command {

    boolean canProcess(String command);

    void process(String command);
}
