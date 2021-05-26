import java.util.Random;
import java.util.concurrent.CompletionService;

public class MatrixUtils {
    public static int N = 2000;

    public static int[][] emptyMatrix() {
        return new int[N][N];
    }

    public static int[][] randomMatrix() {
        final int[][] m = emptyMatrix();
        final Random r = new Random();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                m[i][j] = r.nextInt(1024);
            }
        }

        return m;
    }

    public static int[][] transpose(int a[][]) {
        int b[][] = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                b[i][j] = a[j][i];
                b[j][i] = a[i][j];
            }
        }

        return b;
    }

    public static void join(final CompletionService<?> completionService) {
        for (int k = 0; k < MatrixUtils.N; k++) {
            try {
                completionService.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
