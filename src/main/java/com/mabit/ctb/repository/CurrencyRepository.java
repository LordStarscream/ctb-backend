package com.mabit.ctb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String>
{
    /**
     *
     * @param name
     * @return
     */
    Currency findByName(String name);

    /**
     *
     * @param ticker
     * @return
     */
    Currency findByTicker(String ticker);
}
