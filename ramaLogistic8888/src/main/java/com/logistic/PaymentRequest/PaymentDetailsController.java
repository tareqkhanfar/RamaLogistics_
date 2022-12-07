package com.logistic.PaymentRequest;

import com.logistic.Alerts.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentDetailsController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private TextField amount;

    @FXML
    private TableColumn<PaymentDetailsEntity, Float> amountCoulmn;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<PaymentDetailsEntity,String> discCoulmn;

    @FXML
    private TextField discription;

    @FXML
    private TextField price;

    @FXML
    private TableColumn<PaymentDetailsEntity, Float> priceCoulmn;

    @FXML
    private TextField tax;

    @FXML
    private Label total;

    @FXML
    private TableView<PaymentDetailsEntity> tableDetails;
static float n ;
  static  ObservableList<PaymentDetailsEntity> list1 ;
    @FXML
    void addOnAction(ActionEvent event) throws SQLException {

try {
    //   #########################################################
    tableDetails.getItems().add(new PaymentDetailsEntity(discription.getText(), Float.parseFloat(price.getText())
            , Float.parseFloat(amount.getText())));

    float x = Float.parseFloat(amount.getText());
    n = n + x;
    total.setText(n + "");
}
catch (Exception e ) {
    Alerts.ErrorAlert("Enter valid values");
}
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
PaymentDetailsEntity paymentDetails = tableDetails.getSelectionModel().getSelectedItem();
if (paymentDetails== null ) {
    Alerts.WarningAlert("Please Select Item");
    return;
}
tableDetails.getItems().remove(paymentDetails);
        float x = paymentDetails.getAmount();
        n = n - x ;
        total.setText(n+"");
    }

    @FXML
    void saveOnAction(MouseEvent event)  {


       list1 = tableDetails.getItems();
        Alerts.Success();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        total.setText("0");
        n=0;
        ArrayList <PaymentDetailsEntity> list = new ArrayList<PaymentDetailsEntity>();
        ObservableList<PaymentDetailsEntity> list1 = FXCollections.observableList(list);
        discCoulmn.setCellValueFactory(new PropertyValueFactory<PaymentDetailsEntity , String>("Discription"));
        priceCoulmn.setCellValueFactory(new PropertyValueFactory<PaymentDetailsEntity , Float>("price"));
        amountCoulmn.setCellValueFactory(new PropertyValueFactory<PaymentDetailsEntity , Float>("amount"));
         tableDetails.setItems(list1);
    }


}
