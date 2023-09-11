/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.CTB.fileImport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */

@Component
public class CsvReader {

    public CsvReader() {
        delimiter = ",";
        stringDelimiter = "";
        entries = new ArrayList<String[]>();
    }

    public ArrayList<String[]> getEntries() {
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

    public void Import(File file) {
        this.entries = new ArrayList<String[]>();
        BufferedReader br = null;
        String line = "";
        boolean firstLine = true;
        try {
            br = new BufferedReader(new FileReader(file));
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
