package com.mabit.ctb.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import com.mabit.ctb.service.FileService;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/file/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
        @RequestParam MultipartFile file,
        @RequestParam String importFormat) {
        try{
            fileService.importFile(file, importFormat);
            return ResponseEntity.ok("Datei erfolgreich hochgeladen und gespeichert");
        }catch(IOException ex){
            return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).body("Fehler beim Speichern der Datei: " + ex.getMessage());
        }
    }

}
