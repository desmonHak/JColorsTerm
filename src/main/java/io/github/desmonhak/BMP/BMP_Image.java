package io.github.desmonhak.BMP;
/*
 * Licencia Apache, Versión 2.0 con Modificación
 *
 * Copyright 2023 Desmon
 *
 * Se concede permiso, de forma gratuita, a cualquier persona que obtenga una copia
 * de este software y archivos de documentación asociados (el "Software"), para
 * tratar el Software sin restricciones, incluidos, entre otros, los derechos de
 * uso, copia, modificación, fusión, publicación, distribución, sublicencia y/o
 * venta de copias del Software, y para permitir a las personas a quienes se les
 * proporcione el Software hacer lo mismo, sujeto a las siguientes condiciones:
 * El anterior aviso de copyright y este aviso de permiso se incluirán en todas
 * las copias o partes sustanciales del Software.
 *
 * EL SOFTWARE SE PROPORCIONA "TAL CUAL", SIN GARANTÍA DE NINGÚN TIPO, EXPRESA O
 * IMPLÍCITA, INCLUYENDO PERO NO LIMITADO A LAS GARANTÍAS DE COMERCIABILIDAD,
 * IDONEIDAD PARA UN PROPÓSITO PARTICULAR Y NO INFRACCIÓN. EN NINGÚN CASO LOS
 * TITULARES DEL COPYRIGHT O LOS TITULARES DE LOS DERECHOS DE AUTOR SERÁN
 * RESPONSABLES DE NINGÚN RECLAMO, DAÑO U OTRA RESPONSABILIDAD, YA SEA EN UNA
 * ACCIÓN DE CONTRATO, AGRAVIO O DE OTRA MANERA, QUE SURJA DE, FUERA DE O EN
 * CONEXIÓN CON EL SOFTWARE O EL USO U OTRO TIPO DE ACCIONES EN EL SOFTWARE.
 *
 * Además, cualquier modificación realizada por terceros se considerará propiedad
 * del titular original de los derechos de autor. Los titulares de derechos de
 * autor originales no se responsabilizan de las modificaciones realizadas por terceros.
 * Queda explícitamente establecido que no es obligatorio especificar ni notificar
 * los cambios realizados entre versiones, ni revelar porciones específicas de
 * código modificado.
 */

import io.github.desmonhak.JColorsTerm;

import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class BMP_Image {
    // https://en.wikipedia.org/wiki/BMP_file_format

    // siempre tiene el mismo valor normalmente
    public IntType_forBMP file_header            = new IntType_forBMP((byte)0x4D, (byte)0x42);

    // tamaño imagen
    public IntType_forBMP size_img               = new IntType_forBMP((byte)0, (byte)0, (byte)0, (byte)0);

    // estan reservados pero hay que escribirlos:
    public IntType_forBMP reserve1               = new IntType_forBMP((byte)0, (byte)0);
    public IntType_forBMP reserve2               = new IntType_forBMP((byte)0, (byte)0);

    // donde empieza los datos:
    public IntType_forBMP offset_init_data       = new IntType_forBMP((byte)0, (byte)0, (byte)0, (byte)0x36);

    // tamaño de la cabecera (normalmente vale con 0x28):
    public class Header {
        public IntType_forBMP size_header         = new IntType_forBMP((byte)0, (byte)0, (byte)0, (byte)0x28);

        // ancho de la imagen
        public IntType_forBMP width_img           = new IntType_forBMP((byte)0, (byte)0);

        // altura de la imagen
        public IntType_forBMP height_img          = new IntType_forBMP((byte)0, (byte)0);

        // numero de planos de color, siempre a 1:
        public IntType_forBMP color_planes        = new IntType_forBMP((byte)0, (byte)1);

        // The number of bits per pixel(bits por pixel, si es RGB = 8 * 3 = 24bits por pixel
        public IntType_forBMP bit_pixel           = new IntType_forBMP((byte)0, (byte)24);
    }

    public Header header_bmp = new Header();
    // metodo de compresion(siempre a 0 para no compresion)
    public IntType_forBMP method_compresion       = new IntType_forBMP((byte)0, (byte)0, (byte)0, (byte)0);

    // tamaño de la imagen en crudo(sin contar la cabecera)
    public IntType_forBMP size_img_raw             = new IntType_forBMP((byte)0, (byte)0, (byte)0, (byte)0);

    // resolucion orizontal:
    public IntType_forBMP horizontal_resolution    = new IntType_forBMP((byte)0, (byte)0, (byte)0, (byte)0);

    // resolucion vertical:
    public IntType_forBMP vertical_resolution      = new IntType_forBMP((byte)0, (byte)0, (byte)0, (byte)0);

    // el número de colores en la paleta de colores, o 0 para el valor predeterminado 2^n
    public IntType_forBMP color_palette            = new IntType_forBMP((byte)0, (byte)0, (byte)0, (byte)0);

    // la cantidad de colores importantes utilizados, o 0 cuando todos los colores son importantes; generalmente se ignora
    public IntType_forBMP important_colors         = new IntType_forBMP((byte)0, (byte)0, (byte)0, (byte)0);

    // datos de la imagen(pixeles) con el padding añadido
    public byte[] data_raw;

    public byte[] header_procesade;

    /**
     * Permite inicializar los datos de la imagen, una vez inicializados no se
     * deben alterar, en caso de querer cambiar los datos es necesario instancias nuevamente
     * @param width_img ancho de la imagen
     * @param height_img largo de la imagen
     * @param data_raw buffer de la imagen en formato RGB
     */
    public BMP_Image(int width_img, int height_img, byte[] data_raw) {
        int bytesPerRow = width_img * 3; // 3 bytes por pixel (RGB)
        int padding = (4 - (bytesPerRow % 4)) % 4;
        int bytesPerRowWithPadding = bytesPerRow + padding;
        System.out.println("Se necesita un padding de " + padding);
        // Crear un nuevo array con el padding incluido
        byte[] paddedData = new byte[bytesPerRowWithPadding * height_img];

        // Copiar los datos originales y añadir el padding
        for (int y = 0; y < height_img; y++) {
            System.arraycopy(data_raw, y * bytesPerRow, paddedData, y * bytesPerRowWithPadding, bytesPerRow);
            // El padding se queda como ceros
        }

        this.header_bmp.width_img = new IntType_forBMP(width_img);
        this.header_bmp.height_img = new IntType_forBMP(height_img);
        //this.data_raw = data_raw;
        this.data_raw = paddedData;

        this.size_img_raw = new IntType_forBMP(data_raw.length);

        // no es del todo correcto sumar como tamaño de bitmap header 0x36,
        // pero como en este caso no se usa valores opcionales, se puede hacer
        this.size_img = new IntType_forBMP(data_raw.length+ 0x36);
    }

    /**
     * Permite calcular los datos de la imagen BMP, este metodo solo deberia llamarse
     * una vez por instancia
     * @return devuelve la imagen en formato BMP en forma de array listo para
     * poder ser escrita en un archivo
     */
    public byte[] create_BMP() {
        long size_byte_array = this.file_header.number.length +
                this.size_img.number.length + this.reserve1.number.length +
                this.reserve2.number.length + this.offset_init_data.number.length +

                // datos cabecera:
                this.header_bmp.size_header.number.length + this.header_bmp.width_img.number.length +
                this.header_bmp.height_img.number.length + this.header_bmp.color_planes.number.length +
                this.header_bmp.bit_pixel.number.length +

                // seguir con los demas datos
                this.method_compresion.number.length +
                this.size_img_raw.number.length + this.horizontal_resolution.number.length +
                this.vertical_resolution.number.length + this.color_palette.number.length +
                this.important_colors.number.length +

                // sumar el tamaño de la imagen en crudo
                (long)this.size_img_raw.get_int();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            writeBytes(baos, this.file_header.number);
            writeBytes(baos, this.size_img.number);
            writeBytes(baos, this.reserve1.number);
            writeBytes(baos, this.reserve2.number);
            writeBytes(baos, this.offset_init_data.number);

            writeBytes(baos, this.header_bmp.size_header.number);
            writeBytes(baos, this.header_bmp.width_img.number);
            writeBytes(baos, this.header_bmp.height_img.number);
            writeBytes(baos, this.header_bmp.color_planes.number);
            writeBytes(baos, this.header_bmp.bit_pixel.number);

            writeBytes(baos, this.method_compresion.number);
            writeBytes(baos, this.size_img_raw.number);
            writeBytes(baos, this.horizontal_resolution.number);
            writeBytes(baos, this.vertical_resolution.number);
            writeBytes(baos, this.color_palette.number);
            writeBytes(baos, this.important_colors.number);
            writeBytes(baos, this.data_raw);

            //byte[] data = new byte[Math.toIntExact(size_byte_array)];
            //data[0] = file_header.number[0];

            return header_procesade = baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Permite escribir una cantidad de bytes
     * @param baos objeto de tipo ByteArrayOutputStream donde escribir
     * @param bytes bytes a escribir
     */
    public void writeBytes(ByteArrayOutputStream baos, byte[] bytes) {
        baos.write(bytes, 0, bytes.length);
    }

    /**
     * Imprime el contenido de la imagen en formato hexadecimal
     * @param string_to_hex datos a imprimir
     * @param line_new cada cuanto hacer un salto de linea
     */
    public void printDataRawHex(byte[] string_to_hex, int line_new) {
        if (string_to_hex == null) {
            System.out.println("string_to_hex is null");
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < string_to_hex.length; i++) {
            sb.append(String.format("%02X", string_to_hex[i]));

            if (i % line_new == line_new - 1 || i == string_to_hex.length - 1) {
                sb.append("\n");
            } else {
                sb.append(" ");
            }
        }

        System.out.print(sb.toString());
    }

    public IntType_forBMP getFile_header() {
        return file_header;
    }

    public IntType_forBMP getSize_img() {
        return size_img;
    }

    public IntType_forBMP getReserve1() {
        return reserve1;
    }

    public IntType_forBMP getReserve2() {
        return reserve2;
    }

    public IntType_forBMP getOffset_init_data() {
        return offset_init_data;
    }

    public Header getHeader_bmp() {
        return header_bmp;
    }

    public IntType_forBMP getMethod_compresion() {
        return method_compresion;
    }

    public IntType_forBMP getSize_img_raw() {
        return size_img_raw;
    }

    public IntType_forBMP getHorizontal_resolution() {
        return horizontal_resolution;
    }

    public IntType_forBMP getVertical_resolution() {
        return vertical_resolution;
    }

    public IntType_forBMP getColor_palette() {
        return color_palette;
    }

    public IntType_forBMP getImportant_colors() {
        return important_colors;
    }

    public byte[] getData_raw() {
        return data_raw;
    }

    public byte[] getHeader_procesade() {
        return header_procesade;
    }

    /**
     * Permite escribir la imagen con sus datos
     * @param name_file nombre del archivo BMP
     */
    public void write_bmp_to_file(String name_file){
        try {

            OutputStream img_file = new FileOutputStream(name_file);
            if (header_procesade == null) {
                this.create_BMP();
            }
            img_file.write(header_procesade, 0, header_procesade.length);
            img_file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "BMP_Image{" +
                "file_header=" + file_header +
                ", size_img=" + size_img +
                ", reserve1=" + reserve1 +
                ", reserve2=" + reserve2 +
                ", offset_init_data=" + offset_init_data +
                ", header_bmp=" + header_bmp +
                ", method_compresion=" + method_compresion +
                ", size_img_raw=" + size_img_raw +
                ", horizontal_resolution=" + horizontal_resolution +
                ", vertical_resolution=" + vertical_resolution +
                ", color_palette=" + color_palette +
                ", important_colors=" + important_colors +
                ", data_raw=" + Arrays.toString(data_raw) +
                ", header_procesade=" + Arrays.toString(header_procesade) +
                '}';
    }

    /**
     * Permite imprimir todos los atributos de la imagen
     */
    public void printBMPAttributes() {
        System.out.println("File Header: 0x" + file_header.get_string_hex());
        System.out.println("Image Size: " + size_img.get_int() + " bytes");
        System.out.println("Reserve1: 0x" + reserve1.get_string_hex());
        System.out.println("Reserve2: 0x" + reserve2.get_string_hex());
        System.out.println("Offset Init Data: " + offset_init_data.get_int());

        System.out.println("\nHeader:");
        System.out.println("  Size Header: " + header_bmp.size_header.get_int() + " bytes");
        System.out.println("  Width: " + header_bmp.width_img.get_short() + " pixels");
        System.out.println("  Height: " + header_bmp.height_img.get_short() + " pixels");
        System.out.println("  Color Planes: " + header_bmp.color_planes.get_short());
        System.out.println("  Bits per Pixel: " + header_bmp.bit_pixel.get_short());

        System.out.println("Compression Method: " + method_compresion.get_int());
        System.out.println("Raw Image Size: " + size_img_raw.get_int() + " bytes");
        System.out.println("Horizontal Resolution: " + horizontal_resolution.get_int() + " pixels/meter");
        System.out.println("Vertical Resolution: " + vertical_resolution.get_int() + " pixels/meter");
        System.out.println("Color Palette: " + color_palette.get_int());
        System.out.println("Important Colors: " + important_colors.get_int());

        if (data_raw != null) {
            System.out.println("Raw Data Size: " + data_raw.length + " bytes");
            printDataRawHex(this.data_raw, 16);
        } else {
            System.out.println("Raw Data: Not set");
        }

        System.out.println("Todos los datos: ");
        printDataRawHex(create_BMP(), 16);
    }

    /**
     * abrir BMP estándar sin compresión, con formato 24 bits BGR, que es el más común
     * @param filepath path de la imagen BMP a leer
     * @return devuelve una instancia de imagen BMP
     * @throws IOException la imagen no se pudo leer
     */
    public static BMP_Image readBMPFile(String filepath) throws IOException {
        try (InputStream is = new FileInputStream(filepath)) {
            byte[] fileHeader = new byte[14];
            is.read(fileHeader); // Cabecera archivo BMP

            byte[] dibHeader = new byte[40];
            is.read(dibHeader); // Cabecera DIB (BITMAPINFOHEADER)

            int width = ((dibHeader[7] & 0xFF) << 24) | ((dibHeader[6] & 0xFF) << 16)
                    | ((dibHeader[5] & 0xFF) << 8) | (dibHeader[4] & 0xFF);
            int height = ((dibHeader[11] & 0xFF) << 24) | ((dibHeader[10] & 0xFF) << 16)
                    | ((dibHeader[9] & 0xFF) << 8) | (dibHeader[8] & 0xFF);

            int bitsPerPixel = ((dibHeader[15] & 0xFF) << 8) | (dibHeader[14] & 0xFF);

            int bytesPerPixel = bitsPerPixel / 8;
            int bytesPerRow = width * bytesPerPixel;
            int padding = (4 - (bytesPerRow % 4)) % 4;

            // Posición del inicio de datos (offset)
            int offset = ((fileHeader[13] & 0xFF) << 24) | ((fileHeader[12] & 0xFF) << 16)
                    | ((fileHeader[11] & 0xFF) << 8) | (fileHeader[10] & 0xFF);

            // Saltar hasta comenzar datos (offset header)
            long skipped = is.skip(offset - 14 - 40);

            // Leer datos con padding de filas
            byte[] dataRaw = new byte[(bytesPerRow + padding) * height];
            int bytesRead = 0;
            while (bytesRead < dataRaw.length) {
                int read = is.read(dataRaw, bytesRead, dataRaw.length - bytesRead);
                if (read == -1) break;
                bytesRead += read;
            }

            return new BMP_Image(width, height, dataRaw);
        }
    }
    /**
     * Extrae y devuelve el buffer original de la imagen sin padding por fila.
     * @return byte[] buffer de imagen sin padding (width * height * 3 bytes)
     */
    public byte[] getDataWithoutPadding() {
        int width = header_bmp.width_img.get_short();
        int height = header_bmp.height_img.get_short();
        int bytesPerRow = width * 3;
        int padding = (4 - (bytesPerRow % 4)) % 4;
        int bytesPerRowWithPadding = bytesPerRow + padding;

        byte[] rawDataWithoutPadding = new byte[bytesPerRow * height];

        for (int y = 0; y < height; y++) {
            System.arraycopy(this.data_raw, y * bytesPerRowWithPadding, rawDataWithoutPadding, y * bytesPerRow, bytesPerRow);
        }

        return rawDataWithoutPadding;
    }

    /**
     * Extrae y devuelve el buffer de la imagen en forma de array Color[], sin padding.
     * @return array de Color que representa la imagen pixel por pixel.
     */
    public Color[] getColorsWithoutPadding() {
        int width = header_bmp.width_img.get_short();
        int height = header_bmp.height_img.get_short();
        byte[] rawDataWithoutPadding = getDataWithoutPadding();
        Color[] colors = new Color[width * height];

        for (int i = 0; i < colors.length; i++) {
            int index = i * 3;
            int b = rawDataWithoutPadding[index] & 0xFF;
            int g = rawDataWithoutPadding[index + 1] & 0xFF;
            int r = rawDataWithoutPadding[index + 2] & 0xFF;
            colors[i] = new Color(r, g, b);
        }
        return colors;
    }

    /**
     * Convierte un array de objetos Color a un buffer de bytes en formato BGR.
     * @param colors array de Color
     * @return byte[] buffer en formato BGR (3 bytes por píxel)
     */
    public static byte[] colorsToBGRBuffer(Color[] colors) {
        byte[] bgrBuffer = new byte[colors.length * 3];
        for (int i = 0; i < colors.length; i++) {
            Color c = colors[i];
            bgrBuffer[i * 3] = (byte) c.getBlue();
            bgrBuffer[i * 3 + 1] = (byte) c.getGreen();
            bgrBuffer[i * 3 + 2] = (byte) c.getRed();
        }
        return bgrBuffer;
    }


}
