package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DataSet;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.model.TableFormat;
import ua.com.juja.view.View;

import java.util.List;
import java.util.Set;

public class Find implements Command {

    private View view;
    private DatabaseManager manager;
    private final static Integer TABLE_NAME = 1;

    public Find(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "find|tableName";
    }

    @Override
    public void process(InputString userInput) {
        userInput.validateParameters(format());
        String[] data = userInput.getParameters();
        String tableName = data[TABLE_NAME];

        Set<String> tableColumns = manager.getTableColumns(tableName);
        List<DataSet> tableData = manager.getTableData(tableName);
        TableFormat format = new TableFormat(tableColumns, tableData);
        view.write(format.getTableString());
    }
}
