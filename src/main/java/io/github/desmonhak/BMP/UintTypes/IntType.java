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
package io.github.desmonhak.UintTypes;

public class IntType {
    public IntType_type type;
    public byte[] number;

    public IntType(byte n1) {
        this.number = new byte[1];
        this.number[0] = n1;
        this.type = IntType_type.uint8_t;
    }

    public IntType(byte n1, byte n2) {
        this.number = new byte[2];
        this.number[0] = n2; this.number[1] = n1;  // Invertido para little-endian
        this.type = IntType_type.uint16_t;
    }

    public IntType(short n1) {
        this.number = new byte[2];
        this.number[0] = (byte) (n1 & 0xFF);
        this.number[1] = (byte) ((n1 >> 8) & 0xFF);
        this.type = IntType_type.uint16_t;
    }

    public IntType(byte n1, byte n2, byte n3, byte n4) {
        this.number = new byte[4];
        this.number[0] = n4; this.number[1] = n3;  // Invertido para little-endian
        this.number[2] = n2; this.number[3] = n1;
        this.type = IntType_type.uint32_t;
    }

    public IntType(int n1) {
        this.number = new byte[4];
        this.number[0] = (byte) (n1 & 0xFF);
        this.number[1] = (byte) ((n1 >> 8) & 0xFF);
        this.number[2] = (byte) ((n1 >> 16) & 0xFF);
        this.number[3] = (byte) ((n1 >> 24) & 0xFF);
        this.type = IntType_type.uint32_t;
    }

    public IntType(byte n1, byte n2, byte n3, byte n4, byte n5, byte n6, byte n7, byte n8) {
        this.number = new byte[8];
        this.number[0] = n8; this.number[1] = n7;  // Invertido para little-endian
        this.number[2] = n6; this.number[3] = n5;
        this.number[4] = n4; this.number[5] = n3;
        this.number[6] = n2; this.number[7] = n1;
        this.type = IntType_type.uint64_t;
    }

    public IntType(long n1) {
        this.number = new byte[8];
        this.number[0] = (byte) (n1 & 0xFF);
        this.number[1] = (byte) ((n1 >> 8) & 0xFF);
        this.number[2] = (byte) ((n1 >> 16) & 0xFF);
        this.number[3] = (byte) ((n1 >> 24) & 0xFF);
        this.number[4] = (byte) ((n1 >> 32) & 0xFF);
        this.number[5] = (byte) ((n1 >> 40) & 0xFF);
        this.number[6] = (byte) ((n1 >> 48) & 0xFF);
        this.number[7] = (byte) ((n1 >> 56) & 0xFF);
        this.type = IntType_type.uint64_t;
    }

    public IntType(IntType n1, IntType n2) {
        int size_number = n1.number.length + n2.number.length;
        this.number = new byte[size_number];
        System.arraycopy(n1.number, 0, this.number, 0, n1.number.length);
        System.arraycopy(n2.number, 0, this.number, n1.number.length, n2.number.length);
        this.type = IntType_type.uint_unknow_t;
    }

    public short get_short() {
        return (short)((this.number[1] & 0xFF) << 8 | (this.number[0] & 0xFF));
    }

    public int get_int() {
        return ((this.number[3] & 0xFF) << 24) |
                ((this.number[2] & 0xFF) << 16) |
                ((this.number[1] & 0xFF) << 8) |
                (this.number[0] & 0xFF);
    }

    public long get_long() {
        return ((long)(this.number[7] & 0xFF) << 56) |
                ((long)(this.number[6] & 0xFF) << 48) |
                ((long)(this.number[5] & 0xFF) << 40) |
                ((long)(this.number[4] & 0xFF) << 32) |
                ((long)(this.number[3] & 0xFF) << 24) |
                ((long)(this.number[2] & 0xFF) << 16) |
                ((long)(this.number[1] & 0xFF) << 8) |
                (this.number[0] & 0xFF);
    }

    public String get_string_hex() {
        StringBuilder number = new StringBuilder();
        for (int i = this.number.length - 1; i >= 0; i--) {
            number.append(String.format("%02X", this.number[i]));
        }
        return number.toString();
    }
}