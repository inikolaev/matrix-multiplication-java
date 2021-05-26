public class NaiveMatrix {
    public static void main(String[] args) {
        final int[][] a = MatrixUtils.randomMatrix();
        final int[][] b = MatrixUtils.randomMatrix();
        final int[][] c = MatrixUtils.emptyMatrix();

        final long start = System.currentTimeMillis();
        multiply(c, a, b);
        final long end = System.currentTimeMillis();
        System.out.printf("Elapsed time: %fs\n", (end - start) / 1000f);
    }

    public static int[][] multiply(int c[][], int a[][], int b[][]) {
        for (int k = 0; k < MatrixUtils.N; k++) {
            row(c[k], a[k], b);
        }

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
            result += a[k] * b[k][j];
        }
        return result;
    }
}
