package com.sivkovych.everythingproject._util.displayname;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public final class ForCodeWithTestName extends DisplayNameGenerator.Standard {
    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        var displayName = "";
        var forClass = testClass.getAnnotation(ForClass.class);
        var forGlobalMethod = testClass.getAnnotation(ForMethod.class);
        if (forClass == null) {
            var superclass = testClass.getSuperclass();
            if (superclass != null) {
                var fromSuperclass = superclass.getAnnotation(ForClass.class);
                displayName = fromSuperclass == null
                        ? testClass.getSimpleName()
                        : fromSuperclass.value()
                                .getSimpleName();
            }
        } else {
            var simpleName = forClass.value()
                    .getSimpleName();
            displayName = forGlobalMethod == null
                    ? simpleName
                    : simpleName + "#" + forGlobalMethod.value();
        }
        return displayName;
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        var methodName = testMethod.getName();
        var forGlobalMethod = testClass.getAnnotation(ForMethod.class);
        if (forGlobalMethod != null) {
            return methodName;
        }
        var forMethod = testMethod.getAnnotation(ForMethod.class);
        return forMethod == null
                ? methodName
                : forMethod.value() + ", " + methodName;
    }
}
