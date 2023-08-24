package com.haeco.parallelizm_completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        IntStream intStream = IntStream.range(0, 10);
        List<MyTask> tasks = intStream.mapToObj(item -> {
            return    new MyTask(1);
        }).collect(Collectors.toList());
        long start = System.currentTimeMillis();

        List<CompletableFuture<Integer>> futures = tasks.stream().map(myTask -> {
            return CompletableFuture.supplyAsync(() -> {
                return myTask.doWork();
            });
        }).collect(Collectors.toList());
        // step 3: 当所有任务完成时，获取每个异步任务的执行结果，存入List集合中
        List<Integer> rs = futures.stream().map(future -> {
            return future.join();
        }).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        double costTime = (end - start) / 1000.0;
        System.out.printf("processed %d tasks cost %.2f second",tasks.size(),costTime);
        //TODO 运行发现，两者使用的时间大致一样。能否进一步优化呢？
    }
}
