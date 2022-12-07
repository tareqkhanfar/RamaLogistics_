package com.logistic.FileTracker;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class FileTrackerController implements Initializable {

    @FXML
    private TableColumn<FileEntity, String> FilenameShipping;

    @FXML
    private Button add;
    @FXML
    private RadioButton dollarFileAmount;

    @FXML
    private RadioButton dollarSellingValue;

    @FXML
    private RadioButton dollarTransferAmount;

    @FXML
    private RadioButton nisFileAmount;

    @FXML
    private RadioButton nisSellingValue;

    @FXML
    private RadioButton nisTransferAmount;


    @FXML
    private AnchorPane anchorpane;

    @FXML
    private DatePicker arivalDate;

    @FXML
    private ComboBox<String> clientName;

    @FXML
    private Label client_id;

    @FXML
    private TableColumn<FileEntity, Blob> dataInvoice;

    @FXML
    private TableColumn<FileEntity, Blob> dataShipping;

    @FXML
    private TextField fileAmount;

    @FXML
    private TextField fileNumber;

    @FXML
    private TableView<FileEntity> invoice;

    @FXML
    private TableColumn<FileEntity, String> nameInvoice;

    @FXML
    private DatePicker openDate;

    @FXML
    private DatePicker releaseDate;

    @FXML
    private TextField sellingValue;

    @FXML
    private TableView<FileEntity> shipping;



    @FXML
    private TextField transferAmount;

    @FXML
    private DatePicker transferDate;

    @FXML
    void addInvoiceOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileEntity entity = new FileEntity();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
                ,
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if( file != null )
        {
            try {
                if (AllFilesController.FID != -1 ) {
                    String query = "INSERT INTO `ramalogistic`.`invoice` ( `FileID`, `fileName`, `dataInvoice`) VALUES (?,?,?)" ;
                    DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
                    DataBase.preparedStatement.setInt(1 , Integer.parseInt(fileNumber.getText()));
                    DataBase.preparedStatement.setString(2 , file.getName());
                    DataBase.preparedStatement.setBlob(3 , new FileInputStream(file));
                    DataBase.preparedStatement.executeUpdate();
                }

                entity.setFileName(file.getName());
                entity.setData(new FileInputStream(file));
                invoice.getItems().add(entity);
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();

                Alerts.Error();
            }

        }
        else {
            Alerts.Error();
            System.out.println("Field is error ");
        }

    }

    @FXML
    void addOnAction(ActionEvent event) {
        String query = "" ;
        if (AllFilesController.FID != -1 ) {
            query = "UPDATE `ramalogistic`.`filetracker` " +
                    "SET `FileID` = ?, `id_client` = ?, `openDate` = ?, `ArivalDate` = ?, `FILEAMOUNT` = ?, " +
                    "`RELEASEDATE` = ?, `BALANCE` = ?, `TRANSFER_DATE` = ?, `TRANSFER_AMOUNT` =?, `sellingValue` = ? WHERE `FileID` =?" ;
        }else {

         query = "INSERT INTO `ramalogistic`.`filetracker` (`FileID`, `id_client`, `openDate`, `ArivalDate`, `FILEAMOUNT`,\n" +
           "`RELEASEDATE`, `BALANCE`, `TRANSFER_DATE`, `TRANSFER_AMOUNT`, `sellingValue`) VALUES (?,?,?,?,?,?,?,?,?,?)"; }

        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
            DataBase.preparedStatement.setInt(1 , Integer.parseInt(fileNumber.getText()));
            DataBase.preparedStatement.setInt(2 , Integer.parseInt(client_id.getText()));
            DataBase.preparedStatement.setDate(3 , Date.valueOf(openDate.getValue()));
            DataBase.preparedStatement.setDate(4 , Date.valueOf(arivalDate.getValue()));
            if (nisFileAmount.isSelected()){
                DataBase.preparedStatement.setString(5 , fileAmount.getText().trim()+"NIS");

            }
            else {
                DataBase.preparedStatement.setString(5 , fileAmount.getText().trim()+"$");

            }
            DataBase.preparedStatement.setDate(6 , Date.valueOf(releaseDate.getValue()));
            DataBase.preparedStatement.setString(7 , Float.parseFloat(sellingValue.getText().trim()) - Float.parseFloat(fileAmount.getText().trim())+"");
            DataBase.preparedStatement.setDate(8 , Date.valueOf(transferDate.getValue()));
            if (nisTransferAmount.isSelected()) {
                DataBase.preparedStatement.setString(9 , transferAmount.getText().trim()+"NIS");

            }
            else {
                DataBase.preparedStatement.setString(9 , transferAmount.getText().trim()+"$");

            }
            if (nisSellingValue.isSelected()){
                DataBase.preparedStatement.setString(10 , sellingValue.getText().trim()+"NIS");

            }
            else {
                DataBase.preparedStatement.setString(10 , sellingValue.getText().trim()+"$");
            }

            if (AllFilesController.FID != -1) {
                DataBase.preparedStatement.setInt(11 , Integer.parseInt(fileNumber.getText()));
            }
            DataBase.preparedStatement.executeUpdate();
if (AllFilesController.FID == -1) {
    for (FileEntity entity : shipping.getItems()) {
        saveShippingDocument(entity.getFileName(), entity.getData());
    }
    for (FileEntity entity : invoice.getItems()) {
        saveInvoice(entity.getFileName(), entity.getData());
    }
}
            Alerts.Success();

        } catch (SQLException e) {
Alerts.Error();
        }
    }

    @FXML
    void addShippingOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileEntity entity = new FileEntity();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
                ,
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if( file != null )
        {
            try {
                if (AllFilesController.FID != -1 ) {
                    String query = "INSERT INTO `ramalogistic`.`shipping` ( `FileID`, `fileName`, `dataShipping`) VALUES (?,?,?)" ;
                    DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
                    DataBase.preparedStatement.setInt(1 , Integer.parseInt(fileNumber.getText()));
                    DataBase.preparedStatement.setString(2 , file.getName());
                    DataBase.preparedStatement.setBlob(3 , new FileInputStream(file));
                    DataBase.preparedStatement.executeUpdate();
                }
                entity.setFileName(file.getName());

                entity.setData(new FileInputStream(file));
                shipping.getItems().add(entity);
            } catch (FileNotFoundException | SQLException e) {

              Alerts.Error();
            }

        }
        else {
            Alerts.Error();
            System.out.println("Field is error ");
        }

    }

    @FXML
    void clientNameOnAction(ActionEvent event) {
        int x = clientName.getItems().indexOf(clientName.getValue().toString());
        client_id.setText(idTemp.get(x) + "");
    }

    @FXML
    void deleteInvoiceOnAction(ActionEvent event) {
        FileEntity  entity = invoice.getSelectionModel().getSelectedItem();
if (entity == null ) {
    Alerts.WarningAlert("please Select Item");
    return;
}
if (AllFilesController.FID != -1 ) {
    String s = "\"" ;

    String query = "delete from invoice where FileID="+AllFilesController.FID+" and fileName="+s+entity.getFileName()+s ;
    try {
        DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
        DataBase.preparedStatement.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }

}
invoice.getItems().remove(entity);
    }

    @FXML
    void deleteShippingOnAction(ActionEvent event) {
        FileEntity entity = shipping.getSelectionModel().getSelectedItem();
        if (entity == null) {
            Alerts.WarningAlert("please Select Item");
            return;
        }
        String s = "\"" ;
        if (AllFilesController.FID != -1) {
            String query = "delete from shipping where FileID=" + AllFilesController.FID + " and fileName=" +s+ entity.getFileName()+s;
            try {
                DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
                DataBase.preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        shipping.getItems().remove(entity);

    }
    LinkedList<Integer> idTemp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup tg1 = new ToggleGroup();
        tg1.getToggles().addAll(nisSellingValue , dollarSellingValue);
        ToggleGroup tg2 = new ToggleGroup();
        tg2.getToggles().addAll(nisFileAmount , dollarFileAmount);

        ToggleGroup tg3 = new ToggleGroup();
        tg3.getToggles().addAll(nisTransferAmount , dollarTransferAmount);

        fileNumber.setDisable(false);
        nisSellingValue.setSelected(false);
        nisFileAmount.setSelected(false);
        nisTransferAmount.setSelected(false);

        dollarSellingValue.setSelected(false);
        dollarFileAmount.setSelected(false);
        dollarTransferAmount.setSelected(false);



        FilenameShipping.setCellValueFactory(new PropertyValueFactory<>("FileName"));
        dataShipping.setCellValueFactory(new PropertyValueFactory<>("Data"));
        nameInvoice.setCellValueFactory(new PropertyValueFactory<>("FileName"));
        dataInvoice.setCellValueFactory(new PropertyValueFactory<>("Data"));

        try {
            if (AllFilesController.FID != -1) {
               DisplayData() ;
            }
            else {
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
       String query =  "select * from filetracker where FileID="+AllFilesController.FID;
        try {
            fileNumber.setDisable(true);
            DataBase.resultSet = DataBase.statement.executeQuery(query);
            DataBase.resultSet.next();
            fileNumber.setText(DataBase.resultSet.getInt(1)+"");
            client_id.setText(DataBase.resultSet.getInt(2)+"");
            openDate.setValue(DataBase.resultSet.getDate(3).toLocalDate());
            arivalDate.setValue(DataBase.resultSet.getDate(4).toLocalDate());
            if (DataBase.resultSet.getString(5).contains("$")) {
                fileAmount.setText(DataBase.resultSet.getString(5).replace("$" , ""));
              dollarFileAmount.setSelected(true);
            }
            else {
                fileAmount.setText(DataBase.resultSet.getString(5).replace("NIS",""));
                nisFileAmount.setSelected(true);

            }
            releaseDate.setValue(DataBase.resultSet.getDate(6).toLocalDate());
            transferDate.setValue(DataBase.resultSet.getDate(8).toLocalDate());

            if (DataBase.resultSet.getString(9).contains("$")){
                transferAmount.setText(DataBase.resultSet.getString(9).replace("$" , ""));
                dollarTransferAmount.setSelected(true);
            }
            else {
                transferAmount.setText(DataBase.resultSet.getString(9).replace("NIS" , ""));
                nisTransferAmount.setSelected(true);
            }
            if (DataBase.resultSet.getString(10).contains("$")) {
                sellingValue.setText(DataBase.resultSet.getString(10).replace("$" , ""));
                dollarSellingValue.setSelected(true);
            }
            else {
                sellingValue.setText(DataBase.resultSet.getString(10).replace("NIS" , ""));
                nisSellingValue.setSelected(true);
            }


            String query2 = "select fileName , dataShipping from shipping where FileID="+AllFilesController.FID ;
            DataBase.resultSet  = DataBase.statement.executeQuery(query2);
            while (DataBase.resultSet.next()){
                FileEntity entity = new FileEntity();
                entity.setFileName(DataBase.resultSet.getString(1));
                entity.setData(DataBase.resultSet.getBlob(2).getBinaryStream());
                shipping.getItems().add(entity);
            }

            String query3 = "select fileName , dataInvoice from invoice where FileID="+AllFilesController.FID ;
            DataBase.resultSet  = DataBase.statement.executeQuery(query3);
            while (DataBase.resultSet.next()){
                FileEntity entity = new FileEntity();
                entity.setFileName(DataBase.resultSet.getString(1));
                entity.setData(DataBase.resultSet.getBlob(2).getBinaryStream());
                invoice.getItems().add(entity);
            }


        } catch (SQLException e) {
Alerts.Error();

        }
        catch (Exception e ){
            Alerts.Error();

        }

    }

    private void saveShippingDocument(String FileName , InputStream f) {
        String query = "INSERT INTO `ramalogistic`.`shipping` ( `FileID`, `fileName`, `dataShipping`) VALUES (?,?,?)" ;
        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
            DataBase.preparedStatement.setInt(1 , Integer.parseInt(fileNumber.getText().trim()));
            DataBase.preparedStatement.setString(2 , FileName);
            DataBase.preparedStatement.setBlob(3 , f);
            DataBase.preparedStatement.executeUpdate();
        } catch (SQLException e) {
Alerts.Error();
        }

    }

    private void saveInvoice(String FileName , InputStream f) {
        String query = "INSERT INTO `ramalogistic`.`invoice` ( `FileID`, `fileName`, `dataInvoice`) VALUES (?,?,?)" ;
        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
            DataBase.preparedStatement.setInt(1 , Integer.parseInt(fileNumber.getText().trim()));
            DataBase.preparedStatement.setString(2 , FileName);
            DataBase.preparedStatement.setBlob(3 , f);
            DataBase.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Alerts.Error();
        }

    }
}
