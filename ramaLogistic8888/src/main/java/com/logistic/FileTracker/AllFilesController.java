package com.logistic.FileTracker;

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
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.logistic.DataBase.DataBase.connection;

public class AllFilesController implements Initializable {

    @FXML
    private TableColumn<FileTrackerEntity, String> ArivalDateC;

    @FXML
    private TableColumn<FileTrackerEntity, String> BalanceC;
    @FXML
    private RadioButton status;


    @FXML
    private TableColumn<FileTrackerEntity, String> ClientNameC;
    @FXML
    private RadioButton clernceRadio;

    @FXML
    private RadioButton shippingDocumentRadio;

   public   static int s = -1 , i = -1 , FID=-1;

      @FXML
      private Button refresh ;
    @FXML
    private TableColumn<FileTrackerEntity, Integer> FileNumberC;

    @FXML
    private TableColumn<FileTrackerEntity, String> ReleaseDate;



    @FXML
    private TableColumn<FileTrackerEntity, String> TransferAmountC;

    @FXML
    private MenuItem add;

    @FXML
    private TableColumn<FileTrackerEntity, String> fileAmountC;

    @FXML
    private TableColumn<FileTrackerEntity, String> openDateC;

    @FXML
    private MenuItem report;


    @FXML
    private TableColumn<FileTrackerEntity, String> sellingvalueC;

    @FXML
    private TableColumn<FileTrackerEntity, String> transferDate;

    @FXML
    private TableView<FileTrackerEntity> tableFilesOne;

    @FXML
    void addOnAction(MouseEvent event) throws IOException {
        AllFilesController.FID = -1 ;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Order.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Order.css").toExternalForm());
        stage.show();
    }

    static int FILE_ID_TO_STATUS = -1 ;
    @FXML
    void statusOnAction(ActionEvent event) throws IOException {
        status.setSelected(false);
        FileTrackerEntity fileTracker = tableFilesOne.getSelectionModel().getSelectedItem();
        if (fileTracker == null ) {
            Alerts.WarningAlert("Please Select item .");
            return;
        }
        FILE_ID_TO_STATUS = fileTracker.getFileNumber();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("status.fxml"));
        Stage stage = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Order.css").toExternalForm());
        stage.show();
    }




ObservableList<FileTrackerEntity> listFiles ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup tg = new ToggleGroup();
        tg.getToggles().addAll( shippingDocumentRadio , clernceRadio , status );
        try {
            listFiles = FXCollections.observableArrayList(configureData());
            FileNumberC.setCellValueFactory(new PropertyValueFactory<FileTrackerEntity , Integer>("FileNumber"));
            ClientNameC.setCellValueFactory(new PropertyValueFactory<FileTrackerEntity , String>("clientName"));
            openDateC.setCellValueFactory(new PropertyValueFactory<FileTrackerEntity , String>("openDate"));
            ArivalDateC.setCellValueFactory(new PropertyValueFactory<FileTrackerEntity , String>("arrivalDate"));
            ReleaseDate.setCellValueFactory(new PropertyValueFactory<FileTrackerEntity , String>("relaseDate"));
            transferDate.setCellValueFactory(new PropertyValueFactory<FileTrackerEntity , String>("transferDate"));
            fileAmountC.setCellValueFactory(new PropertyValueFactory<FileTrackerEntity , String>("FileAmount"));
            sellingvalueC.setCellValueFactory(new PropertyValueFactory<FileTrackerEntity , String>("sellingValue"));
            TransferAmountC.setCellValueFactory(new PropertyValueFactory<FileTrackerEntity , String>("transferAmount"));
            BalanceC.setCellValueFactory(new PropertyValueFactory<FileTrackerEntity , String>("Balance"));
            tableFilesOne.setItems(listFiles);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private ArrayList<FileTrackerEntity> configureData() throws SQLException {
        ArrayList<FileTrackerEntity> list = new ArrayList<>();
        int fileNumber ;
        String ClientName  ,fileAmount , sellingValue  ,transferAmount ,balance ;
        String arrivalDate , openDate ,relaseDate ,transferDate ;
        DataBase.resultSet = DataBase.statement.executeQuery("SELECT ft.FileID , c.name_Client , ft.openDate , ft.ArivalDate  ,ft.RELEASEDATE \n" +
                ", ft.TRANSFER_DATE , ft.FILEAMOUNT , ft.sellingValue , ft.TRANSFER_AMOUNT , ft.BALANCE\n" +
                "FROM filetracker ft join client c on  ft.id_client = c.id_Client; ");

        while (DataBase.resultSet.next()){
            fileNumber = DataBase.resultSet.getInt(1) ;
            ClientName = DataBase.resultSet.getString(2);
            openDate = DataBase.resultSet.getDate(3).toString()+"";
            arrivalDate = DataBase.resultSet.getDate(4).toString();
        //    status = DataBase.resultSet.getString(5);
            relaseDate = DataBase.resultSet.getDate(5).toString();
            transferDate = DataBase.resultSet.getDate(6).toString();
            fileAmount = DataBase.resultSet.getString(7);
            sellingValue = DataBase.resultSet.getString(8);
            transferAmount = DataBase.resultSet.getString(9);
            balance = DataBase.resultSet.getString(10);
            list.add(new FileTrackerEntity(fileNumber , ClientName , openDate , arrivalDate  , relaseDate ,transferDate ,fileAmount ,sellingValue , transferAmount , balance )) ;
        }
        return list ;
    }

    @FXML
    void shippingOnAction(ActionEvent event) {
        shippingDocumentRadio.setSelected(false);
        FileTrackerEntity fileTracker = tableFilesOne.getSelectionModel().getSelectedItem();
if (fileTracker == null ) {
    Alerts.WarningAlert("Please Select item .");
    return;
}
s = fileTracker.getFileNumber() ;
FID = fileTracker.getFileNumber();
i = -1 ;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("displayFiles.fxml"));
        Stage stage = null;
        try {
            stage = fxmlLoader.load();
            stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Order.css").toExternalForm());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void invoiceInAction(ActionEvent event) {
        clernceRadio.setSelected(false);
        FileTrackerEntity fileTracker = tableFilesOne.getSelectionModel().getSelectedItem();
        if (fileTracker == null ) {
            Alerts.WarningAlert("Please Select item .");
            return;
        }
        i = fileTracker.getFileNumber() ;
        FID = fileTracker.getFileNumber();
        s = -1 ;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("displayFiles.fxml"));
        Stage stage = null;
        try {
            stage = fxmlLoader.load();
            stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Order.css").toExternalForm());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void updateOnAction(MouseEvent event) throws IOException {

        FileTrackerEntity entity = tableFilesOne.getSelectionModel().getSelectedItem();
        if (entity == null ) {
            Alerts.WarningAlert("Please Select Item");
            return;
        }
        FID = entity.getFileNumber();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Order.fxml"));
        Stage stage = null;
        try {
            stage = fxmlLoader.load();
            stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Order.css").toExternalForm());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void deleteOnAction(MouseEvent event) {
       FileTrackerEntity fileTracker = tableFilesOne.getSelectionModel().getSelectedItem();
        try {
            connection.prepareStatement("DELETE FROM `ramalogistic`.`filetracker` where FileID="+fileTracker.getFileNumber()).executeUpdate();
            tableFilesOne.getItems().remove(fileTracker);
            Alerts.Success();
        }
        catch (SQLException e) {
            Alerts.FileTrackerNOTExist();
        }
        catch (Exception e) {
            Alerts.WarningAlert("Please Select Item.");
        }
    }

    @FXML
    void refreshOnAction(ActionEvent event) {
        try {
            listFiles = FXCollections.observableArrayList(configureData());
            tableFilesOne.setItems(listFiles);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
