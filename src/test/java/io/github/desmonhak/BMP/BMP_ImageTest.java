package io.github.desmonhak.BMP;

import io.github.desmonhak.JColorsTerm;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BMP_ImageTest {



    public static void resizeBMP(String inputPath, String outputPath, int newWidth, int newHeight) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(inputPath));
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = resizedImage.createGraphics();
        // Usar interpolación de alta calidad para mejor resultado
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        ImageIO.write(resizedImage, "bmp", new File(outputPath));
    }

    @Test
    void create_BMP() throws IOException {
        // colores que usare para generar la imagen
        Color[] datos = { // 6 * 3 = 18 colores
                new Color(255, 0, 0),     new Color(0, 255, 0),   new Color(0, 0, 255),
                new Color(255, 255, 0),   new Color(0, 255, 255), new Color(255, 0, 255),
                new Color(255, 255, 255), new Color(0, 0, 0),     new Color(255, 255, 255),
                new Color(125, 0, 0),     new Color(0, 125, 0),   new Color(0, 0, 125),
                new Color(125, 75, 0),    new Color(0, 125, 75),  new Color(75, 0, 125),
                new Color(125, 75, 75),   new Color(0, 125, 0),   new Color(75, 75, 125),
        };

        // buffer de la imagen
        byte[] datos_byte = new byte[datos.length * 3 *10]; // *3 por que cada pixel son 3 bytes

        // crear los datos de la imagen
        for (int i = 0, j = 0; i < datos.length *10; i++, j+=3){
            datos_byte[j]   = (byte) datos[i % datos.length].getRed();
            datos_byte[j+1] = (byte) datos[i % datos.length].getGreen();
            datos_byte[j+2] = (byte) datos[i % datos.length].getBlue();
        }

        /**
         * Creamos una imagen de 3 * 60
         */
        BMP_Image imagen1 = new BMP_Image(3,6 *10, datos_byte);
        imagen1.printBMPAttributes();
        imagen1.write_bmp_to_file("src/test/java/io/github/desmonhak/BMP/Ejemplo3x60.bmp");
        BMP_Image imagen2 = new BMP_Image(6 *10,3, datos_byte);
        imagen2.write_bmp_to_file("src/test/java/io/github/desmonhak/BMP/Ejemplo60x3.bmp");

        JColorsTerm.dump_buffer_cli(datos_byte, 6 *10, 3, true, "   ");
        JColorsTerm.dump_buffer_cli(datos_byte, 3, 6 *10, true, "   ");

        BMP_Image bmp = BMP_Image.readBMPFile("src/test/java/io/github/desmonhak/BMP/Ejemplo60x3.bmp");
        bmp.printBMPAttributes();

        // leemos la imagen y obtemos los bytes de los pixeles
        JColorsTerm.dump_buffer_cli(bmp.getDataWithoutPadding(), 6 *10, 3, true, "   ");


        // redimensiono el BMP a una imagen de 600x600
        resizeBMP("src/test/java/io/github/desmonhak/BMP/nino_RGBs_lineal.bmp", "src/test/java/io/github/desmonhak/BMP/redimensionada.bmp", 600, 600);

        BMP_Image nino = BMP_Image.readBMPFile("src/test/java/io/github/desmonhak/BMP/redimensionada.bmp");
        bmp.printBMPAttributes();

        // leemos la imagen y obtemos los bytes de los pixeles
        JColorsTerm.dump_buffer_cli(nino.getDataWithoutPadding(), nino.header_bmp.width_img.get_int(), nino.header_bmp.height_img.get_int(), true, ".");


    }
}