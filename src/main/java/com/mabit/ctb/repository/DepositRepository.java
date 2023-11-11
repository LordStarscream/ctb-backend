package com.mabit.ctb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.report.Deposit;
import com.mabit.ctb.entity.Currency;


@Repository
public interface DepositRepository extends CrudRepository<Deposit, Long>
{
    List<Deposit> findByCurrency(Currency currency);
    List<Deposit> findByHasAvailableAmmount(boolean hasAvailableAmmount);
    //List<Deposit> findByHasAvailableAmmountAndCurrency(boolean hasAvailableAmmount, Currency currency);
    List<Deposit> findByHasAvailableAmmountAndCurrencyOrderByDateTime(boolean hasAvailableAmmount, Currency currency);
}