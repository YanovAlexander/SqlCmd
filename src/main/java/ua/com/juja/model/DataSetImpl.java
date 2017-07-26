package ua.com.juja.model;

import java.util.*;

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
    public String toString() {
        return "{" +
                "names = " + getNames().toString() + ", " +
                "values = " + getValues().toString() + ", " +
                "}";
    }
}
