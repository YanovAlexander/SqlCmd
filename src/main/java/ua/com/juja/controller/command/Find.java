package ua.com.juja.controller.command;

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
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        doFind(command);
    }

    private void doFind(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException("Format of the command " +
                    "'find|tableName', but indicated : " + command);
        }
        String tableName = data[1];
        Set<String> tableColumns = manager.getTableColumns(tableName);
        List<DataSet> tableData = manager.getTableData(tableName);
        TableFormat format = new TableFormat(tableColumns, tableData);
        view.write(format.getTableString());
    }
}
