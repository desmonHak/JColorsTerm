package io.github.desmonhak.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Creamos una anotacion para metodos (ElementType.METHOD),
 *
 * RetentionPolicy.RUNTIME:
 *  En Java, es una política de retención que especifica
 *  que una anotación de Java debe ser registrada en el
 *  archivo de clase por el compilador y retenida por la
 *  máquina virtual (MV) en tiempo de ejecución.
 *  Esto permite acceder a la anotación mediante reflexión
 *  una vez que la clase se ha cargado en memoria. Esto
 *  es fundamental para los frameworks que necesitan
 *  inspeccionar o modificar el comportamiento del código
 *  basándose en los metadatos de las anotaciones durante
 *  la ejecución.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface HookDebug {
    String msg() default "";
    boolean run() default false;
}
