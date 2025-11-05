package io.github.desmonhak.Utils;

/**
 * Clase genérica para representar un par de objetos relacionados.
 *
 * @param <U> tipo del primer elemento del par
 * @param <V> tipo del segundo elemento del par
 */
public class Pair<U, V> {

    /**
     * El primer elemento de este par.
     */
    private U first;

    /**
     * El segundo elemento de este par.
     */
    private V second;

    /**
     * Construye un nuevo par con los valores dados.
     *
     * @param first  el primer elemento del par
     * @param second el segundo elemento del par
     */
    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Obtiene el primer elemento del par.
     *
     * @return el primer elemento
     */
    public U getFirst() {
        return first;
    }

    /**
     * Establece el primer elemento del par.
     *
     * @param first el nuevo primer elemento
     */
    public void setFirst(U first) {
        this.first = first;
    }

    /**
     * Obtiene el segundo elemento del par.
     *
     * @return el segundo elemento
     */
    public V getSecond() {
        return second;
    }

    /**
     * Establece el segundo elemento del par.
     *
     * @param second el nuevo segundo elemento
     */
    public void setSecond(V second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
