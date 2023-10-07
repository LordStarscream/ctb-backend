package com.mabit.ctb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.file.FileImport;
import com.mabit.ctb.repository.TransactionImportRepository;

@Service
public class FileService {

    @Autowired
    private  TransactionImportRepository transactionImportRepository;
    @Autowired // inject all import implementations
    private List<FileImport> importServices;

    private FileImport getFileImporter(String name){
        for (FileImport fileImport : importServices) {
            if (fileImport.getName().equals(name)){
                return fileImport;
            }
        }
        return null;
    }

    public void importFile(MultipartFile file, String format) throws IOException{
        var fileImporter = getFileImporter(format);

        if(fileImporter == null)
            throw new IOException("Importer <"+format+"> does not exist");

        fileImporter.importMultipartFile(file);
    }

    public Iterable<TransactionImport> getAllTransactionImports(){
        return transactionImportRepository.findAll();
    }
}
