package com.haeco.compare_price;

import com.haeco.utils.CommonUtils;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class ComparePriceService3 {
    // TODO 方案二：使用Future+线程池增强并行
    public PriceResult getCheapestPlatformPrice3(String productName){
        ExecutorService executor = Executors.newFixedThreadPool(5);

        CompletableFuture<PriceResult> taoBaoFuture = CompletableFuture.supplyAsync(() -> {
            PriceResult taoBaoPrice = HttpRequest.getTaoBaoPrice(productName);
            int taoBaoDiscount = HttpRequest.getTaoBaoDiscount(productName);
            return this.computeRealPrice(taoBaoPrice, taoBaoDiscount);
        }, executor);

        // 获取京东平台的价格和优惠
        CompletableFuture<PriceResult> jDongFuture = CompletableFuture.supplyAsync(() -> {
            PriceResult priceResult = HttpRequest.getJDongPrice(productName);
            int discount = HttpRequest.getJDongDiscount(productName);
            return this.computeRealPrice(priceResult, discount);
        }, executor);

        // 获取拼多多平台的价格和优惠
        CompletableFuture<PriceResult> pddFuture = CompletableFuture.supplyAsync(() -> {
            PriceResult priceResult = HttpRequest.getPDDPrice(productName);
            int discount = HttpRequest.getPDDDiscount(productName);
            return this.computeRealPrice(priceResult, discount);
        }, executor);

        return Stream.of(taoBaoFuture,jDongFuture,pddFuture)
                .map(CompletableFuture::join)
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
        ComparePriceService3 service3 = new ComparePriceService3();
        long start = System.currentTimeMillis();
        PriceResult iphone = service3.getCheapestPlatformPrice3("iphone");

        long end = System.currentTimeMillis();
        double costTime = (end - start) / 1000.0;
        System.out.printf("cost %.2f second \n",costTime);
        System.out.println(iphone);
        //cost 2.10 second

    }
}
