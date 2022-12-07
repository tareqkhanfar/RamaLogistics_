package com.logistic.taxInvoice;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import com.logistic.Report.ReportTaxInvoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class TaxInvoiceController implements Initializable {

    @FXML
    private TextField AmountDueVat;
    @FXML
    private TextField vat;


    @FXML
    private Label ClientID;

    @FXML
    private TextField Description;

    @FXML
    private TableColumn<TaxInvoiceEntity, String> DescriptionC;

    @FXML
    private TextField NO;

    @FXML
    private TextArea Notes;

    @FXML
    private TextField address;

    @FXML
    private TableColumn<TaxInvoiceEntity, Float> amountDVatC;

    @FXML
    private TableColumn<TaxInvoiceEntity, Float> amountWVatC;

    @FXML
    private TextField amountWithoutVat;

    @FXML
    private ComboBox<String> clientName;
    @FXML
    private ComboBox<String> fileNumber;
    @FXML
    private DatePicker dateNow;

    @FXML
    private TextField declarationNO;
    @FXML
    private TableView<TaxInvoiceEntity> table;

    @FXML
    private RadioButton dollar;

    @FXML
    private RadioButton nis;

    @FXML
    void addOnAction(ActionEvent event) {
        try {
            if (ReportTaxInvoice.NO_TAX_INVOICE != -1 ) {
                String query = "INSERT INTO `ramalogistic`.`taxinvoicedetails` (`TaxID`," +
                        "`description`,`AmountWithoutVat`,`AmountDueVat`) VALUES (? , ? , ? , ?) " ;
                DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
                DataBase.preparedStatement.setInt(1 , Integer.parseInt(NO.getText()) );
                DataBase.preparedStatement.setString(2 ,  Description.getText());
                DataBase.preparedStatement.setFloat(3 , Float.parseFloat(amountWithoutVat.getText()));
                DataBase.preparedStatement.setFloat(4 , Float.parseFloat(AmountDueVat.getText()));
                DataBase.preparedStatement.executeUpdate();
                table.getItems().add(new TaxInvoiceEntity(Description.getText() , Float.parseFloat(AmountDueVat.getText()) , Float.parseFloat(amountWithoutVat.getText())));

            }
            table.getItems().add(new TaxInvoiceEntity(Description.getText() , Float.parseFloat(AmountDueVat.getText()) , Float.parseFloat(amountWithoutVat.getText())));

        }
        catch (SQLIntegrityConstraintViolationException e ) {
            Alerts.WarningAlert("Description already exists\n" +
                    "Enter a sign to distinguish it from the other ");
        }
        catch (NumberFormatException e ) {
            Alerts.ErrorAlert("Enter valid Value !!");
        }
        catch (Exception e3) {
            Alerts.Error();
        }
    }

    @FXML
    void clientNameOnAction(ActionEvent event) {
        int x = clientName.getItems().indexOf(clientName.getValue().toString());
        ClientID.setText( idTemp.get(x)+"");
        fileNumber.getItems().clear();
        try {
            DataBase.resultSet = DataBase.statement.executeQuery("select FileID  from filetracker where id_client=" + Integer.parseInt(ClientID.getText()));
            while (DataBase.resultSet.next()) {
                int n = DataBase.resultSet.getInt(1);
                fileNumber.getItems().add(n + "");
            }
            DataBase.resultSet = DataBase.statement.executeQuery("select address_Client from client where id_client = " +ClientID.getText());
            DataBase.resultSet.next();
            address.setText(DataBase.resultSet.getString(1));
        }
        catch (SQLException e ){
            Alerts.Error();
        }

    }


    @FXML
    void deleteOnAction(ActionEvent event) {
    TaxInvoiceEntity taxInvoiceEntity = table.getSelectionModel().getSelectedItem() ;
    if (taxInvoiceEntity == null ) {
        Alerts.WarningAlert("Please Select Item.");
        return;
    }
    if (ReportTaxInvoice.NO_TAX_INVOICE != -1 ) {
        try {
            String s = "\"";
            String query = "delete from taxinvoicedetails where TaxID=" + ReportTaxInvoice.NO_TAX_INVOICE + " and " +
                    "description=" + s + taxInvoiceEntity.getDescription() + s;
            System.out.println(query);
            DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
            DataBase.preparedStatement.executeUpdate();
            table.getItems().remove(taxInvoiceEntity);
            return;
        }
        catch (Exception e ) {
            Alerts.Error();
        }


    }
        table.getItems().remove(taxInvoiceEntity);


    }

    @FXML
    void printOnAction(ActionEvent event) {
        try {
            String path = "taxInvoice.jrxml";
            JasperDesign jd = JRXmlLoader.load(path);
            JRDesignQuery jq = new JRDesignQuery();
            jq.setText(DataBase.printTaxInvoice(Integer.parseInt(ClientID.getText()) , Integer.parseInt(fileNumber.getValue().toString()) , Integer.parseInt(NO.getText())));
            jd.setQuery(jq);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jpa = JasperFillManager.fillReport(jr, null, DataBase.connection);
            JasperViewer.viewReport(jpa, false);
        }catch (NullPointerException e ) {
            Alerts.WarningAlert("Please Select item.");
        }
        catch (JRException ew) {
            Alerts.Error();
        }
        catch (NumberFormatException e ) {
            Alerts.WarningAlert("Check all feilds are NOT Empty");
        }
        catch (Exception e ) {
            Alerts.Error();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        String query = "" ;
        if (ReportTaxInvoice.NO_TAX_INVOICE != -1) {
            query = "UPDATE `ramalogistic`.`taxinvoice` " +
                    "SET`TaxID` = ?,`FileID` = ?,`declarationNo` = ?,`id_Client` = ?,`vat` = ?,`Notes` = ?, " +
                    "`dateNow` = ?,`currency` = ? WHERE `TaxID` = ?" ;
        }
        else {
            query = " INSERT INTO `ramalogistic`.`taxinvoice`(`TaxID` , `FileID`,`declarationNo`,`id_Client`,`vat`," +
                    "`Notes`,`dateNow`,`currency`)VALUES (?,?,?,?,?,?,?,?)";

        }
        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
            DataBase.preparedStatement.setInt(1 , Integer.parseInt(NO.getText()));
            DataBase.preparedStatement.setInt(2 , Integer.parseInt(fileNumber.getValue().toString()));
            DataBase.preparedStatement.setString(3 , declarationNO.getText() );
            DataBase.preparedStatement.setInt(4 , Integer.parseInt(ClientID.getText()));
           DataBase.preparedStatement.setFloat(5 , Float.parseFloat(vat.getText()));
            DataBase.preparedStatement.setString(6 , Notes.getText() );

            DataBase.preparedStatement.setDate(7 , Date.valueOf(dateNow.getValue()));

            if (nis.isSelected()) {
                DataBase.preparedStatement.setString(8 , nis.getText());

            }
            else if (dollar.isSelected()) {
                DataBase.preparedStatement.setString(8 , dollar.getText());

            }
            else {
                Alerts.WarningAlert("Please Select Currency !!!");
            }
            if (ReportTaxInvoice.NO_TAX_INVOICE != -1 ) {
                DataBase.preparedStatement.setInt(9 , Integer.parseInt(NO.getText()));
                DataBase.preparedStatement.executeUpdate();
                Alerts.Success();
                ReportTaxInvoice.NO_TAX_INVOICE = -1 ;
                return;
            }
            DataBase.preparedStatement.executeUpdate();


            ObservableList<TaxInvoiceEntity> list = table.getItems();
            for (TaxInvoiceEntity entity : list) {
                String query2 = "INSERT INTO `ramalogistic`.`taxinvoicedetails`(`TaxID`,`description`,`AmountWithoutVat`," +
                        "`AmountDueVat`)VALUES(?,?,?,?)";
                DataBase.preparedStatement = DataBase.connection.prepareStatement(query2);
                DataBase.preparedStatement.setInt(1, Integer.parseInt(NO.getText()));
                DataBase.preparedStatement.setString(2 , entity.getDescription());
                DataBase.preparedStatement.setFloat(3 , entity.getAmountWithoutTax());
                DataBase.preparedStatement.setFloat(4 , entity.getAmountDueTax());
                DataBase.preparedStatement.executeUpdate();
            }
            Alerts.Success();

        } catch (SQLIntegrityConstraintViolationException e) {
            Alerts.ErrorAlert("This invoice already exist in data Base ,, please write another NO.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e2 ) {
Alerts.ErrorAlert("Enter Valid value !!!");
        } catch (Exception e3)
        {
Alerts.Error();
        }

    }
static  int n ;
    LinkedList<Integer> idTemp ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        NO.setDisable(false);
        clientName.setDisable(false);
        fileNumber.setDisable(false);
        address.setDisable(false);


        ToggleGroup tg = new ToggleGroup();
        tg.getToggles().addAll(nis, dollar);

        DescriptionC.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountDVatC.setCellValueFactory(new PropertyValueFactory<>("amountDueTax"));
        amountWVatC.setCellValueFactory(new PropertyValueFactory<>("amountWithoutTax"));



            try {
                if (ReportTaxInvoice.NO_TAX_INVOICE != -1) {
                  DisplayDate () ;
                }
                else {
                    DataBase.resultSet = DataBase.statement.executeQuery("SELECT max(p.TaxID) FROM `taxinvoice` p");
                    DataBase.resultSet.next();
                    n = DataBase.resultSet.getInt(1) + 1;
                    NO.setText(n + "");
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

    private void DisplayDate() {


        NO.setDisable(true);
        clientName.setDisable(true);
        fileNumber.setDisable(true);
        address.setDisable(true);

        try {
            DataBase.resultSet = DataBase.statement.executeQuery("select TaxID , FileID , declarationNo ,  ti.id_Client , c.name_client ," +
                    "vat , Notes , dateNow , currency , c.address_client from taxInvoice ti join  client c on   c.id_client = ti.id_client where TaxID = " +ReportTaxInvoice.NO_TAX_INVOICE);

DataBase.resultSet.next();
            NO.setText(DataBase.resultSet.getInt(1)+"");
            fileNumber.setValue(DataBase.resultSet.getInt(2)+"");
            declarationNO.setText(DataBase.resultSet.getString(3));
            ClientID.setText(DataBase.resultSet.getInt(4)+"");
clientName.setValue(DataBase.resultSet.getString(5));
vat.setText(DataBase.resultSet.getFloat(6)+"");
Notes.setText(DataBase.resultSet.getString(7));
dateNow.setValue(DataBase.resultSet.getDate(8).toLocalDate());
if (DataBase.resultSet.getString(9).equalsIgnoreCase("nis")){
    nis.setSelected(true);
}
else {
    dollar.setSelected(true);

}
            address.setText(DataBase.resultSet.getString(10));

DataBase.resultSet = DataBase.statement.executeQuery("select * from taxinvoicedetails tid join taxInvoice ti on ti.TaxID = "+ReportTaxInvoice.NO_TAX_INVOICE +" and ti.TaxID = tid.TaxID");
while (DataBase.resultSet.next()){
table.getItems().add(new TaxInvoiceEntity(DataBase.resultSet.getString(3) ,
        DataBase.resultSet.getFloat(4) ,  DataBase.resultSet.getFloat(5)));
}

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
