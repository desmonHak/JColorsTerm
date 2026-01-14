package io.github.desmonhak.Annotations;

import io.github.desmonhak.JColorsTerm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HookDebugExecutor {

    public static void executeHookDebugMethods (Object obj) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        System.out.println("Clase: " + clazz);
        for (Method method : clazz.getDeclaredMethods()) {
            HookDebug review = method.getAnnotation(HookDebug.class);
            if (review != null) {
                // Código que deseas ejecutar por defecto antes de invocar el método
                System.out.println(
                        String.format("%s[%sINIT%s] CALL Method before: %s%s%s %s",
                                JColorsTerm.LIGHT_WHITE, JColorsTerm.LIGHT_BLUE, JColorsTerm.LIGHT_WHITE,
                                JColorsTerm.LIGHT_MAGENTA, method, JColorsTerm.RESET,
                                (review.msg().equals("") ? "" : String.format("msg: %s", review.msg()))
                        )
                );

                /**
                 * El metodo setAccessible(true) en Java se usa para establecer el indicador de accesibilidad de un
                 * objeto reflejado, permitiendo que se supriman las comprobaciones normales de control de acceso del
                 * lenguaje Java cuando se utilice ese objeto. Esto significa que si se llama a
                 * method.setAccessible(true) sobre un metodo, campo o constructor privado o con acceso restringido,
                 * se podrá acceder o invocar ese miembro reflejado incluso si normalmente su acceso estaría
                 * prohibido por los modificadores de acceso (como private o protected).
                 *
                 * Este metodo es util en situaciones donde una clase (C) quiere acceder a un miembro
                 * (metodo, campo, constructor) declarado en otra clase (D) y hacerlo aunque las reglas de
                 * acceso normales de Java impidan dicho acceso, por ejemplo, para pruebas, depuración o
                 * manipulación avanzada.
                 */
                Object result = null;
                if (review.run()) {
                    /**
                     * Los metodos que se queiren ejecutar, no deben recibir parametros, o se generar error
                     */
                    method.setAccessible(true);
                    result = method.invoke(obj);
                }

                // Código que deseas ejecutar por defecto después de invocar el método
                System.out.println(
                    String.format("%s[%sEND%s] CALL Method after %s%s%s -> return %s\n",
                        JColorsTerm.LIGHT_WHITE, JColorsTerm.LIGHT_CYAN, JColorsTerm.LIGHT_WHITE,
                            JColorsTerm.LIGHT_MAGENTA, method, JColorsTerm.RESET, result
                    )
                );
            }
        }
    }
}
