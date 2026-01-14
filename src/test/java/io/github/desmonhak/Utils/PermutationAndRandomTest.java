package io.github.desmonhak.Utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static io.github.desmonhak.Utils.PermutationAndRandom.createPermutation;
import static org.junit.jupiter.api.Assertions.*;

class PermutationAndRandomTest {

    @Test
    void createPermutationTest() throws ExecutionException, InterruptedException, IOException {
        List<String> permu =  createPermutation("hola").get();
        IntStream.range(0, permu.size()).forEach(num -> {
            System.out.printf ("%03d -> %s\n", num, permu.get(num));
        });
    }
}