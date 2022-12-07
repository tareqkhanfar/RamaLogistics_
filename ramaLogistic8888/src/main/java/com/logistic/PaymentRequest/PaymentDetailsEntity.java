package com.logistic.PaymentRequest;

public class PaymentDetailsEntity {
    private String Discription ;
    private float price ;
    private  float amount ;


    public PaymentDetailsEntity(String discription, float price, float amount) {
        Discription = discription;
        this.price = price;
        this.amount = amount;

    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }


}
