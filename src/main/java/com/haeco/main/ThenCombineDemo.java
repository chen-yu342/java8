package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.concurrent.CompletableFuture;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 14:18
 */
public class ThenCombineDemo {
    //TODO 编排两个非依赖关系的异步任务
    public static void main(String[] args) {
        CommonUtils.printThreadLog("main start");

        CompletableFuture<String[]> future1 = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取敏感词并解析");
            String context = CommonUtils.readFile("src/main/filter_word.txt");
            String[] arr = context.split(",");
            return arr;
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("读取news文件");
            String context = CommonUtils.readFile("src/main/news.txt");
            return context;
        });
        CompletableFuture<String> rsfuture = future1.thenCombine(future2, (words, context) -> {
            CommonUtils.printThreadLog("替换操作");
            for (String word : words) {
                if (context.indexOf(word) > -1) {
                    context = context.replace(word, "**");
                }
            }
            return context;
        });
        String rs = rsfuture.join();
        CommonUtils.printThreadLog("rs=="+rs);
        CommonUtils.printThreadLog("main 不会阻塞");
        CommonUtils.sleepSeconds(4);
        CommonUtils.printThreadLog("main end");
    }
}
