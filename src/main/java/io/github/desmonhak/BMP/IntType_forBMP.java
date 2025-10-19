package io.github.desmonhak;
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


import io.github.desmonhak.UintTypes.IntType;
import io.github.desmonhak.UintTypes.IntType_type;

/**
 * Clase de enteros de tamaño especifico para manejo del BMP
 */
public class IntType_forBMP extends IntType {
    IntType_type type_required = null;

    /**
     * Permite crear un valor uint16_t desde 2 bytes
     * @param n1 byte 1
     * @param n2 byte 2
     */
    public IntType_forBMP(byte n1, byte n2) {
        super(n1, n2);
        this.type_required = IntType_type.uint16_t; // indica el valor que se requiere para este campo
    }

    /**
     * Permite crear un valor uint16_t desde 1 short
     * @param n1 valor short
     */
    public IntType_forBMP(short n1) {
        super(n1);
        this.type_required = IntType_type.uint16_t;
    }

    /**
     * Permite crear un tipo personalizado usando dos valores enteros
     * @param n1 valor entero numero 1 de tamaño desconocido
     * @param n2 valor entero numero 2 de tamaño desconocido
     */
    public IntType_forBMP(IntType n1, IntType n2) {
        super(n1, n2);
        this.type_required = IntType_type.uint_unknow_t;
    }

    /**
     * Permite crear un valor de 64bits usando 8 bytes
     * @param n1 byte 1
     * @param n2 byte 2
     * @param n3 byte 3
     * @param n4 byte 4
     * @param n5 byte 5
     * @param n6 byte 6
     * @param n7 byte 7
     * @param n8 byte 8
     */
    public IntType_forBMP(byte n1, byte n2, byte n3, byte n4, byte n5, byte n6, byte n7, byte n8) {
        super(n1, n2, n3, n4, n5, n6, n7, n8);
        this.type_required = IntType_type.uint64_t;
    }

    /**
     * Permite crear un valor de 64bits usando un long
     * @param n1 valor long
     */
    public IntType_forBMP(long n1) {
        super(n1);
        this.type_required = IntType_type.uint64_t;
    }

    /**
     * Permite crear un valor de 32bits usando 4 valores de bytes
     * @param n1 byte 1
     * @param n2 byte 2
     * @param n3 byte 3
     * @param n4 byte 4
     */
    public IntType_forBMP(byte n1, byte n2, byte n3, byte n4) {
        super(n1, n2, n3, n4);
        this.type_required = IntType_type.uint32_t;
    }

    /**
     * Permite crear un valor de 32bits usando un int
     * @param n1 valor de tipo int
     */
    public IntType_forBMP(int n1) {
        super(n1);
        this.type_required = IntType_type.uint32_t;
    }

    /**
     * permite crear un valor uint8_t usando un valor de tipo byte
     * @param n1 byte
     */
    public IntType_forBMP(byte n1) {
        super(n1);
        this.type_required = IntType_type.uint8_t;
    }
}
