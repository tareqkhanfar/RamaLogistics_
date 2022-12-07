package com.logistic.FileTracker;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;

public class FileEntity {
    private String FileName ;
    private InputStream Data ;

    public FileEntity( String fileName, InputStream data) {
        FileName = fileName;
        Data = data;
    }

    public FileEntity() {

    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public InputStream getData() {
        return Data;
    }

    public void setData(InputStream data) {
        Data = data;
    }
}
