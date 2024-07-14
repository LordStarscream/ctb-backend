package com.mabit.ctb.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.repository.TransactionImportRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class FileImport {
    @Autowired
    private CsvReader csvReader;
    @Autowired
    private TransactionImportRepository importRepository;
    protected List<TransactionImport> importEntities;

    public abstract String getName();
    protected abstract TransactionImport entryToEntity(String[] entry);

    private List<TransactionImport> convertToEntities(List<String[]> entries) {
        List<TransactionImport> transactions = new ArrayList<>();
        for (String[] entry : entries) {
            var transactionImport = entryToEntity(entry);
            if (transactionImport != null)
                transactions.add(transactionImport);
        }
        return transactions;
    }

    private void persistImport() {
        if (!importEntities.isEmpty()) {
            importRepository.saveAll(this.importEntities);
        }
    }

    public void importInputStream(InputStream inputStream) {
        csvReader.setStringDelimiter("\"");
        var debugDelimiter = csvReader.getStringDelimiter();
        log.debug("csvReader.getStringDelimiter: "+debugDelimiter);
        csvReader.importFile(inputStream);
        List<String[]> entries = csvReader.getEntries();
        importEntities = convertToEntities(entries);
        persistImport();
    }

    public void importFile(File file) throws FileNotFoundException{
        importInputStream(new FileInputStream(file));
    }

    public void importMultipartFile(MultipartFile file) throws IOException{
        importInputStream(file.getInputStream());
    }

    @Override
    public String toString(){
        return getName();
    }
}
