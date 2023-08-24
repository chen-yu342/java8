package com.haeco.parallelizm_completablefuture;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SequenceDemo {
    public static void main(String[] args) {
        IntStream intStream = IntStream.range(0, 10);
        List<MyTask> tasks = intStream.mapToObj(item -> {
         return    new MyTask(1);
        }).collect(Collectors.toList());
        // step 2: 执行tasks集合中的每个任务，统计总耗时
        long start = System.currentTimeMillis();
        List<Integer> rs = tasks.stream()
                .map(myTask -> {
                    return myTask.doWork();
                }).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        double costTime = (end - start) / 1000.0;
        System.out.printf("processed %d tasks cost %.2f second",tasks.size(),costTime);
    }
}
