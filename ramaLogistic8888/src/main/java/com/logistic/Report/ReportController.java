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
import javafx.scene.control.*;
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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    private TableView<ReportEntity> AllPaymentRequest;
    @FXML
    private TableColumn<ReportEntity, String> ChWeight;

    @FXML
    private TableColumn<ReportEntity, Integer> ClientID;

    @FXML
    private TableColumn<ReportEntity, String> ClientName;
    @FXML
    private TableColumn<ReportEntity, Integer> PaymentID;

    @FXML
    private TableColumn<ReportEntity, String> Currency;

    @FXML
    private TableColumn<ReportEntity, String> DescOfGoods;

    @FXML
    private TableColumn<ReportEntity, String> DischPort;

    @FXML
    private TableColumn<ReportEntity, Integer> FileID;

    @FXML
    private TableColumn<ReportEntity, String> GoodsValue;

    @FXML
    private TableColumn<ReportEntity, String> Incoterms;

    @FXML
    private TableColumn<ReportEntity, String> LoadPort;

    @FXML
    private TableColumn<ReportEntity, String> ManifestNo;

    @FXML
    private TableColumn<ReportEntity, String> PackageType;

    @FXML
    private TableColumn<ReportEntity, String> Pcs;

    @FXML
    private TableColumn<ReportEntity, String> TaxGoods;

    @FXML
    private TableColumn<ReportEntity, Float> TotalAmount;

    @FXML
    private TableColumn<ReportEntity, Float> TotalPrice;

    @FXML
    private TableColumn<ReportEntity, String> Volume;

    @FXML
    private TableColumn<ReportEntity, String> Weight;

    @FXML
    private TableColumn<ReportEntity, String> arrDate;

    @FXML
    private TableColumn<ReportEntity, String> carrier;

    @FXML
    private TableColumn<ReportEntity, String> date;

    @FXML
    private TableColumn<ReportEntity, String> depDate;

    @FXML
    private ComboBox<String> fileNo_List;

    @FXML
    private DatePicker from;

    @FXML
    private TableColumn<ReportEntity, String> hbl;

    @FXML
    private Label idClient;

    @FXML
    private TableColumn<ReportEntity, String> mbl;

    @FXML
    private TableColumn<ReportEntity, String> supplierName;

    @FXML
    private DatePicker to;

    @FXML
    private TableColumn<ReportEntity, String> tranportType;
    @FXML
    private ComboBox<String> ClientName_List;



    @FXML
    void fileNumberOnAction(ActionEvent event) throws SQLException {
if (idClient==null || idClient.getText()== "") {
    configureAllForAllClients(searchByFileID());
}
else {
    configureAllForAllClients(searchByClientIDAndFileID());
}
    }

    @FXML
    void selectClientName(ActionEvent event) throws SQLException {
        System.out.println(from.getValue());        System.out.println(to.getValue());


        int x = ClientName_List.getItems().indexOf(ClientName_List.getValue().toString());
        idClient.setText(idTemp.get(x) + "");
        fileNo_List.getItems().clear();
System.out.println(idClient.getText());
        DataBase.resultSet = DataBase.statement.executeQuery("select FileID  from filetracker where id_client=" +idClient.getText());
        while (DataBase.resultSet.next()) {
            int n = DataBase.resultSet.getInt(1);
            System.out.println(n);
            fileNo_List.getItems().add(n+"");

        }
       configureAllForAllClients(getAllPaymentRquestsByID());
    }

    LinkedList<Integer> idTemp;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientID.setCellValueFactory(new PropertyValueFactory<ReportEntity , Integer>("ClientID"));
        ClientName.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("ClientName"));
        date.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("date"));
        tranportType.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("tranportType"));
        supplierName.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("supplierName"));
        carrier.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("carrier"));
        mbl.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("mbl"));
        hbl.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("hbl"));
        depDate.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("depDate"));
        arrDate.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("arrDate"));

        DischPort.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("DischPort"));
        LoadPort.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("LoadPort"));
        ManifestNo.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("ManifestNo"));
        DescOfGoods.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("DescOfGoods"));
        Incoterms.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("Incoterms"));
        PackageType.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("PackageType"));
        Pcs.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("Pcs"));
        Weight.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("Weight"));
        ChWeight.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("ChWeight"));
        Volume.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("Volume"));
        GoodsValue.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("GoodsValue"));
        Currency.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("Currency"));
        FileID.setCellValueFactory(new PropertyValueFactory<ReportEntity , Integer>("FileID"));
        TaxGoods.setCellValueFactory(new PropertyValueFactory<ReportEntity , String>("TaxGoods"));
        TotalPrice.setCellValueFactory(new PropertyValueFactory<ReportEntity , Float>("TotalPrice"));
        TotalAmount.setCellValueFactory(new PropertyValueFactory<ReportEntity , Float>("TotalAmount"));
        PaymentID.setCellValueFactory(new PropertyValueFactory<ReportEntity , Integer>("PaymentID"));


        try {
            configureAllForAllClients(getAllPaymentRquests());

            DataBase.resultSet = DataBase.statement.executeQuery("select id_client , name_client from Client");
            idTemp = new LinkedList<>();

            while (DataBase.resultSet.next()) {

                idTemp.add(DataBase.resultSet.getInt(1));
                ClientName_List.getItems().add(DataBase.resultSet.getString(2));

            }
        }catch(SQLException e){
                e.printStackTrace();
            }
        }
    @FXML
    void RefreshOnAction(ActionEvent event) {
        try {
            configureAllForAllClients(getAllPaymentRquests());
        } catch (SQLException e) {
            Alerts.Error();
        }
    }
        private void configureAllForAllClients(ResultSet resultSet) throws SQLException {
            ArrayList<ReportEntity> list = new ArrayList<>();


DataBase.resultSet =  resultSet;

while (DataBase.resultSet.next()){
    ReportEntity reportEntity = new ReportEntity() ;
    reportEntity.setClientID(DataBase.resultSet.getInt(1));
    reportEntity.setClientName(DataBase.resultSet.getString(2));
     reportEntity.setDate(DataBase.resultSet.getString(3));
     reportEntity.setTranportType(DataBase.resultSet.getString(4));
     reportEntity.setSupplierName(DataBase.resultSet.getString(5));
     reportEntity.setCarrier(DataBase.resultSet.getString(6));
     reportEntity.setMbl(DataBase.resultSet.getString(7));
     reportEntity.setHbl(DataBase.resultSet.getString(8));
     reportEntity.setDepDate(DataBase.resultSet.getString(9));
     reportEntity.setArrDate(DataBase.resultSet.getString(10));
     reportEntity.setDischPort(DataBase.resultSet.getString(11));
     reportEntity.setLoadPort(DataBase.resultSet.getString(12));
     reportEntity.setManifestNo(DataBase.resultSet.getString(13));
     reportEntity.setDischPort(DataBase.resultSet.getString(14));
     reportEntity.setIncoterms(DataBase.resultSet.getString(15));
     reportEntity.setPackageType(DataBase.resultSet.getString(16));
     reportEntity.setPcs(DataBase.resultSet.getString(17));
     reportEntity.setWeight(DataBase.resultSet.getString(18));
     reportEntity.setChWeight(DataBase.resultSet.getString(19));
     reportEntity.setVolume(DataBase.resultSet.getString(20));
     reportEntity.setGoodsValue(DataBase.resultSet.getString(21));
     reportEntity.setCurrency(DataBase.resultSet.getString(22));
     reportEntity.setFileID(DataBase.resultSet.getInt(23));
    reportEntity.setPaymentID(DataBase.resultSet.getInt(24));
    reportEntity.setTaxGoods(DataBase.resultSet.getString(25));
     reportEntity.setTotalPrice(DataBase.resultSet.getString(26));
     reportEntity.setTotalAmount(DataBase.resultSet.getString(27));
     list.add(reportEntity) ;

}
            ObservableList<ReportEntity>listData = FXCollections.observableArrayList(list);
            AllPaymentRequest.setItems(listData);


        }
        public ResultSet getAllPaymentRquests() throws SQLException {
         DataBase.resultSet = DataBase.statement.executeQuery("select  c.id_client , c.name_client , pr.paymentdate , pr.TransportType , pr.supplierName ,\n" +
         "pr.carrier ,pr.mbl , pr.hbl ,pr.depDate ,pr.arrDate ,pr.DischPort , pr.LoadPort,pr.ManifestNo ,\n" +
         "pr.DescOfGoods , pr.Incoterms ,pr.PackageType ,pr.Pcs ,pr.Weight ,pr.ChWeight ,pr.Volume ,pr.GoodsValue,\n" +
         "pr.Currency , pr.FileID ,pr.paymentID,pr.tax  , sum(price),sum(amount) from paymentrequest pr join client c on c.id_client = pr.id_client join filetracker ft on ft.FileID =\n" +
         " pr.FileID join paymentDetails pd on pr.paymentID = pd.paymentID group by pr.paymentID;");

 return  DataBase.resultSet;
        }
    public ResultSet getAllPaymentRquestsByID() throws SQLException {
String queury = "select  c.id_client , c.name_client , pr.paymentdate , pr.TransportType , pr.supplierName ,\n" +
        "pr.carrier ,pr.mbl , pr.hbl ,pr.depDate ,pr.arrDate ,pr.DischPort , pr.LoadPort,pr.ManifestNo ,\n" +
        "pr.DescOfGoods , pr.Incoterms ,pr.PackageType ,pr.Pcs ,pr.Weight ,pr.ChWeight ,pr.Volume ,pr.GoodsValue,\n" +
        "pr.Currency , pr.FileID ,pr.paymentID,pr.tax  , sum(price),sum(amount) from paymentrequest pr join client c on c.id_client ="+ idClient.getText() +" and c.id_client = pr.id_client  join filetracker ft on ft.FileID =\n" +
        " pr.FileID join paymentDetails pd on pr.paymentID = pd.paymentID group by pr.paymentID;\n";

       return DataBase.statement.executeQuery(queury);


    }

    public ResultSet getAllPaymentRquestsByIDAndFrom_To() throws SQLException {
        String s ="\"";

        String queury = "select  c.id_client , c.name_client , pr.paymentdate , pr.TransportType , pr.supplierName ,\n" +
                "pr.carrier ,pr.mbl , pr.hbl ,pr.depDate ,pr.arrDate ,pr.DischPort , pr.LoadPort,pr.ManifestNo ,\n" +
                "pr.DescOfGoods , pr.Incoterms ,pr.PackageType ,pr.Pcs ,pr.Weight ,pr.ChWeight ,pr.Volume ,pr.GoodsValue,\n" +
                "pr.Currency , pr.FileID ,pr.paymentID,pr.tax  , sum(price),sum(amount) from paymentrequest pr join client c on "+idClient.getText()+" and c.id_client = pr.id_client  join filetracker ft on ft.FileID =\n" +
                " pr.FileID join paymentDetails pd on pr.paymentID = pd.paymentID where pr.paymentDate between "+s+from.getValue().toString()+s+" AND "+s+to.getValue().toString()+s+" group by pr.paymentID";
        return DataBase.statement.executeQuery(queury);

    }
    public ResultSet getPaymentRquestsByFromTO() throws SQLException {
        String s ="\"";

        String queury = "select  c.id_client , c.name_client , pr.paymentdate , pr.TransportType , pr.supplierName ,\n" +
                "pr.carrier ,pr.mbl , pr.hbl ,pr.depDate ,pr.arrDate ,pr.DischPort , pr.LoadPort,pr.ManifestNo ,\n" +
                "pr.DescOfGoods , pr.Incoterms ,pr.PackageType ,pr.Pcs ,pr.Weight ,pr.ChWeight ,pr.Volume ,pr.GoodsValue,\n" +
                "pr.Currency , pr.FileID ,pr.paymentID,pr.tax  , sum(price),sum(amount) from paymentrequest pr join client c on  c.id_client = pr.id_client  join filetracker ft on ft.FileID =" +
                "pr.FileID join paymentDetails pd on pr.paymentID = pd.paymentID where pr.paymentDate between "+s+from.getValue().toString()+s+" AND "+s+to.getValue().toString()+s+" group by pr.paymentID";
        System.out.println(queury);
        return DataBase.statement.executeQuery(queury);

    }
    public ResultSet searchByClientIDAndFileID() throws SQLException {
        String query = "select  c.id_client , c.name_client , pr.paymentdate , pr.TransportType , pr.supplierName ,\n" +
                "pr.carrier ,pr.mbl , pr.hbl ,pr.depDate ,pr.arrDate ,pr.DischPort , pr.LoadPort,pr.ManifestNo ,\n" +
                "pr.DescOfGoods , pr.Incoterms ,pr.PackageType ,pr.Pcs ,pr.Weight ,pr.ChWeight ,pr.Volume ,pr.GoodsValue,\n" +
                "pr.Currency , pr.FileID , pr.paymentID, pr.tax  , sum(price),sum(amount) from paymentrequest pr join client c on c.id_client="+idClient.getText()+" and c.id_client = pr.id_client  join filetracker ft on \n" +
                "ft.FileID="+fileNo_List.getValue().toString()+" and ft.FileID =pr.FileID join paymentDetails pd on pr.paymentID = pd.paymentID where pr.paymentDate group by pr.paymentID;\n";
        return DataBase.statement.executeQuery(query);

    }
    public ResultSet searchByFileID() throws SQLException {
        String query = "select  c.id_client , c.name_client , pr.paymentdate , pr.TransportType , pr.supplierName ,\n" +
                "pr.carrier ,pr.mbl , pr.hbl ,pr.depDate ,pr.arrDate ,pr.DischPort , pr.LoadPort,pr.ManifestNo ,\n" +
                "pr.DescOfGoods , pr.Incoterms ,pr.PackageType ,pr.Pcs ,pr.Weight ,pr.ChWeight ,pr.Volume ,pr.GoodsValue,\n" +
                "pr.Currency , pr.FileID , pr.paymentID, pr.tax  , sum(price),sum(amount) from paymentrequest pr join client c on  c.id_client = pr.id_client  join filetracker ft on \n" +
                "ft.FileID="+fileNo_List.getValue().toString()+" and ft.FileID =pr.FileID join paymentDetails pd on pr.paymentID = pd.paymentID where pr.paymentDate group by pr.paymentID;\n";
        return DataBase.statement.executeQuery(query);

    }
    @FXML
    void toOnAction(ActionEvent event) throws SQLException {
        if (from.getValue().toString() != null){

            if (ClientName_List.getValue() != null) {

                configureAllForAllClients(getAllPaymentRquestsByIDAndFrom_To());
            }
            else {
                configureAllForAllClients(getPaymentRquestsByFromTO());
            }
        }
        else {
            System.out.println("Please Select From");
        }

    }
    @FXML
    void deletePaymentRequestONAction(MouseEvent event) {
        ReportEntity reportEntity = AllPaymentRequest.getSelectionModel().getSelectedItem();
        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement("delete from paymentrequest where paymentID="+reportEntity.getPaymentID());
            DataBase.preparedStatement.executeUpdate();
            AllPaymentRequest.getItems().remove(reportEntity);
            Alerts.Success();

        } catch (SQLException e) {
            Alerts.Error();
        }
        catch (NullPointerException e2) {
            Alerts.WarningAlert("Please Select Item.");
        }
    }
    @FXML
    void printerOnAction(MouseEvent event)  {
        try {
            String path = "Blank_A4.jrxml";
            JasperDesign jd = JRXmlLoader.load(path);
            ReportEntity reportEntity = AllPaymentRequest.getSelectionModel().getSelectedItem();
            JRDesignQuery jq = new JRDesignQuery();
            jq.setText(DataBase.printPaymentRequest(reportEntity.getClientID(), reportEntity.getPaymentID(), reportEntity.getFileID()));
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
    public static int PAYMENT_ID_TO_UPDATE = -1 ;
    @FXML
    void updateOnAction(MouseEvent event) throws IOException {
        PAYMENT_ID_TO_UPDATE = -1 ;
        PAYMENT_ID_TO_UPDATE =AllPaymentRequest.getSelectionModel().getSelectedItem().getPaymentID() ;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("paymentRequestUpdate.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stage.setMaximized(true);
        stage.show();
    }


    }
