package ua.com.juja.controller.command;

import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class DeleteTable implements Command {
    private View view;
    private DatabaseManager manager;

    public DeleteTable(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }


    @Override
    public boolean canProcess(String command) {
        return command.startsWith("deleteTable|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException("Format of the command 'clear|tableName', but you type : " + command);
        }
        view.write("Do you really want to delete table '" + data[1] + " ? All data will delete ! Press Y/N ?");
        if (view.read().equalsIgnoreCase("Y")){
            manager.deleteTable(data[1]);
            view.write("Table " + data[1] + " delete successful !");
        }
        view.write("The action is canceled!");
    }
}
