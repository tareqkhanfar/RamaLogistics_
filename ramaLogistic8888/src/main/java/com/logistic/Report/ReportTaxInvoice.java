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

public class ReportTaxInvoice implements Initializable {

    @FXML
    private TableView<TaxEntity> table;

    @FXML
    private TableColumn<TaxEntity, String> ClientName;

    @FXML
    private ComboBox<String> ClientName_List;

    @FXML
    private TableColumn<TaxEntity, Integer> NO;
    @FXML
    private TableColumn<TaxEntity, Integer> clientID;
    @FXML
    private TableColumn<TaxEntity, LocalDate> date;

    @FXML
    private ComboBox<Integer> fileNo_List;

    @FXML
    private TableColumn<TaxEntity, Integer> fileNumber;

    @FXML
    private DatePicker from;

    @FXML
    private Label idClient;

    @FXML
    private DatePicker to;

    @FXML
    void delete(MouseEvent event) {
        TaxEntity entity = table.getSelectionModel().getSelectedItem();
        if (entity == null ) {
            Alerts.WarningAlert("Please Select item .");
            return;
        }

String query = "delete from taxInvoice where TaxID= "+ entity.getNo() ;
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
    void fileNumberOnAction(ActionEvent event) {
        try {
            configureData(getAllTaxInvoiceByCLientIDAndFileID());
        }
        catch (Exception e ) {

        }

    }

    @FXML
    void print(MouseEvent event) {
        TaxEntity entity = table.getSelectionModel().getSelectedItem() ;
        if (entity == null ) {
            Alerts.WarningAlert("Please Select Item.");
            return;
        }
        try {
            String path = "taxInvoice.jrxml";
            JasperDesign jd = JRXmlLoader.load(path);
            JRDesignQuery jq = new JRDesignQuery();
            jq.setText(DataBase.printTaxInvoice(entity.getClient_id() , entity.getFileNumber() ,entity.getNo()));
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
        fileNo_List.getItems().clear();
        configureData(getAllTaxInvoiceByCLientID());
        try {
            DataBase.resultSet = DataBase.statement.executeQuery("select FileID  from filetracker where id_client=" + Integer.parseInt(idClient.getText()));
            while (DataBase.resultSet.next()) {
                int n = DataBase.resultSet.getInt(1);
                fileNo_List.getItems().add(n);
            }

        } catch (SQLException e) {
            Alerts.Error();
        }
    }

    @FXML
    void toOnAction(ActionEvent event) {
if (ClientName_List.getValue()==null) {
    fileNo_List.getItems().clear();
    configureData(getAllTaxInvoiceFromTo());
}
else {
    configureData(getAllTaxInvoiceByCLientIDAndFromTo());

}
    }
public static int NO_TAX_INVOICE = -1 ;
    @FXML
    void update(MouseEvent event) throws IOException {
        TaxEntity entity = table.getSelectionModel().getSelectedItem();
        if (entity == null ) {
            Alerts.WarningAlert("Please Select item .");
            return;
        }
        NO_TAX_INVOICE = entity.getNo();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("taxInvoice.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NO.setCellValueFactory(new PropertyValueFactory<>("no"));
        ClientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        fileNumber.setCellValueFactory(new PropertyValueFactory<>("fileNumber"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        clientID.setCellValueFactory(new PropertyValueFactory<>("client_id"));
        configureData(getAllTaxInvoice());
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
    @FXML
    void RefreshOnAction(ActionEvent event) {
        configureData(getAllTaxInvoice());

    }


    LinkedList<Integer> idTemp;

    ObservableList<TaxEntity> LIST;

    private void configureData(ResultSet resultSet) {
        try {
            table.getItems().clear();
            while (resultSet.next()) {
                table.getItems().add(new TaxEntity(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getDate(4).toLocalDate() , resultSet.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private ResultSet getAllTaxInvoice () {
        String query = "select TaxID , FileID , c.name_client , dateNow , c.id_client from taxInvoice ti join client c on c.id_client = ti.id_client";
        try {
            return DataBase.statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    private ResultSet getAllTaxInvoiceFromTo () {
        String s = "\"";
        String query = "select TaxID , FileID , c.name_client , dateNow , c.id_client from taxInvoice ti join client c on c.id_client = ti.id_client where dateNow between "+s+from.getValue().toString()+s +" and "+s+to.getValue().toString()+s;
        System.out.println(query);
        try {
            return DataBase.statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    private ResultSet getAllTaxInvoiceByCLientID () {

        String query = "select TaxID , FileID , c.name_client , dateNow , c.id_client from taxInvoice ti join client c on c.id_client="+idClient.getText()+" and (c.id_client = ti.id_client)";
        try {
            return DataBase.statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    private ResultSet getAllTaxInvoiceByCLientIDAndFileID () {
        String query = "select TaxID , FileID , c.name_client , dateNow , c.id_client from taxInvoice ti join client c on c.id_client="+idClient.getText()+" and (c.id_client = ti.id_client) where FileID="+fileNo_List.getValue().toString();
        System.out.println(query);

        try {
            return DataBase.statement.executeQuery(query);
        } catch (SQLException  e) {
Alerts.Error();

        }
        catch (NullPointerException e ) {
            Alerts.Error();
        }

        return null ;
    }

    private ResultSet getAllTaxInvoiceByCLientIDAndFromTo () {
        String s = "\"";
        String query = "select TaxID , FileID , c.name_client , dateNow , c.id_client from taxInvoice ti join client c on c.id_client="+idClient.getText()+" and (c.id_client = ti.id_client) where dateNow between "+s+from.getValue().toString()+s +" and "+s+to.getValue().toString()+s;
        try {
            return DataBase.statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }




   public class TaxEntity {
        private int no ;
        private int fileNumber ;
        private  String name ;
        private LocalDate date ;



       private int client_id ;

        public TaxEntity(int no, int fileNumber, String name, LocalDate date  , int clientID) {
            this.no = no;
            this.fileNumber = fileNumber;
            this.name = name;
            this.date = date;
            this.client_id = clientID ;
        }
       public int getClient_id() {
           return client_id;
       }

       public void setClient_id(int client_id) {
           this.client_id = client_id;
       }
        public int getNo() {
             return no;
         }

         public void setNo(int no) {
             this.no = no;
         }

         public int getFileNumber() {
             return fileNumber;
         }

         public void setFileNumber(int fileNumber) {
             this.fileNumber = fileNumber;
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
     }

}
