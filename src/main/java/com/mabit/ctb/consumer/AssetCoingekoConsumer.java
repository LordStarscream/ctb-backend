package com.mabit.ctb.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mabit.ctb.entity.Currency;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */

 @Slf4j
@Component
public class AssetCoingekoConsumer {

    @Value("${spring.application.exchange.coingecko.list.url}")
    private String listUrl;

    @Value("${spring.application.exchange.coingecko.coin.url}")
    private String url;

    @Value("${spring.application.exchange.coingecko.localization}")
    private String localization;

    private String currenciesJson;
    private String ratesJson;
    RestTemplate restTemplate = new RestTemplate();

    public AssetCoingekoConsumer(){
    }

    public AssetCoingekoConsumer(String url){
        this.url = url;
    }

    private void getCurrencyNames(){
        try{
            currenciesJson = restTemplate.getForObject(listUrl, String.class);
            log.debug("Currencies name Json: "+currenciesJson);
        }catch(Exception e){
            log.warn("No connection to "+listUrl);
        }
    }

    private void getCurrencyRates(String requestUrl){
        try{
            ratesJson = restTemplate.getForObject(requestUrl, String.class);
            log.info("requesting Json: "+ ratesJson);
        }catch(Exception e){
            log.warn("No connection to "+requestUrl+" possible");
        }
    }

    private String getRates(){
        return ratesJson;
    }

    private String getNames(){
        log.debug("CurrenciesJson: "+currenciesJson);
        return currenciesJson;
    }

    public String getCurrencyName(String ticker){
        if ((currenciesJson == null) || (currenciesJson.isEmpty())){
            getCurrencyNames();
        }

        String currencyName = null;
        JSONArray currencies = new JSONArray(this.getNames());

        for (Object o : currencies) {
            JSONObject currencyObj = (JSONObject) o;
            if ((currencyObj.getString("symbol").compareToIgnoreCase(ticker)) == 0) {
                currencyName = currencyObj.getString("name");
                break;
            }
        }
        return currencyName;
    }

    private String getCurrencyGeckoId(Currency currency) throws NullPointerException{
        if ((currenciesJson == null) || (currenciesJson.isEmpty())){
            getCurrencyNames();
        }

        String currencyId = null;
        JSONArray currencies = new JSONArray(this.getNames());


        for (Object o : currencies) {
            JSONObject currencyObj = (JSONObject) o;
            if ((currencyObj.getString("symbol").compareToIgnoreCase(currency.getTicker())) == 0) {
                currencyId = currencyObj.getString("id");
                break;
            }
        }
        if (currencyId == null)
            throw new NullPointerException("currencyId not defined");
        return currencyId.toLowerCase();
    }

    public Double getExchangeRateAtDate(Currency currency, LocalDateTime date) throws NullPointerException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String currencyName = getCurrencyGeckoId(currency);

        String request = url+currencyName+"/history?date=";
        request = request+date.format(formatter)+this.localization;
        getCurrencyRates(request);

        if( !(this.ratesJson == null || this.ratesJson.isEmpty())){
            JSONObject obj = new JSONObject(this.getRates());
            JSONObject rates = obj.getJSONObject("market_data").getJSONObject("current_price");
            Double result =rates.getDouble("eur");
            log.info("Rate from gecko json: "+result);
            return result;
        }
        throw new NullPointerException("No result delivered from "+request);
    }
}
