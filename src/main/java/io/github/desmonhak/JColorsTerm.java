package io.github.desmonhak;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class JColorsTerm {
    public String string_color;

    /**
     * Permite resetear el color y estilo de la letra
     */
    public static String RESET      = "\033[0;0m";

    /**
     * Color negro para la letra
     */
    public static String BLACK      = "\033[0;30m";

    /**
     * Color rojo para la letra
     */
    public static String RED        = "\033[0;31m";

    /**
     * Color verde para la letra
     */
    public static String GREEN      = "\033[0;32m";

    /**
     * Color amarillo para la letra
     */
    public static String YELLOW     = "\033[0;33m";

    /**
     * Color azul para la letra
     */
    public static String BLUE       = "\033[0;34m";

    /**
     * Color magenta para la letra
     */
    public static String MAGENTA    = "\033[0;35m";

    /**
     * Color cyan para la letra
     */
    public static String CYAN       = "\033[0;36m";

    /**
     * Color blanco para la letra
     */
    public static String WHITE      = "\033[0;37m";

    /**
     * Color negro claro para la letra
     */
    public static String LIGHT_BLACK      = "\033[0;90m";

    /**
     * Color rojo claro para letra
     */
    public static String LIGHT_RED        = "\033[0;91m";

    /**
     * Color verde claro para letra
     */
    public static String LIGHT_GREEN      = "\033[0;92m";

    /**
     * Color amarillo claro para la letra
     */
    public static String LIGHT_YELLOW     = "\033[0;93m";

    /**
     * Color azul claro para la letra
     */
    public static String LIGHT_BLUE       = "\033[0;94m";

    /**
     * Color magenta claro para la letra
     */
    public static String LIGHT_MAGENTA    = "\033[0;95m";

    /**
     * Color cyan claro para la letra
     */
    public static String LIGHT_CYAN       = "\033[0;96m";

    /**
     * Color blanco claro para la letra
     */
    public static String LIGHT_WHITE      = "\033[0;97m";

    /**
     * Aplica negrita al texto mostrado en la consola.
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.println(System.out.println(JColorsTerm.STYLE_BOLDED + "Texto en negrita");
     * }</pre>
     */
    public static final String STYLE_BOLDED = "\033[1m";

    /**
     * Aplica un tono atenuado u oscuro al texto (también llamado “dim”).
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.println(System.out.println(JColorsTerm.STYLE_DARKENED + "Texto atenuado");
     * }</pre>
     */
    public static final String STYLE_DARKENED = "\033[2m";

    /**
     * Aplica cursiva al texto.
     * Nota: no todas las terminales soportan este estilo.
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.println(JColorsTerm.STYLE_ITALICS + "Texto en cursiva");
     * }</pre>
     */
    public static final String STYLE_ITALICS = "\033[3m";

    /**
     * Aplica subrayado al texto.
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.println(JColorsTerm.STYLE_UNDERLINED + "Texto subrayado");
     * }</pre>
     */
    public static final String STYLE_UNDERLINED = "\033[4m";

    /**
     * Hace que el texto parpadee (blinking).
     * Nota: muchas terminales modernas deshabilitan esta característica por defecto.
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.println(JColorsTerm.STYLE_BLIKING + "Texto parpadeante");
     * }</pre>
     */
    public static final String STYLE_BLIKING = "\033[5m";

    /**
     * Limpia completamente la pantalla y mueve el cursor a la esquina superior izquierda.
     * Es una combinación de comandos que borran el búfer de desplazamiento y reinician la vista.
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.print(JColorsTerm.CLEAR_DISPLAY);
     * }</pre>
     */
    public static final String CLEAR_DISPLAY = "\033[3J\033[H\033[2J";

    /**
     * Limpia la línea actual desde la posición del cursor hasta el final de la línea.
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.print(JColorsTerm.CLEAR_LINE);
     * }</pre>
     */
    public static final String CLEAR_LINE = "\033[K";

    /**
     * Oculta el cursor en la terminal.
     * Útil en interfaces o animaciones de consola.
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.print(JColorsTerm.HIDDEN_SLIDER);
     * }</pre>
     */
    public static final String HIDDEN_SLIDER = "\033[?25l";

    /**
     * Muestra el cursor si estaba oculto previamente.
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.print(JColorsTerm.SHOW_SLIDER);
     * }</pre>
     */
    public static final String SHOW_SLIDER = "\033[?25h";

    /**
     * Desactiva el modo de parpadeo del cursor (cursor estático).
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.print(JColorsTerm.SET_MODE_SLIDER);
     * }</pre>
     */
    public static final String SET_MODE_SLIDER = "\033[?12l";

    /**
     * Mueve el cursor hacia arriba un número específico de líneas.
     *
     * @param number número de líneas que se desea mover hacia arriba (debe ser ≥ 1)
     * @return la secuencia ANSI correspondiente (por ejemplo, "\033[5A" para subir 5 líneas)
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.print(JColorsTerm.UP(3)); // Mueve el cursor 3 líneas hacia arriba
     * }</pre>
     */
    @NotNull
    @Contract(pure = true)
    public static String UP(int number) {
        return String.format("\033[%dA", number);
    }

    /**
     * Mueve el cursor hacia abajo un número específico de líneas.
     *
     * @param number número de líneas que se desea mover hacia abajo (debe ser ≥ 1)
     * @return la secuencia ANSI correspondiente (por ejemplo, "\033[2B" para bajar 2 líneas)
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.print(JColorsTerm.DOWN(2)); // Mueve el cursor 2 líneas hacia abajo
     * }</pre>
     */
    @NotNull
    @Contract(pure = true)
    public static String DOWN(int number) {
        return String.format("\033[%dB", number);
    }

    /**
     * Mueve el cursor hacia adelante (derecha) un número específico de columnas.
     *
     * @param number número de posiciones a mover hacia la derecha (debe ser ≥ 1)
     * @return la secuencia ANSI correspondiente (por ejemplo, "\033[10C" para mover 10 columnas)
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.print(JColorsTerm.FORWARD(10)); // Desplaza el cursor 10 espacios a la derecha
     * }</pre>
     */
    @NotNull
    @Contract(pure = true)
    public static String FORWARD(int number) {
        return String.format("\033[%dC", number);
    }

    /**
     * Mueve el cursor hacia atrás (izquierda) un número específico de columnas.
     *
     * @param number número de posiciones a mover hacia la izquierda (debe ser ≥ 1)
     * @return la secuencia ANSI correspondiente (por ejemplo, "\033[5D" para mover 5 columnas atrás)
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.print(JColorsTerm.BACK(5)); // Desplaza el cursor 5 espacios a la izquierda
     * }</pre>
     */
    @NotNull
    @Contract(pure = true)
    public static String BACK(int number) {
        return String.format("\033[%dD", number);
    }

    /**
     * Mueve el cursor a una posición específica.
     * @param num_line Línea a la que mover el cursor.
     * @param num_col numero de columna a la que desplazar el cursor
     * @return secuencia ANSI
     */
    @NotNull
    @Contract(pure = true)
    public static String POS(int num_line, int num_col) {
        return String.format("\033[%d;%dH", num_line, num_col);
    }

    @NotNull
    @Contract(pure = true)
    public static String SET_TITLE(String title) {
        return String.format("\033]2;%s\007", title);
    }

    @NotNull
    @Contract(pure = true)
    public static String SET_SIZE_SLIDER(int size) {
        return String.format("\033[?%dc", size);
    }

    public JColorsTerm (@NotNull Color colorFG) {
        this.string_color = String.format("\033[38;2;%d;%d;%dm", colorFG.getRed(), colorFG.getGreen(), colorFG.getBlue());
    }
    public JColorsTerm (@NotNull Color colorFG, String text) {
        this.string_color = String.format("\033[38;2;%d;%d;%dm%s", colorFG.getRed(), colorFG.getGreen(), colorFG.getBlue(), text) + RESET;
    }

    public JColorsTerm (@NotNull Color colorFG, @NotNull Color colorBG, String text) {
        this.string_color = String.format("\033[38;2;%d;%d;%dm\033[48;2;%d;%d;%dm%s",
                colorFG.getRed(), colorFG.getGreen(), colorFG.getBlue(),
                colorBG.getRed(), colorBG.getGreen(), colorBG.getBlue(),
                text) + RESET;
    }

    public JColorsTerm (@NotNull Color colorFG, @NotNull Color colorBG) {
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

    /**
     * Establece un **color de fondo** usando el modo extendido ANSI de **256 colores**.
     *
     * <p>El parámetro {@code val_255} puede variar entre 0 y 255, representando uno de los
     * colores definidos en la paleta estándar de 256 colores de la terminal.</p>
     *
     * <p>Esta secuencia usa el formato: <b>"\033[48;5;{n}m"</b>, donde:</p>
     * <ul>
     *   <li><b>48</b> → indica que se cambia el <i>fondo</i> (para el texto, se usa 38)</li>
     *   <li><b>5</b>  → especifica que se usará el modo de 256 colores</li>
     *   <li><b>{n}</b> → valor del color entre 0 y 255</li>
     * </ul>
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.println(ColorTerm255_background(196) + "Texto con fondo rojo brillante" + "\033[0m");
     * }</pre>
     *
     * @param val_255 valor del color de fondo (0–255)
     * @return la secuencia ANSI que cambia el color de fondo
     */
    @NotNull
    @Contract(pure = true)
    public static String ColorTerm255_background(int val_255) {
        return String.format("\033[48;5;%dm", val_255);
    }

    /**
     * Establece un **color de fondo** usando el modo extendido ANSI de **256 colores**.
     *
     * <p>El parámetro {@code val_255} puede variar entre 0 y 255, representando uno de los
     * colores definidos en la paleta estándar de 256 colores de la terminal.</p>
     *
     * <p>Esta secuencia usa el formato: <b>"\033[48;5;{n}m"</b>, donde:</p>
     * <ul>
     *   <li><b>48</b> → indica que se cambia el <i>fondo</i> (para el texto, se usa 38)</li>
     *   <li><b>5</b>  → especifica que se usará el modo de 256 colores</li>
     *   <li><b>{n}</b> → valor del color entre 0 y 255</li>
     * </ul>
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * System.out.println(ColorTerm255_foreground(196) + "Texto con letra rojo brillante" + "\033[0m");
     * }</pre>
     *
     * @param val_255 valor del color de fondo (0–255)
     * @return la secuencia ANSI que cambia el color de fondo
     */
    @NotNull
    @Contract(pure = true)
    public static String ColorTerm255_foreground(int val_255) {
        return String.format("\033[38;5;%dm", val_255);
    }

    /**
     * Permite imprimmir un texto usando colorres del arcoiris.
     * fuente: <a href="https://krazydad.com/tutorials/makecolors.php">krazydad.com/tutorials/makecolors</a>
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * for (int i = 0; i < 10; i++) {
     *     printRaindbow("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\n", i);
     * }
     * }</pre>
     *
     * @param str texto a imprimir
     * @param phase fase de color, recomiendo usar el valo 4 para que empieze por azul
     */
    public static void printRaindbow(String str, int phase) {
        printRaindbow(str, phase, 2, 0, 4);
    }

    /**
     * Permite imprimmir un texto usando colorres del arcoiris.
     * fuente: <a href="https://krazydad.com/tutorials/makecolors.php">krazydad.com/tutorials/makecolors</a>
     *
     * <p><b>Ejemplo:</b></p>
     * <pre>{@code
     * for (int i = 0; i < 10; i++) {
     *     printRaindbow("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\n", i, 1, 10, 3);
     * }
     * }</pre>
     *
     * @param str texto a imprimir
     * @param phase fase de color, recomiendo usar el valo 4 para que empieze por azul
     * @param red_frequency frecuencia de color rojo
     * @param green_frequency frecuencia del color verde
     * @param blue_frequency frecuencia del color azul
     */
    public static void printRaindbow(@NotNull String str, int phase, int red_frequency, int green_frequency, int blue_frequency) {
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

    /**
     * Permite dumpear un buffer de colores que represente pixeles en formato RGB, mostrando
     * los colores de este buffer en forma de RGB o BGR
     * @param datos_color datos de tipo Color RGB
     * @param width_img Ancho de la imagen en píxeles.
     * @param height_img Alto de la imagen en píxeles.
     * @param isBGR True == BGR(por ejemplo imágenes BMP), False == RGB (por ejemplo imágenes PNG o sin especificar)
     * @param pixel valor que representa un pixel
     */
    public static void dump_buffer_cli(Color[] datos_color, int width_img, int height_img, boolean isBGR, String pixel) {
        if (datos_color == null || datos_color.length != width_img * height_img) {
            System.out.println("Error: dimensiones incorrectas o datos nulos.");
            assert datos_color != null;
            System.out.printf("datos_color.length = %d, width_img(%d) * height_img(%d) = %d",
                    datos_color.length, width_img, height_img, width_img * height_img);
            return;
        }
        byte[] datos_byte = new byte[datos_color.length * 3];
        for (int i = 0; i < datos_color.length; i++) {
            Color c = datos_color[i];
            if (isBGR) {
                datos_byte[i * 3] = (byte) c.getBlue();
                datos_byte[i * 3 + 1] = (byte) c.getGreen();
                datos_byte[i * 3 + 2] = (byte) c.getRed();
            } else {
                datos_byte[i * 3] = (byte) c.getRed();
                datos_byte[i * 3 + 1] = (byte) c.getGreen();
                datos_byte[i * 3 + 2] = (byte) c.getBlue();
            }
        }
        dump_buffer_cli(datos_byte, width_img, height_img, isBGR, pixel); // Llama al método principal
    }

    /**
     * Permite dumpear un buffer de bytes que represente pixeles en formato BGR o RGB, mostrando
     * los colores de este buffer
     * @param datos_byte bytes que representa los pixeles en forma de bytes
     * @param width_img Ancho de la imagen en píxeles.
     * @param height_img Alto de la imagen en píxeles.
     * @param isBGR True == BGR(por ejemplo imágenes BMP), False == RGB (por ejemplo imágenes PNG o sin especificar)
     * @param pixel valor que representa un pixel
     */
    public static void dump_buffer_cli(byte[] datos_byte, int width_img, int height_img, boolean isBGR, String pixel) {
        if (datos_byte == null || (datos_byte.length) / 3 != width_img * height_img) {
            System.out.println("Error: dimensiones incorrectas o datos nulos.");
            assert datos_byte != null;
            System.out.printf("datos_byte.length = %d, width_img(%d) * height_img(%d) = %d",
                    datos_byte.length / 3, width_img, height_img, width_img * height_img);
            return;
        }

        for (int fila = height_img - 1; fila >= 0; fila--) {
            for (int col = 0; col < width_img; col++) {
                int index = (fila * width_img + col) * 3;

                int r, g, b;
                if (isBGR) {
                    b = datos_byte[index] & 0xFF;
                    g = datos_byte[index + 1] & 0xFF;
                    r = datos_byte[index + 2] & 0xFF;
                } else { // RGB
                    r = datos_byte[index] & 0xFF;
                    g = datos_byte[index + 1] & 0xFF;
                    b = datos_byte[index + 2] & 0xFF;
                }

                System.out.print(new JColorsTerm(new Color(0,0,0), new Color(r, g, b)) + pixel + JColorsTerm.RESET);
            }
            System.out.println();
        }
    }
}
