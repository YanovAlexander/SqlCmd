package ua.com.juja.model;

import java.util.*;

/**
 * Created by Alexandero on 02.06.2017.
 */
public class DataSetImpl implements DataSet {

    private Map<String, Object> data = new LinkedHashMap<>();

    @Override
    public void put(String name, Object value) {
        data.put(name, value);
    }

    @Override
    public List<Object> getValues() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Set<String> getNames() {
       return data.keySet();
    }

    @Override
    public Object get(String name) {
        return data.get(name);
    }

    @Override
    public void updateFrom(DataSet newValue) {
        Set<String> columns = newValue.getNames();
        for (String names : columns) {
            Object data = newValue.get(names);
            this.put(names, data);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "names = " + getNames().toString() + ", " +
                "values = " + getValues().toString() + ", " +
                "}";
    }
}
