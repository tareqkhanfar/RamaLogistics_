package com.logistic.Client;

public class ClientEntity {

    private int id ;
    private String name ;
    private String address ;
    private String email ;
    private String phone ;
    private  String telefx ;



    public ClientEntity(int id, String name, String address,String phone , String telefx , String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.telefx = telefx;
    }

    public ClientEntity() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelefx() {
        return telefx;
    }

    public void setTelefx(String telefx) {
        this.telefx = telefx;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", telefx=" + telefx +
                '}';
    }


}
