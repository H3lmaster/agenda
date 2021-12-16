package com.has.agenda.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileServiceTest {

    @Autowired
    private FileService fileService;
    
    @Test
    public void writeToFileTest() {
        String jsonString = "{\"name\":\"Anthony\",\"surname\":\"Lewis\",\"phoneNumber\":\"7123123123\",\"email\":\"lewis@mail.com\",\"age\":0,\"category\":{\"yearsOfFriendship\":0}}";
        this.fileService.write(jsonString);
        List<String> fileContentList = this.fileService.read();
        assertThat(fileContentList).isNotNull();
        assertThat(fileContentList.size()).isGreaterThan(0);
    }
    
    @Test
    public void createFileTest() {
        File file = this.fileService.createFile();
        assertThat(file).isNotNull();
    }
    
    @Test
    public void readFileContentTest() {
        List<String> fileContentList = this.fileService.read();
        assertThat(fileContentList).isNotNull();
    }
    
    @Test
    public void clearFileTest() {
        this.fileService.clearContent();
        List<String> fileContentList = this.fileService.read();
        assertThat(fileContentList).isEmpty();
    }
}
