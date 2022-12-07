package com.logistic.FileTracker;

import java.io.InputStream;

public class FileTrackerEntity {
    private String clientName;
    private Integer FileNumber;
    private String openDate;
    private String arrivalDate;
    private String FileAmount;
    private String relaseDate;
    private String Balance;
    private String transferDate;
    private String transferAmount;
    private String sellingValue;
    private int client_id;
    ////////////////////////////////FILES////////////////////////////////////////////////////






    public FileTrackerEntity(int fileNumber, String clientName, String openDate, String arrivalDate, String relaseDate, String transferDate, String fileAmount, String sellingValue, String transferAmount, String balance) {
        this.clientName = clientName;
        FileNumber = fileNumber;
        this.openDate = openDate;
        this.arrivalDate = arrivalDate;
        FileAmount = fileAmount;
        this.relaseDate = relaseDate;
        Balance = balance;
        this.transferDate = transferDate;
        this.transferAmount = transferAmount;
        this.sellingValue = sellingValue;

    }

    public FileTrackerEntity() {

    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }



    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getFileNumber() {
        return FileNumber;
    }

    public void setFileNumber(Integer fileNumber) {
        FileNumber = fileNumber;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }


    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getFileAmount() {
        return FileAmount;
    }

    public void setFileAmount(String fileAmount) {
        FileAmount = fileAmount;
    }

    public String getRelaseDate() {
        return relaseDate;
    }

    public void setRelaseDate(String relaseDate) {
        this.relaseDate = relaseDate;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getSellingValue() {
        return sellingValue;
    }

    public void setSellingValue(String sellingValue) {
        this.sellingValue = sellingValue;
    }
}
