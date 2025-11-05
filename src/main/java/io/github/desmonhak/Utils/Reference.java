package io.github.desmonhak.Utils;

/**
 * Clase para hacer paso de referencias y no pasar copias de valores
 * @param <_Object> tipo de objeto del que trata la referencia
 */
public class Reference<_Object> {
    public _Object value;

    public Reference(_Object value) {
        this.value = value;
    }

    public _Object getValue() {
        return value;
    }

    public void setValue(_Object value) {
        this.value = value;
    }

    public Reference() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.format("Reference[%d -> %d] {value=%s}", this.hashCode(), value.hashCode(), value.toString());
    }
}
