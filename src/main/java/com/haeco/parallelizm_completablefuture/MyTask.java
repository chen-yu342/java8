package com.haeco.parallelizm_completablefuture;

import com.haeco.utils.CommonUtils;

public class MyTask {
    private int duration;

    public MyTask(int duration) {
        this.duration = duration;
    }
    public int doWork(){
        CommonUtils.printThreadLog("do work");
        CommonUtils.sleepSeconds(duration);
        return duration;
    }
}
