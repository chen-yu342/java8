package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.concurrent.CompletableFuture;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 10:36
 */
public class RunAsyncDemo4 {
    public static void main(String[] args) {
        CommonUtils.printThreadLog("main start");

        //TODO lambda  如果是单核CPU，那么任务之间是宁发执行，多核CPU就是并行
        CompletableFuture.runAsync(() -> {
            CommonUtils.printThreadLog("读取文件");
            CommonUtils.readFile("src/main/news.txt");
            CommonUtils.printThreadLog("读取结束");
        });
        CommonUtils.printThreadLog("main不会被阻塞");
        CommonUtils.sleepSeconds(4);
        CommonUtils.printThreadLog("main结束");
    }
}
