package io.github.desmonhak.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void getFirst() {
        Pair<Integer, String> par_valores = new Pair<>(1, "nuevo");
        System.out.println(par_valores);

        Pair<Integer, Pair<String, String>> trio_valores = new Pair<>(1, new Pair<>("nuevo", "nuevo"));
        System.out.println(trio_valores);
    }

}