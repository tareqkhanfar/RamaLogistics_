package com.logistic.CreditNote;

public class CreditNoteEntity {
    private String discription ;
    private String value ;

    public CreditNoteEntity( String discription, String value) {

        this.discription = discription;
        this.value = value;
    }


    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
