package com.logistic.Report;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import com.logistic.ramalogistic.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ReportRrecieptVoucher implements Initializable {

    @FXML
    private TableView<ReceiptEntity> table;

    @FXML
    private TableColumn<ReceiptEntity, String> ClientName;

    @FXML
    private ComboBox<String> ClientName_List;

    @FXML
    private TableColumn<ReceiptEntity, String> accountOF;

    @FXML
    private TableColumn<ReceiptEntity, String> sumOF;
    @FXML
    private TableColumn<ReceiptEntity, Integer> NO;
    @FXML
    private TableColumn<ReceiptEntity, Integer> clientID;
    @FXML
    private TableColumn<ReceiptEntity, LocalDate> date;



    @FXML
    private DatePicker from;

    @FXML
    private Label idClient;

    @FXML
    private DatePicker to;

    @FXML
    void delete(MouseEvent event) {
        ReceiptEntity entity = table.getSelectionModel().getSelectedItem();
        if (entity == null ) {
            Alerts.WarningAlert("Please Select item .");
            return;
        }
        String query = "delete from receiptvoucher where RV= "+entity.getNo() ;
        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement(query) ;
            DataBase.preparedStatement.executeUpdate() ;
            table.getItems().remove(entity);
            Alerts.Success();
        } catch (SQLException e) {
            Alerts.Error();
        }

    }



    @FXML
    void print(MouseEvent event) {
        ReceiptEntity entity = table.getSelectionModel().getSelectedItem() ;
        if (entity == null ) {
            Alerts.WarningAlert("Please Select Item.");
            return;
        }
        try {
            System.out.println("hi");
            String path = "receiptVoucher.jrxml";
            JasperDesign jd = JRXmlLoader.load(path);
            JRDesignQuery jq = new JRDesignQuery();
            jq.setText(DataBase.printReceiptVoucher(entity.getNo() , entity.getIdClient()));
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
    void selectClientName(ActionEvent event) {
        int x = ClientName_List.getItems().indexOf(ClientName_List.getValue().toString());
        idClient.setText(idTemp.get(x) + "");

        try {
            configureData(getAllReceiptVoucherByClientID());
        }
        catch (Exception e ) {
            Alerts.Error();
        }

    }

    @FXML
    void toOnAction(ActionEvent event) {
        try {
            if (ClientName_List.getValue() == null) {
                configureData(getAllReceiptVoucherFromTo());
            } else {
                configureData(getAllReceiptVoucherByClientIDFromTO());
            }
        }
        catch (Exception e ) {
            Alerts.Error();
        }
    }
    public static int NO_TAX_INVOICE = -1 ;
    @FXML
    void update(MouseEvent event) throws IOException {
        ReceiptEntity entity = table.getSelectionModel().getSelectedItem();
        if (entity == null ) {
            Alerts.WarningAlert("Please Select item .");
            return;
        }
        NO_TAX_INVOICE = entity.getNo();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ReceiptVoucher.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NO.setCellValueFactory(new PropertyValueFactory<>("no"));
        ClientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        clientID.setCellValueFactory(new PropertyValueFactory<>("idClient"));
         sumOF.setCellValueFactory(new PropertyValueFactory<>("sumOf"));
        accountOF.setCellValueFactory(new PropertyValueFactory<>("accountOF"));
       configureData(getAllReceiptVoucher());
        try {
            DataBase.resultSet = DataBase.statement.executeQuery("select id_client , name_client from Client");
            idTemp = new LinkedList<>();
            while (DataBase.resultSet.next()) {
                idTemp.add(DataBase.resultSet.getInt(1));
                ClientName_List.getItems().add(DataBase.resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    LinkedList<Integer> idTemp;

    @FXML
    void RefreshOnAction(ActionEvent event) {
        configureData(getAllReceiptVoucher());
    }
    private void configureData(ResultSet resultSet) {
        try {
            table.getItems().clear();
            while (resultSet.next()) {
                ReceiptEntity entity = new ReceiptEntity() ;

                entity.setNo(resultSet.getInt(1));
                entity.setName(resultSet.getString(2));
                entity.setDate(resultSet.getDate(3).toLocalDate());
                entity.setIdClient(resultSet.getInt(4));
                entity.setSumOf(resultSet.getString(5));
                entity.setAccountOF(resultSet.getString(6));
                table.getItems().add(entity);
            }
        } catch (SQLException e) {
            Alerts.Error();
            e.printStackTrace();
        }

    }
    private ResultSet getAllReceiptVoucher() {
        String query = "select rv.RV  , c.name_client , rv.Date_now , c.id_client , rv.sumOf , rv.accountOf from  receiptvoucher rv join client c on c.id_client = rv.id_client";
        try {
            return  DataBase.statement.executeQuery(query);
        } catch (SQLException e) {

            Alerts.Error();
        }
        catch (Exception e) {

            Alerts.Error();
        }
        return null ;
    }
    private ResultSet getAllReceiptVoucherFromTo() {
        String s = "\"";
        String query = "select rv.RV  , c.name_client , rv.Date_now , c.id_client , rv.sumOf , rv.accountOf from  receiptvoucher rv join client c on c.id_client = rv.id_client  where rv.Date_now between "+s+from.getValue().toString()+s +" and +"+s+to.getValue().toString()+s;
        try {
            return  DataBase.statement.executeQuery(query);
        } catch (SQLException e) {

            Alerts.Error();
        }
        catch (Exception e) {

            Alerts.Error();
        }
        return null ;
    }
    private ResultSet getAllReceiptVoucherByClientID() {
        String query = "select rv.RV  , c.name_client , rv.Date_now , c.id_client , rv.sumOf , rv.accountOf from  receiptvoucher rv join client c on c.id_client = "+idClient.getText()+" and  c.id_client = rv.id_client";
        try {
            return  DataBase.statement.executeQuery(query);
        } catch (SQLException e) {

            Alerts.Error();
        }
        catch (Exception e) {

            Alerts.Error();
        }
        return null ;
    }
    private ResultSet getAllReceiptVoucherByClientIDFromTO() {
        String s = "\"";
        String query = "select rv.RV  , c.name_client , rv.Date_now , c.id_client , rv.sumOf , rv.accountOf from  receiptvoucher rv join client c on c.id_client = "+idClient.getText()+" and c.id_client = rv.id_client where rv.Date_now between "+s+from.getValue().toString()+s +" and +"+s+to.getValue().toString()+s;
        try {
            return  DataBase.statement.executeQuery(query);
        } catch (SQLException e) {
            Alerts.Error();
        }
        catch (Exception e) {
            e.printStackTrace();
            Alerts.Error();
        }
        return null ;
    }



    public class ReceiptEntity {
        private int no ;
        private int idClient ;
        private  String name ;
        private LocalDate date ;
        private String sumOf ;
        private String accountOF ;

        public ReceiptEntity(int no, int idClient, String name, LocalDate date, String sumOf, String accountOF) {
            this.no = no;
            this.idClient = idClient;
            this.name = name;
            this.date = date;
            this.sumOf = sumOf;
            this.accountOF = accountOF;
        }

        public ReceiptEntity() {

        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public int getIdClient() {
            return idClient;
        }

        public void setIdClient(int idClient) {
            this.idClient = idClient;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public String getSumOf() {
            return sumOf;
        }

        public void setSumOf(String sumOf) {
            this.sumOf = sumOf;
        }

        public String getAccountOF() {
            return accountOF;
        }

        public void setAccountOF(String accountOF) {
            this.accountOF = accountOF;
        }
    }

}
