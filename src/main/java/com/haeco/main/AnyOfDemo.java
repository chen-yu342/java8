package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.concurrent.CompletableFuture;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/23 10:18
 */
public class AnyOfDemo {
    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            CommonUtils.sleepSeconds(2);
            return "future1";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            CommonUtils.sleepSeconds(1);
            return "future2";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            CommonUtils.sleepSeconds(3);
            return "future3";
        });
        CompletableFuture<Object> rsFuture = CompletableFuture.anyOf(future1, future2, future3);
        Object rs = rsFuture.join();
        CommonUtils.printThreadLog("rs=="+rs);
    }
}
