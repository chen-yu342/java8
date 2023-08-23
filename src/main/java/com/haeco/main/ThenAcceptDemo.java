package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 11:31
 */
public class ThenAcceptDemo {
    public static void main(String[] args) {
        CommonUtils.printThreadLog("main stat");

        CompletableFuture<Void> thenAccept = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取文件");
            String filterWords = CommonUtils.readFile("src/main/filter_word.txt");
            return filterWords;
        }).thenApply(content -> {
            String[] strs = content.split(",");
            return strs;
        }).thenAccept(words -> {
            CommonUtils.printThreadLog("laile....."+Arrays.toString(words));
        });

        CommonUtils.printThreadLog("main不会被阻塞");
        CommonUtils.sleepSeconds(4);
        CommonUtils.printThreadLog("main结束");
    }
}
