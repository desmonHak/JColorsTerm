package io.github.desmonhak;


import java.awt.*;

public class JColorsTerm {
    public String string_color;

    public static String RESET      = "\033[0;0m";

    public static String BLACK      = "\033[0;30m";
    public static String RED        = "\033[0;31m";
    public static String GREEN      = "\033[0;32m";
    public static String YELLOW     = "\033[0;33m";
    public static String BLUE       = "\033[0;34m";
    public static String MAGENTA    = "\033[0;35m";
    public static String CYAN       = "\033[0;36m";
    public static String WHITE      = "\033[0;37m";

    public static String LIGHT_BLACK      = "\033[0;90m";
    public static String LIGHT_RED        = "\033[0;91m";
    public static String LIGHT_GREEN      = "\033[0;92m";
    public static String LIGHT_YELLOW     = "\033[0;93m";
    public static String LIGHT_BLUE       = "\033[0;94m";
    public static String LIGHT_MAGENTA    = "\033[0;95m";
    public static String LIGHT_CYAN       = "\033[0;96m";
    public static String LIGHT_WHITE      = "\033[0;97m";

    public static String STYLE_BOLDED       = "\033[1m";
    public static String STYLE_DARKENED     = "\033[2m";
    public static String STYLE_ITALICS      = "\033[3m";
    public static String STYLE_UNDERLINED   = "\033[4m";
    public static String STYLE_BLIKING      = "\033[5m";

    public static String CLEAR_DISPLAY      = "\033[3J\033[H\033[2J";
    public static String CLEAR_LINE         = "\033[K";
    public static String HIDDEN_SLIDER      = "\033[?25l";
    public static String SHOW_SLIDER        = "\033[?25h";
    public static String SET_MODE_SLIDER    = "\033[?12l";

    public static String UP(int number) {
        return String.format("\033[%dA", number);
    }

    public static String DOWN(int number) {
        return String.format("\033[%dB", number);
    }

    public static String FORWARD(int number) {
        return String.format("\033[%dC", number);
    }

    public static String BACK(int number) {
        return String.format("\033[%dD", number);
    }

    /**
     * Mueve el cursor a una posición específica.
     * @param num_line Línea a la que mover el cursor.
     * @param num_col numero de columna a la que desplazar el cursor
     * @return secuencia ANSI
     */
    public static String POS(int num_line, int num_col) {
        return String.format("\033[%d;%dH", num_line, num_col);
    }

    public static String SET_TITLE(String title) {
        return String.format("\033]2;%s\007", title);
    }

    public static String SET_SIZE_SLIDER(int size) {
        return String.format("\033[?%dc", size);
    }

    public JColorsTerm (Color colorFG) {
        this.string_color = String.format("\033[38;2;%d;%d;%dm", colorFG.getRed(), colorFG.getGreen(), colorFG.getBlue());
    }
    public JColorsTerm (Color colorFG, String text) {
        this.string_color = String.format("\033[38;2;%d;%d;%dm%s", colorFG.getRed(), colorFG.getGreen(), colorFG.getBlue(), text) + RESET;
    }

    public JColorsTerm (Color colorFG, Color colorBG, String text) {
        this.string_color = String.format("\033[38;2;%d;%d;%dm\033[48;2;%d;%d;%dm%s",
                colorFG.getRed(), colorFG.getGreen(), colorFG.getBlue(),
                colorBG.getRed(), colorBG.getGreen(), colorBG.getBlue(),
                text) + RESET;
    }

    public JColorsTerm (Color colorFG, Color colorBG) {
        this.string_color = String.format("\033[38;2;%d;%d;%dm\033[48;2;%d;%d;%dm",
                colorFG.getRed(), colorFG.getGreen(), colorFG.getBlue(),
                colorBG.getRed(), colorBG.getGreen(), colorBG.getBlue());
    }

    public JColorsTerm (int r, int g, int b) {
        this.string_color = String.format("\033[38;2;%d;%d;%dm", r,g,b);
    }

    public JColorsTerm (int r, int g, int b, String text) {
        // \033[38;2;<r>;<g>;<b>m     #Select RGB foreground color
        // \033[48;2;<r>;<g>;<b>m     #Select RGB background color
        this.string_color = String.format("\033[38;2;%d;%d;%dm%s", r,g,b, text) + RESET;
    }

    public static String ColorTerm255(int val_255) {
        return String.format("\033[48;5;%dm", val_255);
    }

    /**
     * Permite imprimmir un texto usando colorres del arcoiris.
     * fuente: https://krazydad.com/tutorials/makecolors.php
     *
     * @param str texto a imprimir
     * @param phase fase de color, recomiendo usar el valo 4 para que empieze por azul
     */
    public static void printRaindbow(String str, int phase) {
        printRaindbow(str, phase, 2, 0, 4);
    }

    public static void printRaindbow(String str, int phase, int red_frequency, int green_frequency, int blue_frequency) {
        int center = 128;
        int width = 127;
        double frequency = Math.PI * 2/str.length();

        for (var i = 0; i < str.length(); ++i) {
            int red     = (int) (Math.sin(frequency * i + red_frequency + phase) * width + center);
            int green   = (int) (Math.sin(frequency * i + green_frequency + phase) * width + center);
            int blue    = (int) (Math.sin(frequency * i + blue_frequency + phase) * width + center);
            System.out.print(new JColorsTerm(red, green, blue) + String.valueOf(str.charAt(i)));
        }
        System.out.print(JColorsTerm.RESET);
    }

    @Override
    public String toString() {
        return string_color;
    }
}
