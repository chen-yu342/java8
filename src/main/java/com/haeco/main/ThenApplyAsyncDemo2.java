package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 13:38
 */
public class ThenApplyAsyncDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");
        CompletableFuture<String[]> future = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取文件");
            String filterWords = CommonUtils.readFile("src/main/filter_word.txt");
            return filterWords;
        }).thenApply(content -> {
            CommonUtils.printThreadLog("切分敏感词");
            String[] words = content.split(",");
            return words;
        });
        CommonUtils.printThreadLog("main不会被阻塞");
        String[] strings = future.get();
        CommonUtils.printThreadLog(Arrays.toString(strings));
        CommonUtils.sleepSeconds(4);
        CommonUtils.printThreadLog("main结束");
        /*TODO 一般而言，commanpool为了提高性能,supplyAsync,thenApply是同一个线程*/

    }
}
