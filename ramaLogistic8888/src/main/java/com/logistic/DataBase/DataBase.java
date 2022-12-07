package com.logistic.DataBase;

import com.logistic.Alerts.Alerts;

import java.sql.*;

public class DataBase {

    public static Connection connection;
    public static Statement statement ;
    public static PreparedStatement preparedStatement ;
    public static ResultSet resultSet;
    public DataBase(String Link , String UserName , String Password){

        try {
connection = DriverManager.getConnection(Link , UserName , Password);
statement = connection.createStatement();
System.out.println("Connection successfully");

        }
        catch (Exception e){
      Alerts.ErrorConnectionDataBase();
        }


    }
    public static String printCreditNote (int creditNo) {
        return  "select * from creditnote cn join creditnotedetails cnd on cn.CreditNO ="+creditNo+" and cn.CreditNO = cnd.CreditNO join client c on c.id_client = cn.id_client" ;
    }
    public static String printReceiptVoucher (int RV , int clientID) {
        return  "select *  from receiptvoucher rv join  client c on ( c.id_client ="+clientID+" and rv.RV ="+RV+" ) and (  rv.id_client = c.id_client )" ;
    }

    public static String printPaymentRequest(int i, int i1, int i2){
        return  String.format("SELECT DISTINCT\n" +
                "    c.name_Client,\n" +
                "    ft.FileID,\n" +
                "    pr.TransportType,\n" +
                "    pr.Carrier,\n" +
                "    pr.MBL,\n" +
                "    pr.HBL,\n" +
                "    pr.tax,\n" +
                "    pr.supplierName,\n" +
                "    pr.paymentDate,\n" +
                "    pr.DepDate,\n" +
                "    pr.ArrDate,\n" +
                "    pr.DischPort,\n" +
                "    pr.LoadPort,\n" +
                "    pr.ManifestNo,\n" +
                "    pr.DescOfGoods,\n" +
                "    pr.Incoterms,\n" +
                "    pr.PackageType,\n" +
                "    pr.Pcs,\n" +
                "    pr.Weight,\n" +
                "    pr.ChWeight,\n" +
                "    pr.Volume,\n" +
                "    pr.GoodsValue,\n" +
                "    pr.Currency,\n" +
                "    pr.CurrencySymbole,\n" +
                "    pd.discription,\n" +
                "    pd.price,\n" +
                "    pd.amount\n" +
                "FROM CLIENT\n" +
                "    c\n" +
                "JOIN paymentrequest pr ON\n" +
                "    c.id_Client = pr.id_Client\n" +
                "JOIN paymentdetails pd ON\n" +
                "    pr.PaymentID = pd.PaymentID\n" +
                "    join filetracker ft on \n" +
                "    c.id_client = ft.id_client\n" +
                "WHERE\n" +
                "    (\n" +
                "        c.id_Client = %d " +
                "AND pr.PaymentID = %d " +
                "and ft.FileID=%d" +
                "    );" , i , i1 , i2);

    }
    public static String printTaxInvoice (int clientID , int FileNo , int TaxID) {
        return "select TI.TaxID , TI.FileID , TI.declarationNo ,TI.dateNow ,c.name_Client , c.address_Client , TI.vat ,TI.currency  ," +
                " TI.notes , tid.AmountWithoutVat , tid.AmountDueVat , tid.description\n" +
                " from taxInvoice TI join client c on c.id_Client = "+clientID+" and  TI.id_client = c.id_client join filetracker ft on" +
                " ft.FileID ="+FileNo+" and TI.FileID = ft.FileID and c.id_Client = ft.id_client join  taxInvoiceDetails tid on TI.TaxID = "+TaxID+" and TI.TaxID = tid.TaxID ; " ;
    }

}
