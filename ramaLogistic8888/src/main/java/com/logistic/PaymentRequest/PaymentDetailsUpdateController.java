package com.logistic.PaymentRequest;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import com.logistic.Report.ReportController;
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

public class PaymentDetailsUpdateController implements Initializable {

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
    void addOnAction(ActionEvent event)  {

        try {
            //   #########################################################
            String query = "INSERT INTO `paymentdetails`( `PaymentID`, `discription`, `price`, `amount`) VALUES (?,?,?,?)";
            DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
            DataBase.preparedStatement.setInt(1, ReportController.PAYMENT_ID_TO_UPDATE);
            DataBase.preparedStatement.setString(2, discription.getText());
            DataBase.preparedStatement.setFloat(3, Float.parseFloat(price.getText()));
            DataBase.preparedStatement.setFloat(4, Float.parseFloat(amount.getText()));
            DataBase.preparedStatement.executeUpdate();
            tableDetails.getItems().add(new PaymentDetailsEntity(discription.getText(), Float.parseFloat(price.getText())
                    , Float.parseFloat(amount.getText())));

        }
        catch (SQLException e ) {
            Alerts.Error();
        }
        catch (NumberFormatException e2 ) {
            Alerts.ErrorAlert("Enter valid Values");

        }
        catch (Exception e3) {
            Alerts.Error();
        }


    }

    @FXML
    void deleteOnAction(ActionEvent event)  {

        String s = "\"";
        PaymentDetailsEntity paymentDetails = tableDetails.getSelectionModel().getSelectedItem();
        if (paymentDetails==null) {
            Alerts.WarningAlert("Please Select Item.");
            return ;
        }
        String query = "delete from paymentdetails where paymentID=" + ReportController.PAYMENT_ID_TO_UPDATE
                + " and discription=" + s+paymentDetails.getDiscription()+s+ " and price=" + paymentDetails.getPrice() + " and amount=" + paymentDetails.getAmount();
        System.out.println(query);
        try {
            DataBase.connection.prepareStatement(query).executeUpdate();
            tableDetails.getItems().remove(paymentDetails);

        } catch (SQLException e) {
Alerts.ErrorAlert("Enter valid values.");

        }




    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ArrayList <PaymentDetailsEntity> list = configureData ();
        ObservableList<PaymentDetailsEntity> list1 = FXCollections.observableList(list);
        discCoulmn.setCellValueFactory(new PropertyValueFactory<>("Discription"));
        priceCoulmn.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountCoulmn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableDetails.setItems(list1);
    }

    private static ArrayList<PaymentDetailsEntity> configureData () {
        ArrayList<PaymentDetailsEntity> list = new ArrayList<>();
        System.out.println(ReportController.PAYMENT_ID_TO_UPDATE);
        String query = "select * from paymentdetails where PaymentID=" + ReportController.PAYMENT_ID_TO_UPDATE ;
        try {
            DataBase.resultSet = DataBase.statement.executeQuery(query);
            while (DataBase.resultSet.next()){
                list.add(new PaymentDetailsEntity(DataBase.resultSet.getString(3) , DataBase.resultSet.getFloat(4) , DataBase.resultSet.getFloat(5)));
            }
        } catch (SQLException e) {
            Alerts.Error();
        }

        return list ;
    }


}
