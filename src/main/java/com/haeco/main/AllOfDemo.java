package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/23 9:26
 */
public class AllOfDemo {
    //TODO allOf anyOf合并多个异步任务
    public static CompletableFuture<String> readFile(String pathFile) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            String context = CommonUtils.readFile(pathFile);
            return context;
        });
        return future;
    }

    public static void main(String[] args) {
        CommonUtils.printThreadLog("main start");
        List<String> fileNames = Arrays.asList("src/main/news1.txt", "src/main/news2.txt", "src/main/news3.txt");


        List<CompletableFuture<String>> futuresList = fileNames.stream()
                .map(AllOfDemo::readFile).collect(Collectors.toList());
        //TODO 把list转成数组待用，以便传入allOf方法中
        int size = futuresList.size();
        CompletableFuture[] completableFutures = futuresList.toArray(new CompletableFuture[size]);

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(completableFutures);

        CompletableFuture<Long> countFuture = allFutures.thenApply(v -> {
            //TODO 所有异步任务都已经完成了，调用这些异步任务完成的futuresList
            return futuresList.stream().map(CompletableFuture::join).filter(content -> content.contains("tmd"))
                    .count();
        });
        Long rs = countFuture.join();
        CommonUtils.printThreadLog("rs=="+rs);
        CommonUtils.printThreadLog("main 不会阻塞");
        CommonUtils.sleepSeconds(4);
        CommonUtils.printThreadLog("main end");
    }
}
