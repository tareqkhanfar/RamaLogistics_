package com.logistic.Report;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import com.logistic.ramalogistic.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ReportCreditNote implements Initializable {
    @FXML
    private Label ClientID;

    @FXML
    private TableColumn<ReportCreditEntity, Integer> NO;

    @FXML
    private ComboBox<String> clientName;

    @FXML
    private TableColumn<ReportCreditEntity, String> clientNameC;

    @FXML
    private TableColumn<ReportCreditEntity, LocalDate> date;

    @FXML
    TableView <ReportCreditEntity> table ;
    @FXML
    void delete(MouseEvent event) {
ReportCreditEntity entity = table.getSelectionModel().getSelectedItem();
if (entity == null ) {
    Alerts.WarningAlert("Please Select item;");
    return;
}

String query = "delete from creditNote where CreditNO="+entity.getNo() ;
        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement(query) ;
            DataBase.preparedStatement.executeUpdate();
            table.getItems().remove(entity);
            Alerts.Success();
        } catch (Exception e) {
            Alerts.Error();
        }

    }

    @FXML
    void print(MouseEvent event) {
        ReportCreditEntity entity = table.getSelectionModel().getSelectedItem();
        if (entity == null ) {
            Alerts.WarningAlert("Please Select item;");
            return;
        }
        try {
            String path = "CreditNote.jrxml";
            JasperDesign jd = JRXmlLoader.load(path);
            JRDesignQuery jq = new JRDesignQuery();
            jq.setText(DataBase.printCreditNote(entity.getNo()));
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

    }

    public static int NO_TO_UPDATE = -1 ;
    @FXML
    void update(MouseEvent event) throws IOException {
        ReportCreditEntity entity = table.getSelectionModel().getSelectedItem();
        if (entity == null ) {
            Alerts.WarningAlert("Please Select item;");
            return;
        }
        NO_TO_UPDATE = entity.getNo();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreditNote.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stage.show();
    }
    @FXML
    void clientNameOnAction(ActionEvent event) {
        int x = clientName.getItems().indexOf(clientName.getValue().toString());
        ClientID.setText(idTemp.get(x)+"");
        configureData(getAllCreditNoteBYClientID());
    }
    LinkedList<Integer> idTemp ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NO.setCellValueFactory(new PropertyValueFactory<>("no"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        clientNameC.setCellValueFactory(new PropertyValueFactory<>("ClientName"));

        try {
            configureData(getAllCreditNote());
            DataBase.resultSet = DataBase.statement.executeQuery("select id_client , name_client from Client");
            idTemp = new LinkedList<>();
            while (DataBase.resultSet.next()) {
                idTemp.add(DataBase.resultSet.getInt(1));
                clientName.getItems().add(DataBase.resultSet.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void RefreshOnAction(ActionEvent event) {
        configureData(getAllCreditNote());

    }
    private void  configureData(ResultSet resultSet) {
        table.getItems().clear();
        try {
            while (resultSet.next()) {
                table.getItems().add(new ReportCreditEntity(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), resultSet.getString(3)));
            }
        }
        catch (Exception e ) {
            e.printStackTrace();
            Alerts.Error();
        }
    }
    private ResultSet getAllCreditNote() {
        String query = "select cn.CreditNO , cn.Date_ , c.name_client from creditnote cn join client c on c.id_client = cn.id_client" ;
        try {
            return DataBase.statement.executeQuery(query);
        } catch (SQLException e) {
            Alerts.Error();
        }
        return  null ;

    }

    private  ResultSet getAllCreditNoteBYClientID () {
        String sql = "select cn.CreditNO  , cn.Date_ , c.name_client from creditnote cn join creditNoteDetails cnd on " +
                "  (  cn.CreditNO = cnd.CreditNO) join client c on c.id_client ="+ClientID.getText()+" and " +
                "  ( c.id_client = cn.id_client) " ;
        try {
            return  DataBase.statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.Error();
        }
        return null ;
    }


    public class ReportCreditEntity  {
        private  String ClientName ;
private  int no ;
private LocalDate date ;

        public ReportCreditEntity(int no, LocalDate date , String ClientName) {
            this.no = no;
            this.date = date;
            this.ClientName = ClientName;
        }

        public String getClientName() {
            return ClientName;
        }

        public void setClientName(String clientName) {
            ClientName = clientName;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }
}
