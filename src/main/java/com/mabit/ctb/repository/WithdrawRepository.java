package com.mabit.ctb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.report.Deposit;
import com.mabit.ctb.entity.report.Withdraw;
import com.mabit.ctb.entity.Currency;


@Repository
public interface WithdrawRepository extends CrudRepository<Withdraw, Long>
{
    List<Deposit> findByInCurrency(Currency currency);
}