package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 13:52
 */
public class ThenComposeDemo {
    public static CompletableFuture<String> readFileFuture(String fileName){
        return CompletableFuture.supplyAsync(()->{
            return CommonUtils.readFile(fileName);
        });
    }

    public static CompletableFuture<String[]> splitFuture(String context){
        return CompletableFuture.supplyAsync(()->{
            return context.split(",");
        });
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
            //TODO 有结果依赖的2个异步任务，用thenCompose
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CommonUtils.printThreadLog("main start");

        CompletableFuture<String[]> future = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取文件");
            String filterWords = CommonUtils.readFile("src/main/filter_word.txt");
            return filterWords;

        }).thenComposeAsync(content -> CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("切分敏感词");
            String[] words = content.split(",");
            return words;
        },executorService));

        CommonUtils.printThreadLog("main不会被阻塞");
        String[] strings = future.get();
        CommonUtils.printThreadLog(Arrays.toString(strings));
        CommonUtils.sleepSeconds(4);
        executorService.shutdown();
        CommonUtils.printThreadLog("main结束");
    }
}
