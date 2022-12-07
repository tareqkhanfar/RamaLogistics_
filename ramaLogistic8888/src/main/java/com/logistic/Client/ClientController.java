package com.logistic.Client;

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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.net.URL;
import java.sql.Blob;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private Hyperlink sendMessage;

    @FXML
    private ImageView addUser;

    @FXML
    private TextField search;

    @FXML
    private   TableColumn<ClientEntity, String> addressColumn;
    @FXML
    private TableColumn<ClientEntity, String> emailColumn;
    @FXML
    private TableView<ClientEntity> tableClient;
    public static TableView<ClientEntity> copyTableClient;

    @FXML
    private TableColumn<ClientEntity, Integer> idColumn;

    @FXML
    private TableColumn<ClientEntity, String> nameColumn;



    @FXML
    private TableColumn<ClientEntity, String> phoneColumn;




    @FXML
    private TableColumn<ClientEntity, String> telefxColumn;


    ObservableList<ClientEntity> list1 ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        copyTableClient = tableClient ;
        ArrayList<ClientEntity> list =configureData();
        for (ClientEntity i : list) {
            System.out.println(i.toString());
        }

        idColumn.setCellValueFactory( new PropertyValueFactory<ClientEntity, Integer>("id") );
  nameColumn.setCellValueFactory( new PropertyValueFactory<ClientEntity, String>("name") );
   emailColumn.setCellValueFactory( new PropertyValueFactory<ClientEntity, String>("email") );
  phoneColumn.setCellValueFactory( new PropertyValueFactory<ClientEntity, String>("phone") );
  telefxColumn.setCellValueFactory( new PropertyValueFactory<ClientEntity, String>("telefx") );
addressColumn.setCellValueFactory( new PropertyValueFactory<ClientEntity, String>("address") );
        list1 = FXCollections.observableArrayList(list);

tableClient.setItems(list1);


////////////////  Filter Clients /////////////////////////////////
configurSearch() ;

    }

    private void configurSearch() {
        FilteredList<ClientEntity> filteredList = new FilteredList<ClientEntity>(list1 , b->true);
        search.textProperty().addListener((observable  , oldValue , newValue )->{
            filteredList.setPredicate(cliententity->{
                if (newValue==null || newValue.isEmpty()){
                    return  true ;
                }

                String lowerCaseValue = newValue.toLowerCase();
                if((cliententity.getId()+"").toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else  if(cliententity.getName().toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else  if(cliententity.getEmail().toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else  if(cliententity.getAddress().toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else  if((cliententity.getPhone()).toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else  if((cliententity.getTelefx()).toLowerCase().indexOf(lowerCaseValue) != -1 ){
                    return  true;
                }
                else {
                    return false;
                }


            });
        });
        SortedList<ClientEntity>sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableClient.comparatorProperty());
        tableClient.setItems(sortedList);
    }

    private ArrayList<ClientEntity> configureData() {
        ArrayList<ClientEntity> list = new ArrayList<>();
        int id ;
        String name , address , email , phone , telefx ;
        try {
            DataBase.resultSet = DataBase.statement.executeQuery("select * from Client");
            while (DataBase.resultSet.next()){
                id = DataBase.resultSet.getInt(1);
                name = DataBase.resultSet.getString(2);
                address = DataBase.resultSet.getString(3);
                phone = DataBase.resultSet.getString(4);
                telefx = DataBase.resultSet.getString(5);
                email = DataBase.resultSet.getString(6);
                list.add(new ClientEntity(id , name , address , phone , telefx , email ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list ;
    }




@FXML
    void addUserOnAction(MouseEvent event) throws IOException {
    clientEntityTo_Update = null ;
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddClient.fxml"));
    Stage stage = fxmlLoader.load();
    stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Client.css").toExternalForm());
  AddClientController. FLAG = true;
    stage.show();
}
    @FXML
    void deleteUserOnAction(MouseEvent event)  {
        try {
            ClientEntity Temp = tableClient.getSelectionModel().getSelectedItem();

            DataBase.preparedStatement = DataBase.connection.prepareStatement("Delete from Client where id_Client=" + Temp.getId());
            DataBase.preparedStatement.executeUpdate();
           // tableClient.getItems().remove(Temp);

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
        ClientEntity Temp = tableClient.getSelectionModel().getSelectedItem();
        if (Temp == null ) {
            Alerts.ErrorAlert("Please Select client.");
            return;
        }
       DataBase.resultSet = DataBase.statement.executeQuery("select img , nameFile from Client where  id_Client="+Temp.getId());
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
    static  ClientEntity clientEntityTo_Update ;
    @FXML
    void updateOnAction(MouseEvent event) throws IOException {
      clientEntityTo_Update = tableClient.getSelectionModel().getSelectedItem();
     if (clientEntityTo_Update != null) {
         AddClientController. FLAG = false;

         FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddClient.fxml"));
         Stage stage = fxmlLoader.load();
         stage.getScene().getStylesheets().addAll(HelloApplication.class.getResource("CSS/Client.css").toExternalForm());
         stage.show();
         Alerts.Success();
     }
     else {
Alerts.WarningAlert("Please Select Client .");     }
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
        ArrayList<ClientEntity> list =configureData();
        list1 = FXCollections.observableArrayList(list);
        tableClient.setItems(list1);
    }


}
