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
public class ThenApplyDemo2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //TODO 链式操作异步任务
        CommonUtils.printThreadLog("main start");
        CompletableFuture<String[]> rs = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取文件");
            String filterWords = CommonUtils.readFile("src/main/filter_word.txt");
            return filterWords;
        }).thenApply(content -> {
            CommonUtils.printThreadLog("切分敏感词");
            String[] words = content.split(",");
            return words;
        });

        CommonUtils.printThreadLog("main continue");
        String[] rs2 = rs.get();
        CommonUtils.printThreadLog(Arrays.toString(rs2));
        CommonUtils.printThreadLog("main end");

    }
}
