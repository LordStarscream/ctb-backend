package com.mabit.ctb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.mabit.ctb.file.FileImport;

@Service
public class FileService {

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
}
