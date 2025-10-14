package io.github.desmonhak;

public class JColorsTerm {
    public String string_color;
    public static String string_reset_color = "\033[39m";
    public JColorsTerm (int r, int g, int b) {
        this.string_color = "\033[38;2;%d;%d;%dm".formatted(r,g,b);
    }
    public JColorsTerm (int r, int g, int b, String text) {
        // \033[38;2;<r>;<g>;<b>m     #Select RGB foreground color
        //\033[48;2;<r>;<g>;<b>m     #Select RGB background color
        this.string_color = ("\033[38;2;%d;%d;%dm%s").formatted(r,g,b, text) + string_reset_color;
    }
}
