package com.logistic.Report;

public class ReportEntity {
private  int ClientID ;
private String ClientName ;
private String date ;
    private String tranportType ;
    private String supplierName ;
    private String carrier ;
    private String mbl ;
    private String hbl ;
    private String depDate ;
    private String arrDate ;

    private String DischPort ;
    private String LoadPort ;
    private String ManifestNo ;
    private String DescOfGoods ;
    private String Incoterms ;
    private String PackageType ;
    private String Pcs ;
    private String Weight ;
    private String ChWeight ;
    private String Volume ;


    private String GoodsValue ;
    private String Currency ;
    private Integer FileID ;
    private String TaxGoods ;
    private String TotalPrice ;
    private String TotalAmount ;

    public int getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(int paymentID) {
        PaymentID = paymentID;
    }

    private int PaymentID ;

    public int getClientID() {
        return ClientID;
    }

    public String getArrDate() {
        return arrDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTranportType() {
        return tranportType;
    }

    public void setTranportType(String tranportType) {
        this.tranportType = tranportType;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getMbl() {
        return mbl;
    }

    public void setMbl(String mbl) {
        this.mbl = mbl;
    }

    public String getHbl() {
        return hbl;
    }

    public void setHbl(String hbl) {
        this.hbl = hbl;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getDischPort() {
        return DischPort;
    }

    public void setDischPort(String dischPort) {
        DischPort = dischPort;
    }

    public String getLoadPort() {
        return LoadPort;
    }

    public void setLoadPort(String loadPort) {
        LoadPort = loadPort;
    }

    public String getManifestNo() {
        return ManifestNo;
    }

    public void setManifestNo(String manifestNo) {
        ManifestNo = manifestNo;
    }

    public String getDescOfGoods() {
        return DescOfGoods;
    }

    public void setDescOfGoods(String descOfGoods) {
        DescOfGoods = descOfGoods;
    }

    public String getIncoterms() {
        return Incoterms;
    }

    public void setIncoterms(String incoterms) {
        Incoterms = incoterms;
    }

    public String getPackageType() {
        return PackageType;
    }

    public void setPackageType(String packageType) {
        PackageType = packageType;
    }

    public String getPcs() {
        return Pcs;
    }

    public void setPcs(String pcs) {
        Pcs = pcs;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getChWeight() {
        return ChWeight;
    }

    public void setChWeight(String chWeight) {
        ChWeight = chWeight;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getGoodsValue() {
        return GoodsValue;
    }

    public void setGoodsValue(String goodsValue) {
        GoodsValue = goodsValue;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public Integer getFileID() {
        return FileID;
    }

    public void setFileID(Integer fileID) {
        FileID = fileID;
    }

    public String getTaxGoods() {
        return TaxGoods;
    }

    public void setTaxGoods(String taxGoods) {
        TaxGoods = taxGoods;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
