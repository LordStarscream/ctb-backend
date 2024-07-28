package com.mabit.ctb.repository;

import com.mabit.ctb.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>{
    Iterable<Account> findByIsCryptoAccount(Boolean isCrypto);
}