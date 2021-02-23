package com.zebpay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("zebpay")
@RefreshScope
public class ZebPayRestController {

    HashMap<String ,Coin> coinMap= new HashMap<>();
    @Autowired
    private  ZebpayTickerService service;

    @RequestMapping("/ticker/{coin}")
    public HashMap getAllCoinsDetails(@PathVariable("coin") String coin){
        System.out.println("Zebpay Coin"+coin);

        ZebpayCoins zebpayCoins=this.service.getZebpayTickerDetails(coin);
        if(zebpayCoins.getCoins().size()>0)
            coinMap.put(coin,zebpayCoins.getCoins().get(0));

        return coinMap               ;

    }

}
