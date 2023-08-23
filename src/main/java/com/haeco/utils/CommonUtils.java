package com.haeco.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 9:58
 */
public class CommonUtils {

    public static String readFile(String pathFile){
        try {
            return Files.readAllLines(Paths.get(pathFile)).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleepMills(long mills){
        try {
            TimeUnit.MILLISECONDS.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleepSeconds(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printThreadLog(String message){
        //时间戳|线程id|线程名|日志信息
        String result = new StringJoiner("|")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.format("%2d", Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(message)
                .toString();
        System.out.println(result);
    }
}
