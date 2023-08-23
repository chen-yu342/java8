package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.concurrent.CompletableFuture;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 11:37
 */
public class ThenRunDemo {
    public static void main(String[] args) {
        CommonUtils.printThreadLog("main start");
        CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取文件");
            String filterWords = CommonUtils.readFile("src/main/filter_word.txt");
            return filterWords;
        }).thenRun(()->{
            CommonUtils.printThreadLog("读取完成");
        });

        CommonUtils.printThreadLog("main 不会阻塞");
        CommonUtils.sleepSeconds(4);
        CommonUtils.printThreadLog("main end");
    }
}
