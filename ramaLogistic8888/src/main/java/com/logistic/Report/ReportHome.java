package com.logistic.Report;

import com.logistic.ramalogistic.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportHome {

    @FXML
    void creditNote(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reportCreditNote.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Client.css").toExternalForm());
        stage.show();
    }

    @FXML
    void payment(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Report.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Client.css").toExternalForm());
        stage.show();
    }

    @FXML
    void reciept(ActionEvent event) throws IOException {
       ReportRrecieptVoucher. NO_TAX_INVOICE = -1 ;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reportRecieptVoucher.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Client.css").toExternalForm());
        stage.show();
    }

    @FXML
    void taxInvoice(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reportTaxInvoice.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Client.css").toExternalForm());
        stage.show();
    }

}
