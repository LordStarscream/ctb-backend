package com.mabit.ctb.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mabit.ctb.entity.TransactionImport;
import com.mabit.ctb.file.FileImport;
import com.mabit.ctb.service.FileService;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired // inject all import implementations
    private List<FileImport> importServices;

    @PostMapping(value = "/file/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadFile(
        @RequestParam MultipartFile file,
        @RequestParam String importFormat) {
        try{
            fileService.importFile(file, importFormat);
            return ResponseEntity.ok(createJsonResponse("Datei erfolgreich hochgeladen und gespeichert"));
        }catch(IOException ex){
            return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).body(createJsonResponse("Fehler beim Speichern der Datei: " + ex.getMessage()));
        }
    }

    private Map<String,String> createJsonResponse(String message){
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }

    @GetMapping("/file/importTypes")
    public List<String> getImportTypes(){
        return importServices.stream()
            .map(FileImport::getName)
            .collect(Collectors.toList());
    }

    @GetMapping("/file/transactionImports")
    public Iterable<TransactionImport> getTransactionImports(){
        return fileService.getAllTransactionImports();
    }

}
