package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.concurrent.CompletableFuture;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 10:36
 */
public class RunAsyncDemo3 {
    public static void main(String[] args) {
        CommonUtils.printThreadLog("main start");

        //TODO lambda
        CompletableFuture.runAsync(() -> {
            CommonUtils.printThreadLog("读取文件");
            CommonUtils.sleepSeconds(3);
            CommonUtils.printThreadLog("读取结束");
        });
        CommonUtils.printThreadLog("main不会被阻塞");
        CommonUtils.sleepSeconds(4);
        CommonUtils.printThreadLog("main结束");
    }
}
