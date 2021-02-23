package com.zebpay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ZebpayTickerService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private  URLValues urlValues;

    public ZebpayCoins getZebpayTickerDetails(String coin) {

        {
            ZebpayCoins b = new ZebpayCoins();
            System.out.println("ZebpayCoins message"+urlValues.getMessage());

            ResponseEntity<String> response=getTicker(coin);
            if(response.getStatusCode()== HttpStatus.OK) {

                try {
                    b.addCoin(getCoinDetails(coin, response));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }


                return b;
            }

            return b;

        }
    }


    private Coin getCoinDetails(String coin, ResponseEntity<String> response) throws JsonProcessingException {
        Coin c = new Coin();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        System.out.println("rootroot"+root.get("buy"));
          if (!root.isEmpty()) {

            JsonNode buy = root.get(ZebConstants.BUY);
            JsonNode sell = root.get(ZebConstants.SELL);

            c.setSell(sell.asDouble());
            c.setBuy(buy.asDouble());

            if (ZebConstants.BTC_INR.equals(coin)) {
                c.setCoin(ZebConstants.BTC_DESC);
            } else if (ZebConstants.BCH_INR.equals(coin)) {
                c.setCoin(ZebConstants.BCH_DESC);
            } else if (ZebConstants.LTC_INR.equals(coin)) {
                c.setCoin(ZebConstants.LTC_DESC);
            } else if (ZebConstants.ETH_INR.equals(coin)) {
                c.setCoin(ZebConstants.ETH_DESC);
            } else if (ZebConstants.XRP_INR.equals(coin)) {
                c.setCoin(ZebConstants.XRP_DESC);
            }
        }

          return  c;
    }

    private ResponseEntity<String> getTicker(String coin){

        return restTemplate.getForEntity(urlValues.getUrl()+coin+ZebConstants.SLASH+ZebConstants.TICKER,String.class);

    }
}
