package com.mabit.ctb.repository;

import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.FiatExchangeRate;
import com.mabit.ctb.entity.Location;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Repository
public interface FiatExchangeRateRepository extends CrudRepository<FiatExchangeRate, Long> 
{

    FiatExchangeRate findByCryptoCurrencyAndFiatCurrencyAndExchangeAndDate(Currency cryptoCurrency, Currency fiatCurrency, Location exchange, LocalDateTime date);

};