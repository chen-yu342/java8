package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 10:55
 */
public class SupplyAsyncDemo2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");
        //TODO lambda
        CompletableFuture<String> newsFuture = CompletableFuture.supplyAsync(()-> {
                String news = CommonUtils.readFile("src/main/news.txt");
                return news;

        });
        CommonUtils.printThreadLog("main 不会阻塞");
        //TODO get()会不会阻塞呢？回调函数
        String news = newsFuture.get();
        CommonUtils.printThreadLog(news);
        CommonUtils.printThreadLog("main end");
    }
}
