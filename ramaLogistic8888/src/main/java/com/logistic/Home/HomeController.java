package com.logistic.Home;

import com.logistic.FileTracker.AllFilesController;
import com.logistic.Report.ReportCreditNote;
import com.logistic.Report.ReportTaxInvoice;
import com.logistic.ramalogistic.HelloApplication;
import com.logistic.ramalogistic.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HomeController implements Initializable {
    @FXML
    private Button Client;
    @FXML
    private Button Backup;
    @FXML
    private Label email;

    @FXML
    private Label name;
    @FXML
    private Label phone;

    @FXML
    private Label telefx;
    @FXML
    private Label address;

    @FXML
    private HBox bar;

    @FXML
    private Button paymentRequest;

    @FXML
    private Label userOnline;


    @FXML
    private Button Order;

    @FXML
    private Button Report;

    @FXML
    private Button Setting;

    @FXML
    void OrderOnAction(ActionEvent event) throws IOException {
        AllFilesController.s = -1 ;
        AllFilesController.i = -1 ;
        AllFilesController.FID=-1;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AllFiles.fxml"));
        Stage stage = fxmlLoader.load();
        stage.setMaximized(true);
        stage.show();
    }
    public static  Stage stagePayment ;
    @FXML
    void paymentRequestOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("paymentRequest.fxml"));
         stagePayment = fxmlLoader.load();
        stagePayment.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stagePayment.setMaximized(true);
        stagePayment.show();
}


    @FXML
    void ClientOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Client.fxml"));
        Stage stage = fxmlLoader.load();
        stage.show();
    }
    @FXML
    void ReportOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reportHome.fxml"));

        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Client.css").toExternalForm());

        stage.show();
    }
    @FXML
    void SettingOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Setting.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Other.css").toExternalForm());

        stage.show();
    }
    @FXML
    void BackupOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Backup.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Other.css").toExternalForm());
        stage.show();
    }

    @FXML
    void creditOnAction(ActionEvent event) throws IOException {
        ReportCreditNote.NO_TO_UPDATE = -1 ;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreditNote.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stage.show();
    }

    @FXML
    void taxInvoiceOnAction(ActionEvent event) throws IOException {
        ReportTaxInvoice.NO_TAX_INVOICE = -1 ;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("taxInvoice.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stage.show();
    }
    @FXML
    void ReceiptVoucherOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ReceiptVoucher.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stage.show();
    }
    @FXML
    void supplierOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Supplier.fxml"));
        Stage stage = fxmlLoader.load();
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Scanner in = new Scanner(new File("info.txt"));
            while (in.hasNext()) {
                name.setText(in.nextLine());
                email.setText(in.nextLine());
                phone.setText(in.nextLine());
                telefx.setText(in.nextLine());
                address.setText(in.nextLine());

            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        userOnline.setText(HelloController.USERNAME);
    }
}
