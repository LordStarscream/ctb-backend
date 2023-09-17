package com.mabit.ctb.file;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
@Slf4j
@Component
public class CsvReader {

    public CsvReader() {
        delimiter = ",";
        stringDelimiter = "";
        entries = new ArrayList<>();
    }

    public List<String[]> getEntries() {
        return entries;
    }

    private ArrayList<String[]> entries;

    private boolean containsHeader = true;

    private String[] header;

    private String delimiter;

    private String stringDelimiter;

    public boolean hasHeader() {
        return containsHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.containsHeader = hasHeader;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getStringDelimiter() {
        return stringDelimiter;
    }

    public void setStringDelimiter(String delimiter) {
        this.stringDelimiter = delimiter;
    }

    public String[] getHeader() {
        return header;
    }

    public void importFile(InputStream inputStream) {
        this.entries = new ArrayList<>();
        String line = "";
        boolean firstLine = true;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = br.readLine()) != null) {
                //use comma as separator
                String cleanedLine = line.replaceAll(stringDelimiter,"");
                String[] csvLine = cleanedLine.replaceAll(this.getStringDelimiter(), "").split(this.delimiter);
                if (firstLine) {
                    if (this.hasHeader())
                        header = csvLine;
                    firstLine = false;
                }
                else {
                    this.entries.add(csvLine);
                }
            }
        } catch (IOException e) {
            log.error("Import failed", e);
        }
    }

}
