package com.haeco.main;

import com.haeco.utils.CommonUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * haeco
 *
 * @author tolstoy.chen
 * @date 2023/8/22 10:10
 */

//Future的局限性
public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Future<String[]> filterWordFuture = executorService.submit(() -> {
            String str = CommonUtils.readFile("src/main/filter_word.txt");
            String[] filterWords = str.split(",");
            return filterWords;
        });

        //读取新闻稿
        Future<String> newsFuture = executorService.submit(() -> {
            return CommonUtils.readFile("src/main/news.txt");
        });

        //替换操作
        Future<String> replaeFuture = executorService.submit(() -> {
            String[] words = filterWordFuture.get();
            String news = newsFuture.get();
            for (String word : words) {

                if (news.indexOf(word) >= 0) {
                    System.out.println(word);
                    news = news.replace(word, "**");
                }
            }
            return news;
        });
        //TODO get()会一直阻塞等待结果
        String filteredNews = replaeFuture.get();
        System.out.println(filteredNews);

    }
}
