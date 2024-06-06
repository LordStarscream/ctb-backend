package com.mabit.ctb.repository;

import com.mabit.ctb.entity.Account;
import com.mabit.ctb.entity.cfd.Deal;


import org.springframework.data.repository.CrudRepository;

public interface DealRepository extends CrudRepository<Deal, Long>{
    Iterable<Deal> findAllByAccount(Account account);
}