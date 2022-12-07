package com.logistic.PaymentRequest;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import com.logistic.Home.HomeController;
import com.logistic.Report.ReportController;
import com.logistic.ramalogistic.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class PaymentRequestUpdateController implements Initializable {

    @FXML
    private Button paymentDetails;

    Stage stage ;
    @FXML
    void paymentDetailsOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("paymentDetailsUpdate.fxml"));
        stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stage.show();
    }


    @FXML
    private ComboBox<String> SelectClient;
    @FXML
    private TextField HBL;

    @FXML
    private TextField MBL;
    @FXML
    private TextField currencyAmount ;

    @FXML
    private   Label NO;
    @FXML
    private Label fileNo;
    @FXML
    private Hyperlink sendMessage;
    @FXML
    private ComboBox<String> selectSupplier;
    @FXML
    private Label idSupllier;

    @FXML
    private DatePicker arrDate;

    @FXML
    private TextField carrier;

    @FXML
    private TextField chWeight;
    @FXML
    private ComboBox<String> fileNumber;



    @FXML
    private TextField currency;

    @FXML
    private DatePicker dateNow;

    @FXML
    private DatePicker depDate;

    @FXML
    private TextField descGoods;

    @FXML
    private TextField dischPort;

    @FXML
    private TextField goodsValue;

    @FXML
    private Label idLabel;

    @FXML
    private TextField incoterms;

    @FXML
    private Label l1;

    @FXML
    private Label l2;

    @FXML
    private Label l3;

    @FXML
    private TextField loadPort;

    @FXML
    private TextField manifestNo;

    @FXML
    private TextField packageType;



    @FXML
    private TextField pcs;

    @FXML
    private ImageView save;



    @FXML
    private TextField transportType;

    @FXML
    private TextField volume;

    @FXML
    private TextField weight;
    @FXML
    private TextField tax;
    private  int paymentID_ ;


    @FXML
    void exitOnAction(ActionEvent event) {

    }




    @FXML
    void printOnAction(MouseEvent event) throws JRException {
        try {
            System.out.println("hi");
            String path = "Blank_A4.jrxml";
            JasperDesign jd = JRXmlLoader.load(path);
            JRDesignQuery jq = new JRDesignQuery();
            jq.setText(DataBase.printPaymentRequest(Integer.parseInt(idLabel.getText()), Integer.parseInt(NO.getText()), Integer.parseInt(fileNo.getText())));
            jd.setQuery(jq);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jpa = JasperFillManager.fillReport(jr, null, DataBase.connection);
            JasperViewer.viewReport(jpa, false);
        }
        catch (NumberFormatException e ) {
            Alerts.WarningAlert("NOT EXIST A PAYMENT REQUEST");
        }

    }
    LinkedList<Integer> idTemp ;
    LinkedList<Integer> idTempSupplier ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            String query = "select * from paymentrequest where paymentID="+ReportController.PAYMENT_ID_TO_UPDATE ;
            try {
                DataBase.resultSet = DataBase.statement.executeQuery(query);
                DataBase.resultSet.next() ;

                showDataOnScreen () ;
                DataBase.resultSet = DataBase.statement.executeQuery("select id_client , name_client from Client");
                idTemp = new LinkedList<>();
                while (DataBase.resultSet.next()) {
                    idTemp.add(DataBase.resultSet.getInt(1));
                    SelectClient.getItems().add(DataBase.resultSet.getString(2));
                    selectSupplier.getItems().add(DataBase.resultSet.getString(2));
                }

                DataBase.resultSet = DataBase.statement.executeQuery("select id_Supplier , name_supplier from supplier");
                idTempSupplier = new LinkedList<>();
                while (DataBase.resultSet.next()) {
                    idTempSupplier.add(DataBase.resultSet.getInt(1));
                    selectSupplier.getItems().add(DataBase.resultSet.getString(2));
                }



            } catch (SQLException e) {
Alerts.Error();
            }

    }


    @FXML
    void selectOnAction(ActionEvent event) throws SQLException {


        int x = SelectClient.getItems().indexOf(SelectClient.getValue().toString());
        idLabel.setText( idTemp.get(x)+"");
        fileNumber.getItems().clear();
        DataBase.resultSet = DataBase.statement.executeQuery("select FileID  from filetracker where id_client="+Integer.parseInt(idLabel.getText()));
        while (DataBase.resultSet.next()) {
            int n = DataBase.resultSet.getInt(1);
            System.out.println(n);
            fileNumber.getItems().add(n+"");
        }

    }


    @FXML
    void saveOnAction(MouseEvent event) {
        try {
            SavePaymentRequest( );
        } catch (SQLException e) {
            Alerts.Error();
        }
        catch (NumberFormatException e) {
            Alerts.FiledsNotEmptyOrSqlError();
        }   catch (NullPointerException e ) {
            Alerts.FiledsNotEmptyOrSqlError();
        }
        catch (Exception e ) {
            Alerts.Error();
        }


    }
    void SavePaymentRequest( ) throws SQLException , NumberFormatException {
           String query = "UPDATE `ramalogistic`.`paymentrequest`" +
                    "SET " +
                    "`PaymentID` = ?,`id_Client` = ?,`PaymentDate` =? ,`TransportType` =? ,`SupplierName` = ? ,`Carrier` = ?, " +
                    "`MBL` = ?,`HBL` = ?,`DepDate` =?,`ArrDate` = ?,`DischPort` = ?,`LoadPort` = ?,`ManifestNo` = ?, " +
                    "`DescOfGoods` =? ,`Incoterms` =? ,`PackageType` =?,`Pcs` =?,`Weight` =? ,`ChWeight` =?,`Volume` =? , " +
                    "`GoodsValue` =? ,`Currency` =?,`tax` = ?,`FileID` = ?,`CurrencySymbole` = ? " +
                    "WHERE `PaymentID` = ?" ;

        DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
        DataBase.preparedStatement.setInt(1 , Integer.parseInt(NO.getText()));
        DataBase.preparedStatement.setInt(2 , Integer.parseInt(idLabel.getText()));
        DataBase.preparedStatement.setDate(3 , Date.valueOf(dateNow.getValue()));
        DataBase.preparedStatement.setString(4 ,transportType.getText());
        DataBase.preparedStatement.setString(5  ,selectSupplier.getValue().toString());
        DataBase.preparedStatement.setString(6  ,carrier.getText());
        DataBase.preparedStatement.setString(7  ,MBL.getText());
        DataBase.preparedStatement.setString(8  ,HBL.getText());
        DataBase.preparedStatement.setDate(9 , Date.valueOf(depDate.getValue()));
        DataBase.preparedStatement.setDate(10 , Date.valueOf(arrDate.getValue()));
        DataBase.preparedStatement.setString(11 , dischPort.getText());
        DataBase.preparedStatement.setString(12 , loadPort.getText());
        DataBase.preparedStatement.setString(13 , manifestNo.getText());
        DataBase.preparedStatement.setString(14 , descGoods.getText());
        DataBase.preparedStatement.setString(15 , incoterms.getText());
        DataBase.preparedStatement.setString(16 , packageType.getText());
        DataBase.preparedStatement.setString(17 , pcs.getText());
        DataBase.preparedStatement.setString(18 , weight.getText());
        DataBase.preparedStatement.setString(19 , chWeight.getText());
        DataBase.preparedStatement.setString(20 , volume.getText());
        DataBase.preparedStatement.setString(21 ,goodsValue.getText());
        DataBase.preparedStatement.setString(22 , currency.getText());
        DataBase.preparedStatement.setString(23 , tax.getText());
        DataBase.preparedStatement.setInt(24 , Integer.parseInt(fileNo.getText()));
        DataBase.preparedStatement.setString(25 , currencyAmount.getText());

        DataBase.preparedStatement.setInt(26 , Integer.parseInt(NO.getText()));
        DataBase.preparedStatement.executeUpdate();
        Alerts.Success();


    }
    @FXML
    void close(MouseEvent event) {
        HomeController.stagePayment.close();
    }



        @FXML
    void selectSupplierOnAction(ActionEvent event) {
        int x = selectSupplier.getItems().indexOf(selectSupplier.getValue().toString());
        idSupllier.setText( idTempSupplier.get(x)+"");
    }

    @FXML
    void fileNumberOnAction(ActionEvent event) {
        fileNo.setText(fileNumber.getValue().toString());
    }
    @FXML
    void sendMessageOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Message.fxml"));
        Stage stage ;
        stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stage.show();

    }
    private void showDataOnScreen() {
        try {
            NO.setText(DataBase.resultSet.getInt(1)+"");
            idLabel.setText(DataBase.resultSet.getInt(2)+"");
            dateNow.setValue(DataBase.resultSet.getDate(3).toLocalDate());
            transportType.setText(DataBase.resultSet.getString(4));
            selectSupplier.setValue(DataBase.resultSet.getString(5));
            carrier.setText(DataBase.resultSet.getString(6));
            MBL.setText(DataBase.resultSet.getString(7));
            HBL.setText(DataBase.resultSet.getString(8));
            depDate.setValue(DataBase.resultSet.getDate(9).toLocalDate());
            arrDate.setValue(DataBase.resultSet.getDate(10).toLocalDate());
            dischPort.setText(DataBase.resultSet.getString(11));
            loadPort.setText(DataBase.resultSet.getString(12));
            manifestNo.setText(DataBase.resultSet.getString(13));
            descGoods.setText(DataBase.resultSet.getString(14));
            incoterms.setText(DataBase.resultSet.getString(15));
            packageType.setText(DataBase.resultSet.getString(16));
            pcs.setText(DataBase.resultSet.getString(17));
            weight.setText(DataBase.resultSet.getString(18));
            chWeight.setText(DataBase.resultSet.getString(19));
            volume.setText(DataBase.resultSet.getString(20));
            goodsValue.setText(DataBase.resultSet.getString(21));
            currency.setText(DataBase.resultSet.getString(22));
            tax.setText(DataBase.resultSet.getString(23));
            fileNumber.setValue(DataBase.resultSet.getInt(24)+"");
            fileNo.setText(DataBase.resultSet.getInt(24)+"");

            currencyAmount.setText(DataBase.resultSet.getString(25));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
