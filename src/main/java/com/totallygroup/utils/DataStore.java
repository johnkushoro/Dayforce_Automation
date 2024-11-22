package com.totallygroup.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class DataStore {

    private static DataStore instance;
    private final Map<String, Object> storedValues;

    private DataStore() {
        this.storedValues = new HashMap<>();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public void setValue(String key, Object value) {
        this.storedValues.put(key, value);
    }

    public Object getValue(String key) {
        return this.storedValues.get(key);
    }
}
