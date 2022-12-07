package com.logistic.taxInvoice;

public class TaxInvoiceEntity {
    private String description ;
    private Float amountDueTax ;
    private Float amountWithoutTax ;

    public TaxInvoiceEntity(String description, Float amountDueTax, Float amountWithoutTax) {
        this.description = description;
        this.amountDueTax = amountDueTax;
        this.amountWithoutTax = amountWithoutTax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmountDueTax() {
        return amountDueTax;
    }

    public void setAmountDueTax(Float amountDueTax) {
        this.amountDueTax = amountDueTax;
    }

    public Float getAmountWithoutTax() {
        return amountWithoutTax;
    }

    public void setAmountWithoutTax(Float amountWithoutTax) {
        this.amountWithoutTax = amountWithoutTax;
    }
}
