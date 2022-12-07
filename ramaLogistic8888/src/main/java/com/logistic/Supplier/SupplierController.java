package com.logistic.Supplier;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import com.logistic.ramalogistic.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Blob;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {
    @FXML
    private Hyperlink sendMessage;

    @FXML
    private ImageView addUser;

    @FXML
    private TextField search;

    @FXML
    private   TableColumn<SupplierEntity, String> addressColumn;
    @FXML
    private TableColumn<SupplierEntity, String> emailColumn;
    @FXML
    private TableView<SupplierEntity> tableSupplier;
    public static TableView<SupplierEntity> copyTableSupplier;

    @FXML
    private TableColumn<SupplierEntity, Integer> idColumn;

    @FXML
    private TableColumn<SupplierEntity, String> nameColumn;



    @FXML
    private TableColumn<SupplierEntity, String> phoneColumn;




    @FXML
    private TableColumn<SupplierEntity, String> telefxColumn;


    ObservableList<SupplierEntity> list11;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        copyTableSupplier = tableSupplier;
        ArrayList<SupplierEntity> listt =configureData();
        for (SupplierEntity i : listt) {
            System.out.println(i.toString());
        }

        idColumn.setCellValueFactory( new PropertyValueFactory<SupplierEntity, Integer>("id") );
  nameColumn.setCellValueFactory( new PropertyValueFactory<SupplierEntity, String>("name") );
   emailColumn.setCellValueFactory( new PropertyValueFactory<SupplierEntity, String>("email") );
  phoneColumn.setCellValueFactory( new PropertyValueFactory<SupplierEntity, String>("phone") );
  telefxColumn.setCellValueFactory( new PropertyValueFactory<SupplierEntity, String>("telefx") );
addressColumn.setCellValueFactory( new PropertyValueFactory<SupplierEntity, String>("address") );
        list11 = FXCollections.observableArrayList(listt);

tableSupplier.setItems(list11);


////////////////  Filter Clients /////////////////////////////////
configurSearch() ;

    }

    private void configurSearch() {
        FilteredList<SupplierEntity> filteredList = new FilteredList<SupplierEntity>(list11, b->true);
        search.textProperty().addListener((observable  , oldValue , newValue )->{
            filteredList.setPredicate(SupplierEntity->{
                if (newValue==null || newValue.isEmpty()){
                    return  true ;
                }

                String lowerCaseValue = newValue.toLowerCase();
                if((SupplierEntity.getId()+"").toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else  if(SupplierEntity.getName().toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else  if(SupplierEntity.getEmail().toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else  if(SupplierEntity.getAddress().toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else  if((SupplierEntity.getPhone()).toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else  if((SupplierEntity.getTelefx()).toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else {
                    return false;
                }


            });
        });
        SortedList<SupplierEntity>sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableSupplier.comparatorProperty());
        tableSupplier.setItems(sortedList);
    }

    private ArrayList<SupplierEntity> configureData() {
        ArrayList<SupplierEntity> list = new ArrayList<>();
        int id ;
        String name , address , email , phone , telefx ;
        try {
            DataBase.resultSet = DataBase.statement.executeQuery("select * from supplier");
            while (DataBase.resultSet.next()){
                id = DataBase.resultSet.getInt(1);
                name = DataBase.resultSet.getString(2);
                address = DataBase.resultSet.getString(3);
                phone = DataBase.resultSet.getString(4);
                telefx = DataBase.resultSet.getString(5);
                email = DataBase.resultSet.getString(6);
                list.add(new SupplierEntity(id , name , address , phone , telefx , email ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list ;
    }




@FXML
    void addUserOnAction(MouseEvent event) throws IOException {
        supplierEntityTo_Update = null ;
        System.out.println("Add supplier");
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddSupplier.fxml"));
    Stage stage = fxmlLoader.load();
    stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Client.css").toExternalForm());
  AddSupplierController. FLAG = true;
    stage.show();
}
    @FXML
    void deleteUserOnAction(MouseEvent event)  {
        try {
            SupplierEntity Temp = tableSupplier.getSelectionModel().getSelectedItem();

            DataBase.preparedStatement = DataBase.connection.prepareStatement("Delete from supplier where id_supplier=" + Temp.getId());
            DataBase.preparedStatement.executeUpdate();

            Alerts.Success();
        }
        catch (NullPointerException e) {
            Alerts.WarningAlert("Please Select Client .");
        }
        catch (SQLException e) {
            Alerts.Error();
        }

    }
    @FXML
    void downloadImage(MouseEvent event) throws SQLException, IOException {
        SupplierEntity Temp = tableSupplier.getSelectionModel().getSelectedItem();
        if (Temp == null ) {
            Alerts.ErrorAlert("Please Select Supplier.");
            return;
        }
       DataBase.resultSet = DataBase.statement.executeQuery("select img , nameFile from Supplier where  id_supplier="+Temp.getId());
        Blob blob = null;
String Filename ="";
       while (DataBase.resultSet.next()){
            blob =  DataBase.resultSet.getBlob(1);
           Filename = DataBase.resultSet.getString(2);
       }
       if (blob==null) {
Alerts.WarningAlert("There is no FILE for this client");
           return;
       }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        fileChooser.setInitialFileName(Filename);
        File file = fileChooser.showSaveDialog(new Stage());
        if (file==null) {
            Alerts.Error();
            return;
        }
        InputStream inputStream = blob.getBinaryStream();
        byte buffer[] = inputStream.readAllBytes() ;
        FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());
        fileOutputStream.write(buffer);
        fileOutputStream.close();
        fileOutputStream.flush();
        Alerts.Success();

    }
    static SupplierEntity supplierEntityTo_Update;
    @FXML
    void updateOnAction(MouseEvent event) throws IOException {
      supplierEntityTo_Update = tableSupplier.getSelectionModel().getSelectedItem();
     if (supplierEntityTo_Update != null) {
         AddSupplierController. FLAG = false;

         FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddSupplier.fxml"));
         Stage stage = fxmlLoader.load();
         stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Client.css").toExternalForm());
         stage.show();
         Alerts.Success();
     }
     else {
Alerts.WarningAlert("Please Select Supplier .");     }
    }

    @FXML
    void sendMessageOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Message.fxml"));
        Stage stage ;
        stage   = fxmlLoader.load();
        stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/PaymentRequest.css").toExternalForm());
        stage.show();

    }
    @FXML
    void RefreshOnAction(ActionEvent event) {
        ArrayList<SupplierEntity> list =configureData();
        list11 = FXCollections.observableArrayList(list);
        tableSupplier.setItems(list11);
    }


}
