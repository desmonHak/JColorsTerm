package io.github.desmonhak.Log;

import io.github.desmonhak.JColorsTerm;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum TypeLog {
    INFO("INFO", JColorsTerm.LIGHT_BLUE),
    WARNING("WARNING", JColorsTerm.LIGHT_YELLOW),
    ERROR("ERROR", JColorsTerm.LIGHT_RED);

    String color;
    String type_error;

    TypeLog(String type_error, String color) {
        this.type_error = type_error;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType_error() {
        return type_error;
    }

    public void setType_error(String type_error) {
        this.type_error = type_error;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public String toString() {
        return (color + type_error + JColorsTerm.RESET);
    }
}
