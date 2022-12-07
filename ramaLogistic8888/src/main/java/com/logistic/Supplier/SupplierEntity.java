package com.logistic.Supplier;

public class SupplierEntity {

    private int id ;
    private String name ;
    private String address ;
    private String email ;
    private String phone ;
    private  String telefx ;



    public SupplierEntity(int id, String name, String address,String phone , String telefx , String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.telefx = telefx;
    }

    public SupplierEntity() {

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
        return "SupplierEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", telefx=" + telefx +
                '}';
    }


}
