package ua.com.juja.model;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Set;

public class TableFormat {
    private Set<String> columns;
    private Table table;
    private List<DataSet> tableData;

    public TableFormat(Set<String> columns, List<DataSet> tableData) {
        this.columns = columns;
        this.tableData = tableData;
        table = new Table(columns.size(), BorderStyle.CLASSIC, ShownBorders.ALL);
    }

    public String getTableString() {
        build();
        return table.render();
    }

    private void build() {
        buildHeader();
        buildRows();
    }

    private void buildHeader() {
        columns.forEach(column -> table.addCell(column));
    }

    private void buildRows() {
        tableData.forEach(row -> row.getValues()
                .forEach(value -> table.addCell(value.toString())));
    }
}
