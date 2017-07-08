package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DataSet;
import ua.com.juja.model.DataSetImpl;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class InsertEntry implements Command {
    private View view;
    private DatabaseManager manager;

    public InsertEntry(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "insertEntry|tableName|column1|value1|column2|value2...columnN|valueN";
    }

    @Override
    public void process(InputValidation command) {
        command.validationPairs(format());
        String[] data = command.getParameters();
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

