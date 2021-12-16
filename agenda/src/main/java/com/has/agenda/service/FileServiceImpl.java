package com.has.agenda.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private static final String EMPTY = "";

    @Value("${agenda.filename}")
    private String fileName;

    @Override
    public File createFile() {
        File file = new File(fileName);

        if ( !file.exists() ) {

            try {
                file.createNewFile();
            } catch (Exception ex) {
                logger.error("Exception when creating the file...", ex);
            }
        }
        
        return file;
    }

    @Override
    public void write(String data) {
        this.write(data, true);
    }    
    
    private void write(String data, boolean append) {

        try {
            this.createFile();
            FileWriter fileWriter = new FileWriter(fileName, append);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(data.toString());
            if ( !EMPTY.equals(data) ) {
                bufferWriter.newLine();
            }
            bufferWriter.close();
            fileWriter.close();
            logger.debug("File saved successfully.");
        } catch (IOException ex) {
            logger.error("An error occurred when saving to the file...", ex);
        }

    }

    @Override
    public List<String> read() {
        List<String> fileContentList = new ArrayList<>(); 
        Scanner reader = null;
        
        try {
        
            File file = new File(fileName);
            reader = new Scanner(file);
            
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if ( !Objects.isNull(line) && !EMPTY.equals(line)) {
                    fileContentList.add(line);
                }
            }
        
        } catch (Exception ex) {
            logger.error("An error occurred while reading the file...", ex);
        } finally {
            if ( reader != null ) {
                reader.close();
            }
        }
        
        return fileContentList;
    }

    @Override
    public void clearContent() {
        this.write(EMPTY, false);   
    }

}
