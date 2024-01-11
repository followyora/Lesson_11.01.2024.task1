package org.example.reflect;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReflectionHelper {
    private static final String CLASS_TYPE = ".class";

    private ReflectionHelper() {
    }

    public static List<Class<?>> findAnnotations(String packageName, Class<? extends Annotation> annotation) {
        List<Class<?>> result = new ArrayList<>();
        if (packageName == null || packageName.isEmpty()) return result;
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); // ClassLoader can find every files
        try {
            Iterator<URL> iterator = classLoader.getResources(path).asIterator();
            while (iterator.hasNext()) {
                File directory = new File(iterator.next().getFile());
                if (!directory.exists()) continue;
                String[] fileNames = directory.list();
                if (fileNames == null) continue;
                for (String file : fileNames) {
                    if (file.endsWith(CLASS_TYPE)) {
                        String className = packageName + "." + file.substring(0, file.length() - CLASS_TYPE.length());
                        try {
                            Class<?> clazz = Class.forName(className);
                            if(clazz.isAnnotationPresent(annotation)) result.add(clazz);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
