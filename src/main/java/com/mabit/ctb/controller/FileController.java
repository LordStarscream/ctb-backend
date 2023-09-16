package com.mabit.ctb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.service.TradeSevice;

public class FileController {
    @Autowired
    TradeSevice tradeService;

    @PostMapping("/file/import")
    public Iterable<Currency> getAllCurrencies() {
        return tradeService.GetAllCurrency();
    }

}
