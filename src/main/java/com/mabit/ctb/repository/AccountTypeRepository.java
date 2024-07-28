package com.mabit.ctb.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.AccountType;

@Repository
public interface AccountTypeRepository extends CrudRepository<AccountType, Integer>
{
    /**
     *
     * @param type
     * @return
     */
    Optional<AccountType> findByName(String name);

}