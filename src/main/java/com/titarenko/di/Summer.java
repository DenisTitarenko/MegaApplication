package com.titarenko.di;

import com.titarenko.di.annotation.Brick;
import com.titarenko.di.annotation.InsertPlease;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Summer {

    private final static String SRC_PATH = "src/main/java/";
    private static final String SEPARATOR = File.separator;
    private static Map<Class<?>, Object> bricks = new HashMap<>();

    /**
     * Scanning 'src' path and creating Brick instances of this package
     *
     * @param src - path to .java files, starts with "src/main/java/"
     */
    public static void go(String src){
        try {
        Set<Path> srcPaths = Files.walk(Paths.get(src))
                .filter(Files::isRegularFile)
                .collect(Collectors.toSet());

        for (Path path : srcPaths) {
            String packagePath = path.toString()
                    .substring(SRC_PATH.length(), path.toString().lastIndexOf('.'))
                    .replaceAll(SEPARATOR, ".");
            if (Class.forName(packagePath).isAnnotationPresent(Brick.class)) {
                Object o = Class.forName(packagePath).getConstructor().newInstance();   // phase 1
                bricks.put(o.getClass(), o);
            }
        }

        for (Map.Entry<Class<?>, Object> entry : bricks.entrySet()) {
            for (Field field : entry.getKey().getDeclaredFields()) {
                if (field.isAnnotationPresent(InsertPlease.class)) {
                    field.setAccessible(true);
                    field.set(entry.getValue(), getInstance(field));                    // phase 2
                }
            }
        }
        runInit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getInstance(Field field) {
        return field.getType().isInterface() ? bricks.get(field.getAnnotation(InsertPlease.class).what())
                : bricks.get(field.getType());
    }

    private static void runInit() throws IllegalAccessException, InvocationTargetException {
        for (Map.Entry<Class<?>, Object> entry : bricks.entrySet()) {
            for (Method method : entry.getKey().getDeclaredMethods()) {
                if ("init".equals(method.getName())) {
                    method.invoke(entry.getValue());                                    // phase 3
                }
            }
        }
    }
}
