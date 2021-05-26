import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FalseSharing {
    final static int cpus = Runtime.getRuntime().availableProcessors();
    final static long[] longs = new long[cpus * 8];

    private static class Worker implements Callable<Double> {
        private final int index;

        public Worker(int index) {
            this.index = index;
        }

        @Override
        public Double call() throws Exception {
            long start = System.nanoTime();
            while (!Thread.currentThread().isInterrupted()) {
                longs[index]++;
            }
            long end = System.nanoTime();
            long duration = end - start;

            return (double) longs[index] / duration;
        }
    }

    public static void run(int step, int threads, Duration duration) throws InterruptedException {
        final ExecutorService executor = Executors.newFixedThreadPool(cpus);

        Arrays.fill(longs, 0);

        final List<Future<Double>> results = IntStream.range(0, threads).mapToObj(i -> new Worker(i * step)).map(executor::submit).collect(Collectors.toList());

        Thread.sleep(duration.toMillis());

        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        final OptionalDouble mean = results.stream().filter(Future::isDone).mapToDouble(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                return 0;
            }
        }).average();

        System.out.println("" + step + "," + threads + "," + mean.getAsDouble());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("step,threads,ops");
        for (int step = 1; step <= 8; step++) {
            for (int threads = 1; threads <= cpus; threads *= 2) {
                run(step, threads, Duration.ofSeconds(60));
            }
        }
    }
}
