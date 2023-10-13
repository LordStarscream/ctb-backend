package com.mabit.ctb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mabit.ctb.entity.Trade;
import com.mabit.ctb.entity.Transaction;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Iterable<Transaction> findByTrade(Trade trade);
    List<Transaction> findByOrderByDateTimeAsc();
}