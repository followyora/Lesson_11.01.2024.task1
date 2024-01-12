package org.example;
/*Create classes Human, Robot, Animal in the "subject" package.
 Create your own annotation @Lifeforms and apply it to the Human and Animal classes.
 Then, write a class ReflectionHelper that uses the Reflection API to retrieve all classes marked with the @Lifeforms annotation based on the package name.
  In the main method, print the names of all the found classes. For each class, create an object using reflection and the default constructor.

  Add a class BioRobot that extends the Robot class. Specify the @Lifeforms annotation above the BioRobot class.
  Check that the new class is identified by the ReflectionHelper.
 */

import org.example.reflect.Lifeforms;
import org.example.reflect.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Class<?>> listClasses = ReflectionHelper.findAnnotations("org.example.subject", Lifeforms.class);
        System.out.println(createInstances(listClasses));

    }

gi
    private static List<?> createInstances(List<Class<?>> listClasses) {
        return listClasses.stream()
                .map(c -> {
                    try {
                        return c.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                             IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }
}