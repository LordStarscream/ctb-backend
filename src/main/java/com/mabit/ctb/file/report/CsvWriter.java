package com.mabit.ctb.file.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
public class CsvWriter {

    public CsvWriter() {
        delimiter = ",";
        stringDelimiter = "";
        entries = new ArrayList<>();
    }

    private List<String[]> entries;

    private String[] header;

    private String delimiter;

    private String stringDelimiter;

    public List<String[]> getEntries() {
        return entries;
    }

    public void setEntries(List<String[]> entries) {
        this.entries = entries;
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

    public void setHeader(String[] header) {
        this.header = header;
    }

    public byte[] export() {
        if (header != null)
            this.entries.add(0, header);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            for(String[] entry:this.entries) {
                StringBuilder line = new StringBuilder();
                for (String s: entry){
                    line.append(getStringDelimiter()).append(s).append(getStringDelimiter()).append(getDelimiter());
                }
                line.delete(line.length()-1, line.length());
                outputStream.write(line.toString().getBytes(StandardCharsets.UTF_8));
                outputStream.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
           log.error("Error Exporting to CSV in byte[]", e);
            return new byte[0];
        }
    }

}
