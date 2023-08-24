package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

//TODO 异步任务的交互：将异步任务获取结果的速度相比较，按一定规则（先到先用）进行下一步处理
public class acceptEitherDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int a = new Random().nextInt(3);
            CommonUtils.sleepSeconds(a);
            CommonUtils.printThreadLog("任务1耗时" + a + "秒");
            return a;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int a = new Random().nextInt(3);
            CommonUtils.sleepSeconds(a);
            CommonUtils.printThreadLog("任务2耗时" + a + "秒");
            return a;
        });

        future1.acceptEither(future2, (result) -> {
            CommonUtils.printThreadLog("耗时" + result);
        });
        // 主线程休眠4秒，等待所有异步任务完成
        CommonUtils.sleepSeconds(4);

    }
}
