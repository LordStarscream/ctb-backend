package com.mabit.ctb.service.cfd;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.cfd.Deal;
import com.mabit.ctb.repository.AccountRepository;
import com.mabit.ctb.repository.DealRepository;

@Service
public class TransactionService {

    @Autowired
    DealRepository dealRepository;

    @Autowired
    AccountRepository accountRepository;

    public Iterable<Deal> getDealsOfAccount() {
        return dealRepository.findAll();
    }

    public Iterable<Deal> getDealsOfAccount(Long accountId) throws TransactionServiceException{

        var account = accountRepository.findById(accountId);
        if (!account.isPresent())
            throw new TransactionServiceException("Account does not exist", null);
        else
            return dealRepository.findAllByAccount(account.get());

    }

    public void addDeal(Deal deal){
        dealRepository.save(deal);
    }

    public void addDeals(Iterable<Deal> deals){
        dealRepository.saveAll(deals);
    }

}
