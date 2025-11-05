package io.github.desmonhak.Log;

import io.github.desmonhak.JColorsTerm;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Tipos de logs
 */
public enum TypeLog {
    /**
     * Log de tipo info
     */
    INFO("INFO", JColorsTerm.LIGHT_BLUE),

    /**
     * Log de tipo Warning
     */
    WARNING("WARNING", JColorsTerm.LIGHT_YELLOW),

    /**
     * Log de tipo error
     */
    ERROR("ERROR", JColorsTerm.LIGHT_RED);

    /**
     * Secuencia ANSI para el color
     */
    String color;

    /**
     * Mensaje de error
     */
    String type_error;

    /**
     * Contructor para crear un log
     * @param type_error tipo de error
     * @param color color del mensaje del error
     */
    TypeLog(String type_error, String color) {
        this.type_error = type_error;
        this.color = color;
    }

    /**
     * @return Obtener secuencia ANSI de color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color Permite cambiar el color ANSI del mensaje
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return obtener el tipo de error del que se trata
     */
    public String getType_error() {
        return type_error;
    }

    /**
     * Permite cambiar el tipo de error
     * @param type_error nuevo tipo de error
     */
    public void setType_error(String type_error) {
        this.type_error = type_error;
    }

    /**
     * @return devuelve el mensaje de error
     */
    @NotNull
    @Contract(pure = true)
    @Override
    public String toString() {
        return (color + type_error + JColorsTerm.RESET);
    }
}
