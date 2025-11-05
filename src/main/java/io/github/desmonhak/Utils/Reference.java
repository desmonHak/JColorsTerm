package io.github.desmonhak.Utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Clase para hacer paso de referencias y no pasar copias de valores
 * @param <_Object> tipo de objeto del que trata la referencia
 */
public class Reference<_Object>
        implements Serializable, // para serelializacion
        Comparable<Reference<_Object>>, // para comparaciones
        Cloneable // para clonaos
{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Versionamiento simple: Mantener últimos valores en lista
     */
    private final LinkedList<_Object> history = new LinkedList<>();

    /**
     * Tamaño maximo del historial
     */
    private int maxHistorySize = 16;

    /**
     * Indica si quiero tener historial de cambios para esta referencia
     */
    private boolean trackHistory;

    /**
     * Campo para controlar inmutabilidad, que sea contante
     */
    private volatile boolean locked = false;

    /**
     * Bloquea la referencia para impedir que se modifique su valor.
     * Después de llamar a este método, setValue y operaciones atómicas no modificarán el valor.
     */
    public void lock() {
        locked = true;
    }

    /**
     * Verifica si la referencia está bloqueada (inmutable).
     *
     * @return true si no se permite modificar el valor
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Lista para listeners que se notificarán en cambios del valor
     */
    private final CopyOnWriteArrayList<Consumer<_Object>> listeners = new CopyOnWriteArrayList<>();

    /**
     * Valor de la referencia (usa AtomicReference para concurrencia segura)
     */
    public AtomicReference<_Object> value = null;

    private int depth = 0;

    /**
     * Constructor por defecto
     * @param value valor copia/original al que se referencia
     */
    public Reference(_Object value) {
        this(value, 0, false, false);
    }

    /**
     * Permite crear una referencia con una profundidad de referencia especificada
     * @param value valor de la referencia
     * @param depth profundidad de la referencia
     */
    public Reference(_Object value, int depth) {
        this(value, depth, false, false);
    }

    /**
     * Permite indicar si quiere bloquear la referencia, lo que permite no cambiar el valor
     *
     * @param value valor al que se hace referencia
     * @param depth profundidad de la referencia
     * @param locked false si se quiere que el valor sea mutable en la referencia
     */
    public Reference(_Object value, int depth, boolean locked) {
        this(value, depth, false, locked);
    }

    /**
     * Permite indicar si quiere bloquear la referencia, lo que permite no cambiar el valor,
     * ademas permite especifcar si se quiere tener un historial de cambios, si es locked,
     * el historial no tiene utilidad practica
     * @param value valor al que se hace referencia
     * @param depth profundidad de la referencia
     * @param trackHistory true si se quiere historial
     * @param locked false si se quiere que el valor sea mutable en la referencia
     */
    public Reference(_Object value, int depth, boolean trackHistory, boolean locked) {
        this.value = new AtomicReference<>(value);
        this.depth = depth;
        this.trackHistory = trackHistory;
        this.locked = locked;
        addToHistory(value);
    }

    /**
     * Permite indicar si quiere bloquear la referencia, lo que permite no cambiar el valor
     *
     * @param value valor al que se hace referencia
     * @param locked false si se quiere que el valor sea mutable en la referencia
     */
    public Reference(_Object value, boolean locked) {
        this(value, 0, false, locked);
    }

    /**
     * Permite indicar si quiere bloquear la referencia, lo que permite no cambiar el valor,
     * ademas permite especifcar si se quiere tener un historial de cambios, si es locked,
     * el historial no tiene utilidad practica
     *
     * @param value valor al que se hace referencia
     * @param trackHistory true si se quiere historial
     * @param locked false si se quiere que el valor sea mutable en la referencia
     */
    public Reference(_Object value, boolean trackHistory, boolean locked) {
        this.value = new AtomicReference<>(value);
        this.depth = 0;
        this.trackHistory = trackHistory;
        this.locked = locked;
    }

    /**
     * Devuelve la profundidad de anidamiento de esta referencia.
     * Puede ser útil para controlar o limitar niveles de referencias anidadas.
     *
     * @return el valor actual del campo depth que indica la profundidad
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Establece la profundidad de anidamiento para esta referencia.
     * Útil para inicializar o modificar el nivel en estructuras complejas.
     *
     * @param depth nuevo valor para la profundidad de la referencia
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Constructor por defecto que inicializa la referencia con valor null.
     */
    public Reference() {
        this.value = new AtomicReference<>(null);
    }

    /**
     * Añade un listener (observador) que será notificado cada vez que cambie el valor referenciado.
     * El listener es un Consumer que recibe el nuevo valor como parámetro.
     *
     * @param listener el consumidor a añadir a la lista de listeners
     */
    public void addListener(Consumer<_Object> listener) {
        listeners.add(listener);
    }

    /**
     * Remueve un listener previamente registrado, para que deje de ser notificado.
     *
     * @param listener el consumidor a eliminar de la lista de listeners
     */
    public void removeListener(Consumer<_Object> listener) {
        listeners.remove(listener);
    }


    /**
     * Notifica todos los listeners registrados pasando el nuevo valor cambiado.
     * Este método es llamado internamente cada vez que se actualiza el valor.
     *
     * @param value nuevo valor para enviar a los listeners
     */
    private void notifyListeners(_Object value) {
        for (Consumer<_Object> listener : listeners) {
            listener.accept(value);
        }
    }

    /**
     * Obtener el valor original
     * @return valor almacenado por la referencia
     */
    public _Object getValue() {
        return value.get();
    }

    /**
     * Cambiar valor de la referencia
     * @param newValue valor nuevo para la referencia
     */
    public void setValue(_Object newValue) {
        if (locked) {
            throw new IllegalStateException("Reference está bloqueada y no puede modificarse");
        }
        if (trackHistory) {
            this.setValueWithHistory(newValue);
        } else {
            value.set(newValue);
            notifyListeners(newValue);
        }
    }

    /**
     * Comprueba si el valor es nulo
     * @return true si el valor referenciado es null
     */
    public boolean isNull() {
        return value.get() == null;
    }

    /**
     * Permite crear una nueva referencia
     * @param value valor de la referencia, u otra referencia
     * @param <T> valor contenido por la referencia
     * @return nueva referencia creada
     */
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> Reference<T> ref(T value) {
        return new Reference<>(value);
    }

    /**
     * Mapea o transforma el valor apuntado, devolviendo una nueva referencia con el nuevo tipo
     * @param mapper función de transformación
     * @param <R> tipo de retorno
     * @return nueva referencia con el valor transformado
     */
    public <R> Reference<R> map(Function<_Object, R> mapper) {
        R mappedValue = null;
        _Object currentValue = this.getValue();
        if (currentValue != null) {
            mappedValue = mapper.apply(currentValue);
        }
        return new Reference<>(mappedValue);
    }

    /**
     * Calcula el código hash para esta referencia basado en el valor contenido.
     * <p>
     * Si el valor interno es null, retorna 0 como código hash.
     * Si no, delega a {@code hashCode()} del valor contenido.
     * <p>
     * Esto cumple con el contrato general de {@code hashCode()} en relación a {@code equals()}:
     * <ul>
     *   <li>Si dos objetos son iguales según {@code equals()}, deben retornar el mismo {@code hashCode()}.</li>
     *   <li>No se garantiza que objetos diferentes tengan hashCode distintos, pero es deseable minimizar colisiones.</li>
     * </ul>
     *
     * @return el código hash calculado
     */
    @Override
    public int hashCode() {
        _Object v = this.getValue();
        return (v == null) ? 0 : v.hashCode();
    }

    /**
     * Compara esta referencia con otro objeto para determinar si son iguales.
     * <p>
     * La comparación considera los siguientes casos:
     * <ul>
     *   <li>Si ambos objetos son la misma instancia ({@code this == obj}), retorna {@code true}.</li>
     *   <li>Si el objeto pasado no es una instancia de {@code Reference}, retorna {@code false}.</li>
     *   <li>Si ambos valores referenciados son {@code null}, retorna {@code true}.</li>
     *   <li>Si sólo uno de los valores es {@code null}, retorna {@code false}.</li>
     *   <li>Si ambos valores referenciados son a su vez {@code Reference}, compara recursivamente sus contenidos usando {@code equals}.</li>
     *   <li>En los demás casos, delega a {@code equals} del valor contenido ({@code thisVal.equals(otherVal)}).</li>
     * </ul>
     *
     * @param obj el objeto a comparar con esta referencia
     * @return {@code true} si las referencias son iguales según los criterios anteriores, {@code false} en caso contrario
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Reference)) return false;

        Reference<_Object> other = (Reference<_Object>) obj;
        _Object thisVal = this.getValue();
        _Object otherVal = other.getValue();

        if (thisVal == null && otherVal == null) return true;
        if (thisVal == null || otherVal == null) return false;

        // Si es otra Reference anidada, compara recursivamente
        if (thisVal instanceof Reference && otherVal instanceof Reference) {
            return ((Reference<?>) thisVal).equals(otherVal);
        }

        return thisVal.equals(otherVal);
    }

    /**
     * Método privado para toString que
     * evita NullPointerException y trata referencias anidadas
     * @param obj objeto a retornar
     * @return retorna el toString del obtjeto pasado
     */
    private String safeToString(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof Reference) {
            return ((Reference<?>) obj).toString();
        }
        return obj.toString();
    }

    /**
     * Devuelve un String que representa la referencia
     * @return String que representa la referencia, si la referencia contiene mas referencias
     * devuelve las multiples referencias
     */
    @Override
    public String toString() {
        _Object v = this.getValue();
        String valueStr = (v == null) ? "null" : safeToString(v);
        return String.format("Reference[%08x] {value=%s}", System.identityHashCode(this), valueStr);
    }

    /**
     * Compara esta referencia con otra referencia del mismo tipo para establecer un orden natural.
     * <p>
     * La comparación se realiza en base al valor contenido dentro de la referencia:
     * <ul>
     *     <li>Si ambos valores son {@code null}, se consideran iguales (retorna 0).</li>
     *     <li>Si el valor de esta referencia es {@code null}, se considera menor que cualquier valor no null (retorna -1).</li>
     *     <li>Si el valor de la otra referencia es {@code null}, esta referencia se considera mayor (retorna 1).</li>
     *     <li>Si ambos valores implementan {@link Comparable} y son compatibles para comparación, delega la comparación a esos valores.</li>
     * </ul>
     * <p>
     * Si los valores no son comparables, se lanza una {@link IllegalArgumentException}.
     *
     * @param other la otra referencia con la cual comparar
     * @return un número negativo, cero o positivo si esta referencia es menor, igual o mayor que la referencia {@code other}
     * @throws IllegalArgumentException si el valor contenido no implementa {@link Comparable} o no es compatible para la comparación
     */
    @Override
    public int compareTo(@NotNull Reference<_Object> other) {
        _Object thisVal = this.getValue();
        _Object otherVal = other.getValue();

        if (thisVal == null && otherVal == null) return 0;
        if (thisVal == null) return -1;
        if (otherVal == null) return 1;

        if (thisVal instanceof Comparable && otherVal.getClass().isAssignableFrom(thisVal.getClass())) {
            @SuppressWarnings("unchecked")
            Comparable<_Object> compThis = (Comparable<_Object>) thisVal;
            return compThis.compareTo(otherVal);
        }

        throw new IllegalArgumentException("El tipo apuntado no es Comparable");
    }


    /**
     * Interfaz para indicar la accion que realizar sobre el valor
     * @param <_Object> tipo de objeto en que realizar la accion
     */
    public interface Action<_Object> {
        /**
         * Metodo para invocar la accion
         * @param value valor recibido al que aplicar la accion
         * @return nuevo valor o el mismo, despues de aplicar la accion
         */
        _Object invoke(AtomicReference<_Object> value);
    }

    /**
     * Actualiza el valor primero, y luego obtiene el valor actualizado
     * @param action_for_value como realizar la actualization del valor
     * @return valor actualizado
     */
    public _Object updateAndGet(@NotNull Action<_Object> action_for_value) {
        if (locked) {
            throw new IllegalStateException("Reference está bloqueada y no puede modificarse");
        }

        _Object object = action_for_value.invoke(value);
        if (trackHistory) {
            this.setValueWithHistory(object);
        } else {
            this.setValue(object);
            notifyListeners(object); // notificar al listener
        }
        return object;
    }

    // Operaciones atómicas adicionales delegadas a AtomicReference:

    /**
     * Intenta establecer el valor de la referencia a {@code update} sólo si el valor actual es igual a {@code expected}.
     * Esta operación es atómica y se realiza sin bloqueo.
     *
     * @param expected el valor esperado actual
     * @param update   el nuevo valor a establecer si el valor actual es igual al esperado
     * @return {@code true} si la operación fue exitosa, {@code false} si el valor actual no coincide con {@code expected}
     */
    public boolean compareAndSet(_Object expected, _Object update) {
        if (locked) {
            throw new IllegalStateException("Reference está bloqueada y no puede modificarse");
        }

        return value.compareAndSet(expected, update);
    }

    /**
     * Establece el nuevo valor de la referencia y devuelve el valor anterior.
     * Notifica a los listeners registrados acerca del cambio.
     *
     * @param newValue el nuevo valor para la referencia
     * @return el valor anterior almacenado en la referencia
     */
    public _Object getAndSet(_Object newValue) {
        if (locked) {
            throw new IllegalStateException("Reference está bloqueada y no puede modificarse");
        }

        if (trackHistory) {
            addToHistory(getValue());
        }
        _Object old = value.getAndSet(newValue);
        notifyListeners(newValue);
        return old;
    }

    /**
     * Actualiza el valor de la referencia aplicando la función {@code updateFunction} al valor actual.
     * Intenta repetir la operación hasta que se realice correctamente de forma atómica.
     *
     * @param updateFunction función que recibe el valor actual y devuelve el nuevo valor para actualizar
     * @return el valor previo antes de la actualización
     */
    public _Object getAndUpdate(@NotNull Function<_Object, _Object> updateFunction) {
        if (locked) {
            throw new IllegalStateException("Reference está bloqueada y no puede modificarse");
        }

        while (true) {
            _Object prev = getValue();
            _Object next = updateFunction.apply(prev);
            if (trackHistory) {
                addToHistory(prev);
            }
            if (compareAndSet(prev, next)) {
                return prev;
            }
        }
    }

    /**
     * Actualiza el valor de la referencia aplicando la función {@code updateFunction} al valor actual y devuelve el nuevo valor actualizado.
     * Intenta repetir la operación hasta que se realice correctamente de forma atómica.
     * Notifica a los listeners registrados acerca del nuevo valor.
     *
     * @param updateFunction función que recibe el valor actual y devuelve el nuevo valor para actualizar
     * @return el nuevo valor almacenado en la referencia tras la actualización
     */
    public _Object updateAndGet(@NotNull Function<_Object, _Object> updateFunction) {
        if (locked) {
            throw new IllegalStateException("Reference está bloqueada y no puede modificarse");
        }

        while (true) {
            _Object prev = getValue();
            _Object next = updateFunction.apply(prev);
            if (trackHistory) {
                addToHistory(prev);
            }
            if (compareAndSet(prev, next)) {
                notifyListeners(next);
                return next;
            }
        }
    }

    // Métodos funcionales similares a Optional

    /**
     * Ejecuta el consumidor {@code consumer} si el valor actual está presente (no es null).
     *
     * @param consumer acción a ejecutar con el valor no nulo
     */
    public void ifPresent(Consumer<_Object> consumer) {
        _Object v = getValue();
        if (v != null) consumer.accept(v);
    }

    /**
     * Filtra el valor actual con el predicado {@code predicate}.
     * Si el valor cumple la condición, devuelve esta referencia, sino una referencia con valor null.
     *
     * @param predicate condición para filtrar el valor
     * @return esta referencia si el valor satisface el filtro, o nueva referencia con valor null en caso contrario
     */
    public Reference<_Object> filter(Predicate<_Object> predicate) {
        _Object v = getValue();
        if (v != null && predicate.test(v)) {
            return this;
        } else {
            return new Reference<>(null);
        }
    }

    /**
     * Convierte esta referencia en un {@link Optional} que puede contener el valor actual o estar vacío si es null.
     *
     * @return un Optional con el valor actual o vacío si null
     */
    public Optional<_Object> toOptional() {
        return Optional.ofNullable(getValue());
    }

    /**
     * Crea una nueva referencia a partir de un {@link Optional}.
     * Si el Optional tiene valor, se usa ese valor; si está vacío, se establece null.
     *
     * @param opt Optional del que extraer el valor
     * @param <T> tipo del valor contenido en el Optional y la referencia
     * @return nueva referencia con el valor del Optional o null
     */
    @NotNull
    @Contract("_ -> new")
    public static <T> Reference<T> fromOptional(@NotNull Optional<T> opt) {
        return new Reference<>(opt.orElse(null));
    }

    /**
     * Realiza una clonación profunda de esta referencia y del objeto contenido,
     * siempre que el objeto contenido implemente {@link Cloneable}.
     *
     * Si el valor no implementa {@link Cloneable}, se hace una copia superficial únicamente de la referencia.
     *
     * @return un nuevo objeto Reference cuya copia del valor es independiente a esta instancia
     * @throws CloneNotSupportedException si la clonación del valor falla
     */
    @Override
    public Reference<_Object> clone() throws CloneNotSupportedException {
        _Object val = this.getValue();
        _Object clonedVal = null;

        if (val instanceof Cloneable) {
            try {
                // Intentar invocar clone usando reflexión
                clonedVal = ( _Object ) val.getClass().getMethod("clone").invoke(val);
            } catch (Exception e) {
                throw new CloneNotSupportedException("No se pudo clonar el valor contenido: " + e.getMessage());
            }
        } else {
            // No implementa Cloneable, realizar copia superficial (misma referencia)
            clonedVal = val;
        }

        return new Reference<>(clonedVal, this.getDepth());
    }


    /**
     * Método que se llama automáticamente durante la serialización.
     * Permite personalizar qué se serializa y cómo.
     *
     * @param out objeto de salida para la serialización
     * @throws IOException si ocurre un error durante la serialización
     */
    @Serial
    private void writeObject(@NotNull java.io.ObjectOutputStream out) throws java.io.IOException {
        // Se serializa el valor, profundidad y cualquier otro campo necesario
        out.defaultWriteObject();
        out.writeObject(this.getValue()); // serializar solo el valor
        out.writeInt(this.getDepth());    // serializar la profundidad
    }

    /**
     * Método que se llama automáticamente durante la deserialización.
     * Permite personalizar cómo se reconstruye el objeto.
     *
     * @param in objeto de entrada para la deserialización
     * @throws IOException si ocurre un error durante la lectura
     * @throws ClassNotFoundException si no se encuentra la clase del valor serializado
     */
    @Serial
    private void readObject(@NotNull java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        // Reestáble los campos principales
        in.defaultReadObject();
        Object valorLeido = in.readObject();
        int profundidadLeida = in.readInt();

        // Asignar los valores
        this.value = new AtomicReference<>(( _Object ) valorLeido);
        this.depth = profundidadLeida;
    }

    /**
     * permita convertir tu Reference<_Object> a un Stream<_Object>
     * @return un objeto tipo Stream, del valor contenido en la referencia
     */
    public Stream<_Object> stream() {
        _Object val = this.getValue();
        return (val == null) ? Stream.empty() : Stream.of(val);
    }

    /**
     * Devuelve el valor más interno no referencia, desemvolviendo recursivamente si apunta a Reference.
     *
     * @return valor más interno no Reference o null
     */
    public _Object unwrap() {
        _Object current = this.getValue();
        while (current instanceof Reference) {
            current = ((Reference<_Object>) current).getValue();
        }
        return current;
    }

    /**
     * @return tamaño maximo del historial
     */
    public int getMaxHistorySize() {
        return maxHistorySize;
    }

    /**
     * Cambiar el tamaño maximo del historial
     * @param maxHistorySize tamaño nuevo para el historial
     */
    public void setMaxHistorySize(int maxHistorySize) {
        this.maxHistorySize = maxHistorySize;
    }

    private void addToHistory(_Object value) {
        if (value != null) {
            history.addLast(value);
            if (history.size() > maxHistorySize) {
                history.removeFirst();
            }
        }
    }

    /**
     * Devuelve un historial de los valores anteriores
     * @return la lista devuelta es inmodificable
     */
    public List<_Object> getHistory() {
        return Collections.unmodifiableList(history);
    }

    /**
     * Permite cambiar el valor, registrando el cabio en el historial
     * @param newValue nuevo valor a asignar
     */
    public void setValueWithHistory(_Object newValue) {
        if (locked) {
            throw new IllegalStateException("Reference está bloqueada y no puede modificarse");
        }
        value.set(newValue);
        addToHistory(newValue);
        notifyListeners(newValue);
    }



}
