package com.titarenko.di;

import com.titarenko.di.annotation.Brick;
import com.titarenko.di.annotation.InsertPlease;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Summer {

    private final static String SRC_PATH = "src/main/java/";
    private static Map<Class<?>, Object> bricks = new HashMap<>();

    /**
     * Scanning 'src' path and creating Brick instances of this package
     *
     * @param src - path to .java files, starts with "src/main/java/"
     */
    public static void go(String src) throws IOException, ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Set<Path> srcPaths = Files.walk(Paths.get(src))
                .filter(Files::isRegularFile)
                .collect(Collectors.toSet());

        for (Path path : srcPaths) {
            String packagePath = path.toString()
                    .substring(SRC_PATH.length(), path.toString().lastIndexOf('.'))
                    .replaceAll("\\\\", ".");
            if (Class.forName(packagePath).isAnnotationPresent(Brick.class)) {
                Object o = Class.forName(packagePath).getConstructor().newInstance();
                bricks.put(o.getClass(), o);
            }
        }

        for (Map.Entry<Class<?>, Object> entry : bricks.entrySet()) {
            for (Field field : entry.getKey().getDeclaredFields()) {
                if (field.isAnnotationPresent(InsertPlease.class)) {
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    field.set(entry.getValue(), bricks.get(type));
                }
            }
        }
    }
}
