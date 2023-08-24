package com.haeco.parallelizm_completablefuture;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelDemo {
    public static void main(String[] args) {
        IntStream intStream = IntStream.range(0, 10);
        List<MyTask> tasks = intStream.mapToObj(item -> {
            return    new MyTask(1);
        }).collect(Collectors.toList());
   //TODO 因为涉及 Stream API，而且存在耗时的长任务，所以，我们可以使用 `parallelStream()`
        long start = System.currentTimeMillis();
        List<Integer> rs = tasks.parallelStream()
                .map(myTask -> {
                    return myTask.doWork();
                }).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        double costTime = (end - start) / 1000.0;
        System.out.printf("processed %d tasks cost %.2f second",tasks.size(),costTime);
   //TODO 它花费了2秒多，因为此次并行执行使用了8个线程 (7个是ForkJoinPool线程池中的, 一个是 main 线程)

    }

}
