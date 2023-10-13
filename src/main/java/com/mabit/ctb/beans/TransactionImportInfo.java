package com.mabit.ctb.beans;

import java.util.ArrayList;
import java.util.List;

import com.mabit.ctb.entity.Currency;
import com.mabit.ctb.entity.TransactionImport;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionImportInfo {

    private TransactionImport transactionImport;
    private List<Currency> currency = new ArrayList<>();
    private boolean importSuccess;
}
