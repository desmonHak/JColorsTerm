package io.github.desmonhak.Annotations;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class HookDebugExecutorTest {

    @Test
    void executeHookDebugMethods() throws InvocationTargetException, IllegalAccessException {
        ClassTestsAnotation class_ = new ClassTestsAnotation();

        HookDebugExecutor.executeHookDebugMethods(class_);

    }

}