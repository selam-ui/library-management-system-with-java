package com.librarymanagementsystem.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logger {
    private static java.util.Map<String, org.slf4j.Logger> loggers = new java.util.HashMap<>();

    public static org.slf4j.Logger getLogger(Class<?> clazz) {
        String className = clazz.getName();
        if (!loggers.containsKey(className)) {
            loggers.put(className, LoggerFactory.getLogger(clazz));
        }
        return loggers.get(className);
    }

    public static org.slf4j.Logger getLogger(String name) {
        if (!loggers.containsKey(name)) {
            loggers.put(name, LoggerFactory.getLogger(name));
        }
        return loggers.get(name);
    }
}
