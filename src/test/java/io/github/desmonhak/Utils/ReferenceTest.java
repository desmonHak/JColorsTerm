package io.github.desmonhak.Utils;

import org.junit.jupiter.api.Test;

import static io.github.desmonhak.Utils.Reference.ref;
import static org.junit.jupiter.api.Assertions.*;

class ReferenceTest {

    @Test
    void getValue() {
        Reference<String> ref = new Reference<>("hello");
        assertEquals("hello", ref.getValue());
        System.out.println("getValue(): " + ref);
    }

    @Test
    void setValue() {
        Reference<Integer> ref = new Reference<>(10);
        ref.setValue(20);
        assertEquals(20, ref.getValue());
        System.out.println("setValue(): " + ref);
    }

    @Test
    void testHashCodeConsistency() {
        String text = "test";
        Reference<String> ref1 = new Reference<>(text);
        Reference<String> ref2 = new Reference<>(text);

        assertEquals(ref1.hashCode(), ref2.hashCode());
        assertEquals(0, new Reference<String>(null).hashCode());

        System.out.println("hashCode ref1: " + ref1.hashCode());
        System.out.println("hashCode ref2: " + ref2.hashCode());
        System.out.println("hashCode null ref: " + new Reference<String>(null).hashCode());
    }

    @Test
    void testEquals() {
        Reference<String> ref1 = new Reference<>("value");
        Reference<String> ref2 = new Reference<>("value");
        Reference<String> ref3 = ref1;

        assertEquals(ref1, ref2);
        assertEquals(ref1, ref3);
        assertNotEquals(ref1, null);
        assertNotEquals(ref1, "value");

        Reference<String> refNull1 = new Reference<>(null);
        Reference<String> refNull2 = new Reference<>(null);
        assertEquals(refNull1, refNull2);

        System.out.println("Equals ref1 vs ref2: " + ref1.equals(ref2));
        System.out.println("Equals ref1 vs ref3: " + ref1.equals(ref3));
        System.out.println("Equals null refs: " + refNull1.equals(refNull2));
    }

    @Test
    void testToString() {
        Reference<Integer> ref = new Reference<>(123);
        System.out.println("toString(): " + ref);

        Reference<Integer> ref1 = new Reference<>(123);
        Reference<Reference<Integer>> ref2 = new Reference<>(ref1);
        Reference<Reference<Reference<Integer>>> ref3 = new Reference<>(ref2);

        System.out.println("Nested refs:");
        System.out.println(ref1);
        System.out.println(ref2);
        System.out.println(ref3);
    }

    @Test
    void testCompareTo() {
        Reference<Integer> ref1 = new Reference<>(10);
        Reference<Integer> ref2 = new Reference<>(20);
        Reference<Integer> ref3 = new Reference<>(10);
        Reference<Integer> refNull1 = new Reference<>(null);
        Reference<Integer> refNull2 = new Reference<>(null);

        assertTrue(ref1.compareTo(ref2) < 0);
        assertTrue(ref2.compareTo(ref1) > 0);
        assertEquals(0, ref1.compareTo(ref3));
        assertTrue(refNull1.compareTo(ref1) < 0);
        assertEquals(0, refNull1.compareTo(refNull2));

        System.out.println("CompareTo ref1 vs ref2: " + ref1.compareTo(ref2));
        System.out.println("CompareTo ref2 vs ref1: " + ref2.compareTo(ref1));
        System.out.println("CompareTo ref1 vs ref3: " + ref1.compareTo(ref3));
        System.out.println("CompareTo null vs ref1: " + refNull1.compareTo(ref1));
        System.out.println("CompareTo null vs null: " + refNull1.compareTo(refNull2));
    }

    @Test
    void testMap() throws CloneNotSupportedException {
        Reference<Integer> ref = new Reference<>(10);
        Reference<String> mapped = ref.map(i -> "Número: " + i);
        assertEquals("Número: 10", mapped.getValue());

        Reference<Integer> refNull = new Reference<>(null);
        Reference<String> mappedNull = refNull.map(i -> "Número: " + i);
        assertNull(mappedNull.getValue());

        System.out.println("Map result: " + mapped.getValue());
        System.out.println("Map null result: " + mappedNull.getValue());

        Reference<Integer> contador = new Reference<>(0);
        int nuevo = contador.updateAndGet((Reference.Action<Integer>) v -> (v == null) ? 1 : v.get() + 1);
        System.out.println("Nuevo contador: " + nuevo);

        int previo = contador.getAndUpdate(v -> (v == null) ? 1 : v + 1);
        System.out.println("Valor previo: " + previo);
        System.out.println("Valor actualizado: " + contador.getValue());

        Reference<Integer> reference = new Reference<>(10);
        reference.addListener(newValue -> System.out.println("(Listener) Valor cambió: " + newValue));

        reference.setValue(20);
        reference.updateAndGet((Reference.Action<Integer>) v -> v == null ? 1 : v.get() + 1);

        Reference<Integer> original = new Reference<>(2);
        Reference<Integer> copia = original.clone();
        System.out.println("Clone: " + copia);

        Reference<String> reference1 = new Reference<>("Hola Mundo");
        reference1.stream().map(String::toUpperCase).forEach(System.out::println);
    }

    @Test
    void testIsNull() {
        Reference<String> ref = new Reference<>(null);
        assertTrue(ref.isNull());
        ref.setValue("Hola");
        assertFalse(ref.isNull());
    }

    @Test
    void testHistoryTracking() {
        Reference<Integer> ref = new Reference<>(0, 0, true, false);
        ref.setValue(1);
        ref.setValue(2);
        ref.setValue(3);

        System.out.println("Historial de valores:");
        for (Integer i : ref.getHistory()) {
            System.out.println(i);
        }

        assertEquals(4, ref.getHistory().size());
        assertEquals(0, ref.getHistory().get(0));
        assertEquals(1, ref.getHistory().get(1));
    }

    @Test
    void testLocking() {
        Reference<String> ref = new Reference<>("inicial", false, false);
        ref.setValue("modificado");
        ref.lock();
        assertThrows(IllegalStateException.class, () -> ref.setValue("nuevo valor"));

        System.out.println("Valor tras bloqueo: " + ref.getValue());
    }
}
