package com.haeco.parallelizm_completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureDemo2 {
    public static void main(String[] args) {
        IntStream intStream = IntStream.range(0, 10);
        List<MyTask> tasks = intStream.mapToObj(item -> {
            return    new MyTask(1);
        }).collect(Collectors.toList());

        //TODO CompletableFutures 比 ParallelStream 优点之一是你可以指定Executor去处理任务。你能选择更合适数量的线程。

        final int N_CPU = Runtime.getRuntime().availableProcessors();
        System.out.println("N_CPU==="+N_CPU);
        // TODO  设置线程池的数量最少是10个，最大是16个
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(tasks.size(), N_CPU * 2));

        long start = System.currentTimeMillis();

        List<CompletableFuture<Integer>> futures = tasks.stream().map(myTask -> {
            return CompletableFuture.supplyAsync(() -> {
                return myTask.doWork();
            },executorService);
        }).collect(Collectors.toList());
        // step 3: 当所有任务完成时，获取每个异步任务的执行结果，存入List集合中
        List<Integer> rs = futures.stream().map(future -> {
            return future.join();
        }).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        double costTime = (end - start) / 1000.0;
        System.out.printf("processed %d tasks cost %.2f second",tasks.size(),costTime);
        executorService.shutdown();
    }
}
