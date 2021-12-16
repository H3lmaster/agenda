package com.has.agenda.service;

import java.io.File;
import java.util.List;

public interface FileService {

    public File createFile();
    
    public void write(String data);
    
    public void clearContent();
    
    public List<String> read();
}
