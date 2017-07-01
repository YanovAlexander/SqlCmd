package ua.com.juja.controller.command;

import ua.com.juja.model.DataSet;
import ua.com.juja.model.DataSetImpl;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class CreateQuery implements Command {
    private View view;
    private DatabaseManager manager;

    public CreateQuery(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("createQuery|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("There must be an even number of parameters in the format " +
                    "createQuery|tableName|column1|value1|column2|value2...columnN|valueN, but indicated : '%s'", command));
        }

        String tableName = data[1];
        DataSet dataSet = new DataSetImpl();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];

            dataSet.put(columnName, value);
        }

        manager.create(tableName, dataSet);
        view.write(String.format("%s was successfully created in table %s.", dataSet, tableName));
    }
}

