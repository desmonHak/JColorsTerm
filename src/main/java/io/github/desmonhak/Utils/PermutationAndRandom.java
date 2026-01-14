package io.github.desmonhak.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.*;

public class PermutationAndRandom {

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

    private static class PermutationTask extends RecursiveTask<List<String>> {
        private final char[] arr;
        private final int k;

        public PermutationTask(char[] arr, int k) {
            this.arr = arr;
            this.k = k;
        }

        @NotNull
        @Override
        protected List<String> compute() {
            int n = arr.length;
            List<String> result = new ArrayList<>();
            if (k == n) {
                result.add(new String(arr));
            } else {
                List<PermutationTask> subtasks = new ArrayList<>();
                for (int i = k; i < n; i++) {
                    swap(arr, i, k);
                    PermutationTask subtask = new PermutationTask(arr.clone(), k + 1);
                    subtask.fork();
                    subtasks.add(subtask);
                    swap(arr, i, k); // backtrack
                }
                for (PermutationTask task : subtasks) {
                    result.addAll(task.join());
                }
            }
            return result;
        }
    }

    private static void swap(@NotNull char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static Optional<List<String>> createPermutation(String string) {
        if (string == null) throw new NullPointerException("string == null");
        if (string.isEmpty()) return Optional.empty();

        char[] chars = string.toCharArray();

        List<String> result = forkJoinPool.invoke(new PermutationTask(chars, 0));

        forkJoinPool.shutdown();

        return Optional.of(result);
    }
}
