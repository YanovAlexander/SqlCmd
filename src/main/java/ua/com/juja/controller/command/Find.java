package ua.com.juja.controller.command;

import ua.com.juja.model.DataSet;
import ua.com.juja.model.DatabaseManager;
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
            throw new IllegalArgumentException("Формат комманды 'find|tableName', а ты ввел : " + command);
        }
        String tableName = data[1];

        List<DataSet> tableData = manager.getTableData(tableName);
        Set<String> tableColumns = manager.getTableColumns(tableName);
        printHeader(tableColumns);
        prindTable(tableData);
    }

    private void prindTable(List<DataSet> tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
    }

    private void printHeader(Set<String> tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("------------------------");
        view.write(result);
        view.write("------------------------");

    }

    private void printRow(DataSet row) {
        List<Object> values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }

        view.write(result);
    }

}
