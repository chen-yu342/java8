package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.concurrent.CompletableFuture;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 10:36
 */
public class RunAsyncDemo {
    public static void main(String[] args) {
        //回顾线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                CommonUtils.printThreadLog("读取文件");
                CommonUtils.sleepSeconds(3);
                CommonUtils.printThreadLog("读取结束");
            }
        });
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                CommonUtils.printThreadLog("读取文件");
                CommonUtils.sleepSeconds(3);
                CommonUtils.printThreadLog("读取结束");
            }
        });
        //TODO CompletableFuture其实就是开启一个多线程
    }
}
