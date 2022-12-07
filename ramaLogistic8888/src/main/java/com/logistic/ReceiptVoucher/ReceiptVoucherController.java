package com.logistic.ReceiptVoucher;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import com.logistic.Report.ReportRrecieptVoucher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ReceiptVoucherController implements Initializable {

    @FXML
    private Label ClientId;
    @FXML
    private Label NO;

    @FXML
    private TextField accountOf;

    @FXML
    private TextField bank;

    @FXML
    private TextField bank1;

    @FXML
    private TextField bank2;

    @FXML
    private TextField bank3;

    @FXML
    private RadioButton cash;
    @FXML
    private RadioButton cheque;

    @FXML
    private TextField cheque0;

    @FXML
    private TextField cheque1;

    @FXML
    private TextField cheque2;

    @FXML
    private TextField cheque3;

    @FXML
    private ComboBox<String> clientName;

    @FXML
    private DatePicker dateNow;

    @FXML
    private RadioButton dollar;

    @FXML
    private TextField dueDate;

    @FXML
    private TextField dueDate1;

    @FXML
    private TextField dueDate2;

    @FXML
    private TextField dueDate3;

    @FXML
    private RadioButton nis;

    @FXML
    private TextField sumOf;

    @FXML
    private TextField total;

    @FXML
    private TextField total1;

    @FXML
    private TextField total2;

    @FXML
    private TextField total3;

    @FXML
    void clientOnAction(ActionEvent event) {

        int x = clientName.getItems().indexOf(clientName.getValue().toString());
        ClientId.setText(idTemp.get(x)+"");

    }

    @FXML
    void printOnAction(MouseEvent event) {
        try {
            System.out.println("hi");
            String path = "receiptVoucher.jrxml";
            JasperDesign jd = JRXmlLoader.load(path);
            JRDesignQuery jq = new JRDesignQuery();
            jq.setText(DataBase.printReceiptVoucher(n , Integer.parseInt(ClientId.getText())));
            jd.setQuery(jq);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jpa = JasperFillManager.fillReport(jr, null, DataBase.connection);
            JasperViewer.viewReport(jpa, false);
        }
        catch (NumberFormatException e ) {
            Alerts.WarningAlert("NOT EXIST ");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveOnAction(MouseEvent event) {
        String query = "";
        if (ReportRrecieptVoucher.NO_TAX_INVOICE != -1 ) {
            query = "UPDATE `ramalogistic`.`receiptvoucher` SET  `id_client` =?, `sumOf` = ?," +
                    "`accountOf` = ?, `Date_now` = ?, `cash` = ?, `cheque` =?," +
                    "`total1` = ?, `total2` = ?, `total3` = ?, `total4` = ?," +
                    "`date1` =?, `date2` = ?, `date3` = ?, `date4` = ?, `bank1` =? ," +
                    "`bank2` =?, `bank3` = ?, `bank4` =?, `chequeNO1` = ?, `chequeNO2` = ?," +
                    "`chequeNO3` = ?, `chequeNO4` = ?, `nis` = ?, `dollar` = ?" +
                    "WHERE `RV` = ? AND `id_client` = ?" ;
        }
        else {
             query = "INSERT INTO `ramalogistic`.`receiptvoucher` (`id_client`,`sumOf`,`accountOf`,`Date_now`,`cash`,`cheque`,\n" +
                    "`total1`,`total2`,`total3`,`total4`,`date1`,`date2`,`date3`,`date4`,`bank1`,`bank2`,`bank3`,\n" +
                    "`bank4`,`chequeNO1`,`chequeNO2`,`chequeNO3`,`chequeNO4`,`nis`,`dollar`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ;\n";
        }
        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
            DataBase.preparedStatement.setInt(1 , Integer.parseInt(ClientId.getText()));
            DataBase.preparedStatement.setString(2 , sumOf.getText());
            DataBase.preparedStatement.setString(3 , accountOf.getText());
            DataBase.preparedStatement.setDate(4 , Date.valueOf(dateNow.getValue()));
            if (cash.isSelected()) {
                DataBase.preparedStatement.setString(5, "YES");
            }
            else {
                DataBase.preparedStatement.setString(5, " ");

            }
            if (cheque.isSelected()) {
                DataBase.preparedStatement.setString(6, "YES");
            }
            else {
                DataBase.preparedStatement.setString(6, " ");

            }
            DataBase.preparedStatement.setString(7 , total.getText());
            DataBase.preparedStatement.setString(8 , total1.getText());
            DataBase.preparedStatement.setString(9, total2.getText());
            DataBase.preparedStatement.setString(10 , total3.getText());
            DataBase.preparedStatement.setString(11 , dueDate.getText());
            DataBase.preparedStatement.setString(12 , dueDate1.getText());
            DataBase.preparedStatement.setString(13 , dueDate2.getText());
            DataBase.preparedStatement.setString(14 , dueDate3.getText());
            DataBase.preparedStatement.setString(15 , bank.getText());
            DataBase.preparedStatement.setString(16 , bank1.getText());
            DataBase.preparedStatement.setString(17 , bank2.getText());
            DataBase.preparedStatement.setString(18 , bank3.getText());
            DataBase.preparedStatement.setString(19 , cheque0.getText());
            DataBase.preparedStatement.setString(20 , cheque1.getText());
            DataBase.preparedStatement.setString(21 , cheque2.getText());
            DataBase.preparedStatement.setString(22 , cheque3.getText());

            if (nis.isSelected()){
                DataBase.preparedStatement.setString(23 ,"YES");

            }
            else {
                DataBase.preparedStatement.setString(23 , " ");

            }

            if (dollar.isSelected()){
                DataBase.preparedStatement.setString(24 , "YES");

            }
            else {
                DataBase.preparedStatement.setString(24 , " ");
            }

            if (ReportRrecieptVoucher.NO_TAX_INVOICE != -1 ) {
                DataBase.preparedStatement.setInt(25, n);
                DataBase.preparedStatement.setInt(26 , Integer.parseInt(ClientId.getText()));
            }

            DataBase.preparedStatement.executeUpdate();
            Alerts.Success();


        } catch (SQLException e) {
            Alerts.Error();
        }
        catch (Exception e2) {
            Alerts.WarningAlert("Check if some Fields   NOT Empty ");
        }


    }
    LinkedList<Integer> idTemp ;

    static  int n ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientName.setDisable(false);
        try {
            if (ReportRrecieptVoucher .NO_TAX_INVOICE != -1 ) {
                DisplayData() ;

            }else {
                DataBase.resultSet = DataBase.statement.executeQuery("SELECT max(p.RV) FROM `receiptvoucher` p");
                DataBase.resultSet.next();
                n = DataBase.resultSet.getInt(1) + 1;
                NO.setText("NO." + n);

                DataBase.resultSet = DataBase.statement.executeQuery("select id_client , name_client from Client");
                idTemp = new LinkedList<>();
                while (DataBase.resultSet.next()) {
                    idTemp.add(DataBase.resultSet.getInt(1));
                    clientName.getItems().add(DataBase.resultSet.getString(2));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void DisplayData() {
        clientName.setDisable(true);
        String query = "select * from receiptvoucher where RV="+ReportRrecieptVoucher.NO_TAX_INVOICE ;
        try {
            DataBase.resultSet = DataBase.statement.executeQuery(query);
            DataBase.resultSet.next();
            n = DataBase.resultSet.getInt(1) ;
            NO.setText("NO." +n);
            ClientId.setText(DataBase.resultSet.getInt(2)+"");
            sumOf.setText(DataBase.resultSet.getString(3));
            accountOf.setText(DataBase.resultSet.getString(4));
            dateNow.setValue(DataBase.resultSet.getDate(5).toLocalDate());
            if (DataBase.resultSet.getString(6).equalsIgnoreCase("yes")){
                cash.setSelected(true);
            }

            if (DataBase.resultSet.getString(7).equalsIgnoreCase("yes")){
                cheque.setSelected(true);
            }
            total.setText(DataBase.resultSet.getString(8));
            total1.setText(DataBase.resultSet.getString(9));
            total2.setText(DataBase.resultSet.getString(10));
            total3.setText(DataBase.resultSet.getString(11));

            dueDate.setText(DataBase.resultSet.getString(12));
            dueDate1.setText(DataBase.resultSet.getString(13));
            dueDate2.setText(DataBase.resultSet.getString(14));
            dueDate3.setText(DataBase.resultSet.getString(15));

            bank.setText(DataBase.resultSet.getString(16));
            bank1.setText(DataBase.resultSet.getString(17));
            bank2.setText(DataBase.resultSet.getString(18));
            bank3.setText(DataBase.resultSet.getString(19));


            cheque0.setText(DataBase.resultSet.getString(20));
            cheque1.setText(DataBase.resultSet.getString(21));
            cheque2.setText(DataBase.resultSet.getString(22));
            cheque3.setText(DataBase.resultSet.getString(23));
            if (DataBase.resultSet.getString(24).equalsIgnoreCase("yes")){
                nis.setSelected(true);
            }
            if (DataBase.resultSet.getString(25).equalsIgnoreCase("yes")){
                dollar.setSelected(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
