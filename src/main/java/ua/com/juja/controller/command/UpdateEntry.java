package ua.com.juja.controller.command;


import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DataSet;
import ua.com.juja.model.DataSetImpl;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 10.07.2017.
 */
public class UpdateEntry implements Command {
    private DatabaseManager manager;
    private View view;

    public UpdateEntry(View view, DatabaseManager manager){
        this.manager = manager;
        this.view = view;
    }

    @Override
    public String format() {
        return "updateEntry|tableName|ID";
    }

    @Override
    public void process(InputValidation command) {
        command.validationParameters(format());
        String[] data = command.getParameters();

        view.write("Enter data of entry in format 'column1|value1|column2|value2|....|columnN|valueN':");
        InputValidation dataEntry = new InputValidation(view.read());
        dataEntry.validationPairs("column1|value1|column2|value2|...|columnN|valueN");
        String[] dataEntryParameters = dataEntry.getParameters();


        DataSet dataSet = new DataSetImpl();
        for (int index = 0; index < (dataEntryParameters.length / 2); index++){
            String columnName = dataEntryParameters[index * 2];
            String value = dataEntryParameters[index * 2 + 1];

            dataSet.put(columnName, value);
        }
        String userID = data[2];
        int id = Integer.parseInt(userID);

        String tableName = data[1];
        manager.update(tableName, id, dataSet);
        view.write("Update is successful !");
    }

}
