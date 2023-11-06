package com.mabit.ctb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.report.Hold;
import com.mabit.ctb.entity.Currency;


@Repository
public interface HoldRepository extends CrudRepository<Hold, Long>
{
    List<Hold> findByInCurrency(Currency inCurrency);
}