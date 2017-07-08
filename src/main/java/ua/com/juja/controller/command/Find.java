package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputValidation;
import ua.com.juja.model.DataSet;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.TableFormat;
import ua.com.juja.view.View;

import java.util.List;
import java.util.Set;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class Find implements Command {

    private View view;
    private DatabaseManager manager;

    public Find(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "find|tableName";
    }

    @Override
    public void process(InputValidation command) {
        command.validationParameters(format());
        String[] data = command.getParameters();
        String tableName = data[1];

        Set<String> tableColumns = manager.getTableColumns(tableName);
        List<DataSet> tableData = manager.getTableData(tableName);
        TableFormat format = new TableFormat(tableColumns, tableData);
        view.write(format.getTableString());
    }
}
