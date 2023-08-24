package com.haeco.compare_price;

import com.haeco.utils.CommonUtils;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class ComparePriceService {
      // TODO 方案一：串行方式操作商品比价
    public PriceResult getCheapestPlatformPrice(String productName){
        PriceResult priceResult;
        int discount;

        PriceResult taoBaoPrice = HttpRequest.getTaoBaoPrice(productName);
        int taoBaoDiscount = HttpRequest.getTaoBaoDiscount(productName);
        PriceResult taoBaoPriceResult = this.computeRealPrice(taoBaoPrice, taoBaoDiscount);

        // 获取京东平台的价格和优惠
        priceResult = HttpRequest.getJDongPrice(productName);
        discount = HttpRequest.getJDongDiscount(productName);
        PriceResult jDongPriceResult = this.computeRealPrice(priceResult, discount);

        // 获取拼多多平台的价格和优惠
        priceResult = HttpRequest.getPDDPrice(productName);
        discount = HttpRequest.getPDDDiscount(productName);
        PriceResult pddPriceResult = this.computeRealPrice(priceResult, discount);
        Optional<PriceResult> min = Stream.of(taoBaoPriceResult, jDongPriceResult, pddPriceResult)
                .min(Comparator.comparing(PriceResult::getRealPrice));
        return min.get();

    }
    public PriceResult computeRealPrice(PriceResult priceResult,int discount){
        priceResult.setRealPrice(priceResult.getPrice()-discount);
        priceResult.setDiscount(discount);
        CommonUtils.printThreadLog(priceResult.getPlatform()+"final price is:"+priceResult.getRealPrice());
        return priceResult;
    }

    public static void main(String[] args) {
        ComparePriceService comparePriceService = new ComparePriceService();
        long start = System.currentTimeMillis();
        PriceResult priceResult = comparePriceService.getCheapestPlatformPrice("iphone");
        long end = System.currentTimeMillis();
        double costTime = (end - start) / 1000.0;
        System.out.printf("cost %.2f second \n",costTime);
        System.out.println("priceResult = " + priceResult);
    }
}
