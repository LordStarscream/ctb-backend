package com.mabit.ctb.service.cfd;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabit.ctb.entity.Account;
import com.mabit.ctb.entity.cfd.Deal;
import com.mabit.ctb.entity.cfd.DealDto;
import com.mabit.ctb.entity.cfd.EntryType;
import com.mabit.ctb.entity.cfd.TradeType;
import com.mabit.ctb.repository.AccountRepository;
import com.mabit.ctb.repository.DealRepository;
import com.mabit.ctb.utils.StringUtils;

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

    public Deal dtoToDeal(DealDto dealDto){
        Deal deal = new Deal();
        return updateDeal(deal, dealDto);
    }

    public Deal updateDeal(Deal deal, DealDto dealDto){
        deal.setId(dealDto.getId());
        if(dealDto.getAccountId() != null){
            Account account = accountRepository.findById(dealDto.getAccountId()).get();
            deal.setAccount(account);
        }
        deal.setOpenTime(translateToDate(dealDto.getOpenTime()));
        deal.setType(TradeType.valueOf(dealDto.getType().trim()));
        deal.setSize(dealDto.getSize());
        deal.setItem(dealDto.getItem());
        deal.setPrice(dealDto.getPrice());
        deal.setOrderNumber(dealDto.getOrderNumber());
        deal.setComment(dealDto.getComment());
        deal.setEntry(EntryType.valueOf(dealDto.getEntry().trim()));
        deal.setCommission(dealDto.getCommission());
        deal.setSwap(dealDto.getSwap());
        deal.setProfit(dealDto.getProfit());
        return deal;
    }

    public void addDeal(DealDto deal){
        if (deal.getId() == null){
            dealRepository.save(dtoToDeal(deal));
        }
        else{
            Optional<Deal> dbDeal = dealRepository.findById(deal.getId());
            if(dbDeal.isPresent()){
                Deal updateDeal = updateDeal(dbDeal.get(), deal);
                dealRepository.save(updateDeal);
            }else{
                deal.setId(null);
                dealRepository.save(dtoToDeal(deal));
            }
        }
    }

    public void addDeals(Iterable<Deal> deals){
        dealRepository.saveAll(deals);
    }

    public void updateDeals(List<DealDto> dealsDto){
        for (DealDto dealDto : dealsDto) {
            addDeal(dealDto);
        }
    }

    private LocalDateTime translateToDate(String dateString){
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;

        if (StringUtils.isNullOrEmpty(dateString))
            return null;

        try {
            formatter = DateTimeFormatter.ISO_DATE_TIME;
            dateTime = LocalDateTime.parse(dateString.trim(), formatter);
        } catch (Exception ex) {
            formatter = DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm:ss");
            dateTime = LocalDateTime.parse(dateString.trim(), formatter);
        }
        return dateTime;
    }
}
