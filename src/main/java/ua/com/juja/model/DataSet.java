package main.java.ua.com.juja.model;

import java.util.List;
import java.util.Set;

/**
 * Created by Alexandero on 28.06.2017.
 */
public interface DataSet {
    void put(String name, Object value);

    List<Object> getValues();

    Set<String> getNames();

    Object get(String name);

    void updateFrom(DataSet newValue);
}
