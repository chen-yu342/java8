package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 11:16
 */
public class ThenApplyDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");
        CompletableFuture<String> filterWordsFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取文件");
            String filterWords = CommonUtils.readFile("src/main/filter_word.txt");
            return filterWords;
        });

        CompletableFuture<String[]> fileWords = filterWordsFuture.thenApply(content -> {
            CommonUtils.printThreadLog("切分敏感词");
            String[] words = content.split(",");
            return words;
        });

        CommonUtils.printThreadLog("main continue");
        String[] rs = fileWords.get();
        CommonUtils.printThreadLog(Arrays.toString(rs));
        CommonUtils.printThreadLog("main end");

    }
}
