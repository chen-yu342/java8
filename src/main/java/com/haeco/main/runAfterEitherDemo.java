package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

//TODO 异步任务的交互：将异步任务获取结果的速度相比较，按一定规则（先到先用）进行下一步处理
public class runAfterEitherDemo {
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
      //TODO 如果不关心最先到达的结果，只想在有一个异步任务先完成时得到完成的通知
        future1.runAfterEither(future2, () -> {
            CommonUtils.printThreadLog("job end.....");
        });
        // 主线程休眠4秒，等待所有异步任务完成
        CommonUtils.sleepSeconds(4);

    }
}
