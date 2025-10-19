package io.github.desmonhak;

import io.github.desmonhak.BMP.BMP_Image;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static io.github.desmonhak.JColorsTerm.*;
import static org.junit.jupiter.api.Assertions.*;

class JColorsTermTest {


    @Test
    void testConstructorRGB() {
        JColorsTerm term = new JColorsTerm(255, 128, 64);
        assertEquals("\033[38;2;255;128;64m", term.string_color);

        System.out.println("Ejemplo RGB sin texto:");
        System.out.println(term + "Texto coloreado" + RESET);
    }

    @Test
    void testConstructorRGBWithText() {
        JColorsTerm term = new JColorsTerm(255, 128, 64, "Hola Mundo");
        assertEquals("\033[38;2;255;128;64mHola Mundo" + RESET, term.string_color);

        System.out.println("Ejemplo RGB con texto incluido:");
        System.out.println(term);
    }

    @Test
    void testToString() {
        JColorsTerm term = new JColorsTerm(200, 0, 0);
        assertEquals("\033[38;2;200;0;0m", term.toString());

        System.out.println("Ejemplo usando toString():");
        System.out.println(term + "Texto rojo personalizado" + RESET);
    }

    @Test
    void testUP() {
        assertEquals("\033[5A", UP(5));
        System.out.println("Mueve el cursor 5 líneas arriba: " + UP(5));
    }

    @Test
    void testDOWN() {
        assertEquals("\033[10B", DOWN(10));
        System.out.println("Mueve el cursor 10 líneas abajo: " + DOWN(10));
    }

    @Test
    void testFORWARD() {
        assertEquals("\033[3C", FORWARD(3));
        System.out.println("Mueve el cursor 3 columnas adelante: " + FORWARD(3));
    }

    @Test
    void testBACK() {
        assertEquals("\033[7D", BACK(7));
        System.out.println("Mueve el cursor 7 columnas atrás: " + BACK(7));
    }

    @Test
    void testPOS() {
        assertEquals("\033[12;34H", POS(12, 34));
        System.out.println("Mueve el cursor a la posición (12, 34): " + POS(12, 34));
    }

    @Test
    void testSET_TITLE() {
        assertEquals("\033]2;MiTitulo\007", SET_TITLE("MiTitulo"));
        System.out.println("Establece título de ventana a 'MiTitulo': " + SET_TITLE("MiTitulo"));
    }

    @Test
    void testSET_SIZE_SLIDER() {
        assertEquals("\033[?25c", SET_SIZE_SLIDER(25));
        System.out.println("Cambia el modo del cursor: " + SET_SIZE_SLIDER(25));
    }

    @Test
    void testConstants() {
        assertNotNull(RESET);
        assertNotNull(RED);
        assertTrue(RESET.startsWith("\033["));
        assertTrue(GREEN.contains("32"));

        System.out.println("Ejemplo de colores básicos:");
        System.out.println(RED + "Rojo" + RESET);
        System.out.println(GREEN + "Verde" + RESET);
        System.out.println(BLUE + "Azul" + RESET);
        System.out.println(YELLOW + "Amarillo" + RESET);
        System.out.println(CYAN + "Cian" + RESET);
    }

    @Test
    void testToStringWithText() {
        JColorsTerm term = new JColorsTerm(10, 100, 30, "Texto Personalizado");
        assertEquals("\033[38;2;10;100;30mTexto Personalizado" + RESET, term.toString());

        System.out.println(term + " -> Ejemplo con texto personalizado en RGB (10,100,30):");

        System.out.println(new JColorsTerm(Color.pink) + "Color rosa usando awt.Color");

        // color rosa con fondo cyan
        System.out.println(new JColorsTerm(Color.pink, Color.CYAN) + "Color rosa usando awt.Color");

    }

    void table(int max, int init, int end) {
        for (int r = 0; r <= max; r+=36) {
            for (int g = init; g <= end && r+g <= max; g++) {
                System.out.printf(ColorTerm255_background(r + g) + " %03d ", r+g);
            }
            System.out.println(RESET);
        }
    }



    @Test
    void colorsTerm255() {

        System.out.println();
        for (int i = 0; i < 256; i++) {
            System.out.printf(ColorTerm255_background(i) + " %03d ", i);
            if((i % 16) == 0) {
                System.out.println(RESET);
            }
        }
        System.out.println();

        for (int i = 0; i < 256; i++) {
            System.out.printf(ColorTerm255_foreground(i) + " %03d ", i);
            if((i % 16) == 0) {
                System.out.println(RESET);
            }
        }
        System.out.println();
    }

    @Test
    public void dump_buffer() {
        Color[] datos = { // 6 * 3 = 18 colores
                new Color(255, 0, 0),     new Color(0, 255, 0),   new Color(0, 0, 255),
                new Color(255, 255, 0),   new Color(0, 255, 255), new Color(255, 0, 255),
                new Color(255, 255, 255), new Color(0, 0, 0),     new Color(255, 255, 255),
                new Color(125, 0, 0),     new Color(0, 125, 0),   new Color(0, 0, 125),
                new Color(125, 75, 0),    new Color(0, 125, 75),  new Color(75, 0, 125),
                new Color(125, 75, 75),   new Color(0, 125, 0),   new Color(75, 75, 125),
        };
        JColorsTerm.dump_buffer_cli(datos, 3, 6, false, "   ");
    }

    @Test
    void exampleTable() {

        for (int i = 0; i < 10; i++) {
            printRaindbow("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\n", i);
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            printRaindbow("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\n", i, 1, 10, 3);
        }

        table(201, 16, 21);
        System.out.println();

        table(207, 22, 27);
        System.out.println();

        table(213, 28, 33);
        System.out.println();

        table(219, 34, 39);
        System.out.println();

        table(225, 40, 45);
        System.out.println();

        table(231, 46, 51);
        System.out.println();

    }
}