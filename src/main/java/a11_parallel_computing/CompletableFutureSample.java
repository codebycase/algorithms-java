package a11_parallel_computing;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureSample {
    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(5000);
            completableFuture.complete("Hello");
            return null;
        });
        return completableFuture;
    }

    private static void supplyAsyncSample() throws InterruptedException, ExecutionException {
        // Run a task specified by a Supplier object asynchronously
        /*
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                return "Result of the asynchronous computation";
            }
        });
        */

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "Hello";
        });

        // Block and get the result of the Future
        String result = future.get();
        System.out.println(result);
    }

    public static void chainFutures() throws Exception {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");
        assert "Hello World".equals(future.get());
    }

    public static void combineFutures() throws Exception {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
        assert "Hello World".equals(completableFuture.get());
    }

    public static void combineFutures2() throws Exception {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);
        assert "Hello World".equals(completableFuture.get());
    }

    public static void runMultipleFutures() throws Exception {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");
        String combined = Stream.of(future1, future2, future3).map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
        assert "Hello Beautiful World".equals(combined);
    }

    public static void handlingErrors() throws InterruptedException, ExecutionException {
        String name = null;
        @SuppressWarnings("unused")
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException("Computation Error!");
            }
            return "Hello, " + name;
        }).handle((s, t) -> s != null ? s : "Hello, Stranger!");

        // An alternative way to throw an exception!
        completableFuture.completeExceptionally(new RuntimeException("Calculation failed!"));

        assert "Hello, Stranger!".equals(completableFuture.get());
    }

    public static void main(String[] args) throws Exception {
        chainFutures();
        combineFutures();
        combineFutures2();
        runMultipleFutures();
        handlingErrors();
    }
}
