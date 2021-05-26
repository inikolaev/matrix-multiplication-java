import java.util.concurrent.*;

public class FastForkJoinPoolMatrix {
    static class MultiplyTask extends RecursiveTask<Void> {
        final int[][] a;
        final int[][] b;
        final int[][] c;
        final int i;
        final int j;

        public MultiplyTask(final int[][] a, final int[][] b, final int[][] c, final int i, final int j) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.i = i;
            this.j = j;
        }

        @Override
        protected Void compute() {
            if (i == j - 1) {
                row(c[i], a[i], b);
            } else {
                final int m = (i + j) >> 1;
                invokeAll(new MultiplyTask(a, b, c, i, m), new MultiplyTask(a, b, c, m, j));
            }

            return null;
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
        ForkJoinPool.commonPool().invoke(new MultiplyTask(a, t, c, 0, MatrixUtils.N));
        return c;
    }
}
