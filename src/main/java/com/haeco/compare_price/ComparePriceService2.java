package com.haeco.compare_price;

import com.haeco.utils.CommonUtils;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class ComparePriceService2 {
    // TODO 方案二：使用Future+线程池增强并行
    public PriceResult getCheapestPlatformPrice(String productName){
        ExecutorService executor = Executors.newFixedThreadPool(5);

        Future<PriceResult> taoBaoFuture = executor.submit(() -> {
            PriceResult taoBaoPrice = HttpRequest.getTaoBaoPrice(productName);
            int taoBaoDiscount = HttpRequest.getTaoBaoDiscount(productName);
            return this.computeRealPrice(taoBaoPrice, taoBaoDiscount);
        });
        // 获取京东平台的价格和优惠
        Future<PriceResult> jDongFuture = executor.submit(() -> {
            PriceResult priceResult = HttpRequest.getJDongPrice(productName);
            int discount = HttpRequest.getJDongDiscount(productName);
            return this.computeRealPrice(priceResult, discount);
        });

        // 获取拼多多平台的价格和优惠
        Future<PriceResult> pddFuture = executor.submit(() -> {
            PriceResult priceResult = HttpRequest.getPDDPrice(productName);
            int discount = HttpRequest.getPDDDiscount(productName);
            return this.computeRealPrice(priceResult, discount);
        });
        return Stream.of(taoBaoFuture,jDongFuture,pddFuture)
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }finally {
                        executor.shutdown();
                    }
                })
                .filter(Objects::nonNull)
                .min(Comparator.comparing(PriceResult::getRealPrice))
                .get();

    }

    public PriceResult computeRealPrice(PriceResult priceResult,int discount){
        priceResult.setRealPrice(priceResult.getPrice()-discount);
        priceResult.setDiscount(discount);
        CommonUtils.printThreadLog(priceResult.getPlatform()+"final price is:"+priceResult.getRealPrice());
        return priceResult;
    }
    public static void main(String[] args) {
        ComparePriceService2 service2 = new ComparePriceService2();
        long start = System.currentTimeMillis();
        PriceResult iphone = service2.getCheapestPlatformPrice("iphone");

        long end = System.currentTimeMillis();
        double costTime = (end - start) / 1000.0;
        System.out.printf("cost %.2f second \n",costTime);
        System.out.println(iphone);
        //cost 2.10 second

    }
}
