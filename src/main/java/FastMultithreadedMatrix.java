import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FastMultithreadedMatrix {
    final static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {
        final int[][] a = MatrixUtils.randomMatrix();
        final int[][] b = MatrixUtils.randomMatrix();
        final int[][] c = MatrixUtils.emptyMatrix();

        final long start = System.currentTimeMillis();
        multiply(c, a, b);
        final long end = System.currentTimeMillis();
        System.out.printf("Elapsed time: %fs\n", (end - start) / 1000f);

        executor.shutdown();
    }

    public static int[][] multiply(int c[][], int a[][], int b[][]) {
        final int t[][] = MatrixUtils.transpose(b);

        final CompletionService<Void> completionService = new ExecutorCompletionService<>(executor);

        for (int k = 0; k < MatrixUtils.N; k++) {
            final int row = k;
            completionService.submit(() -> {
                row(c[row], a[row], t);
                return null;
            });
        }

        MatrixUtils.join(completionService);

        return c;
    }

    private static void row(int c[], int a[], int b[][]) {
        for (int k = 0; k < MatrixUtils.N; k++) {
            c[k] = cell(a, b, k);
        }
    }

    private static int cell(int a[], int b[][], int j) {
        int result = 0;
        for (int k = 0; k < MatrixUtils.N; k++) {
            result += a[k] * b[j][k];
        }
        return result;
    }
}
