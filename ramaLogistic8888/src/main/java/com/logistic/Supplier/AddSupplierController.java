package com.logistic.Supplier;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

public class AddSupplierController implements Initializable {

    @FXML
    private Button Save;

    @FXML
    private Label FileName;

    @FXML
    private TextField address;

    @FXML
    private TextField email;

    @FXML
    private TextField id;


    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField telefx;
    private InputStream inputData ;
String nameFile = "" ;
    @FXML
    void IDImageOnAction(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if( selectedFile != null )
        {
            inputData   = new FileInputStream(selectedFile) ;

            nameFile = selectedFile.getName();
            FileName.setText(nameFile);
        }

    }


public static boolean FLAG = false ; // to distinguished update or Inserte , if = false then update , else if = true then insert
    @FXML
    void SaveOnAction(ActionEvent event) {
        try {
            if (!FLAG){
                String query = "update Supplier set id_Supplier=? , name_supplier=? ,address_supplier=? , phone=? , telefx=? , email=? , img=? , nameFile=? where id_supplier="+id.getText() ;
                DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
            }else {
                System.out.println("ADd111");
                DataBase.preparedStatement= DataBase.connection.prepareStatement("insert into Supplier(id_Supplier ,name_supplier,address_supplier,phone,telefx,email,img, nameFile) " +
                        "values(?,?,?,?,?,?,? , ?)");
            }


            int idTemp =Integer.parseInt(id.getText().trim());
            String nameTemp = name.getText().trim();
            String emailTemp = email.getText().trim();
            String phoneTemp =phone.getText().trim();
            String telefxTemp =telefx.getText().trim();
            String addressTemp = address.getText().trim();

            DataBase.preparedStatement.setInt(1,idTemp);
            DataBase.preparedStatement.setString(2,nameTemp);
            DataBase.preparedStatement.setString(3,addressTemp);
            DataBase.preparedStatement.setString(4,phoneTemp);
            DataBase.preparedStatement.setString(5,telefxTemp);
            DataBase.preparedStatement.setString(6,emailTemp);
            DataBase.preparedStatement.setBlob(7,inputData);
            DataBase.preparedStatement.setString(8,nameFile);

            try {
                DataBase.preparedStatement.executeUpdate();
                System.out.println("ADd1");

                Alerts.Success();
                SupplierController.supplierEntityTo_Update = null ;

            }
            catch (SQLIntegrityConstraintViolationException e) {
                SupplierController.supplierEntityTo_Update = null ;
                Alerts. Error();

            }

        } catch (SQLException e) {
            Alerts.WarningAlert("Supplier ID Already Exist .");
            SupplierController.supplierEntityTo_Update = null ;

        }
        catch (Exception e) {
            Alerts.FiledsNotEmptyOrSqlError();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       if (SupplierController.supplierEntityTo_Update != null ){ // this mean to update
           SupplierEntity supplierEntity = SupplierController.supplierEntityTo_Update;

System.out.println("Update");
           String query = "select img , nameFile from supplier where id_supplier="+supplierEntity.getId();
           try {
               DataBase.resultSet = DataBase.statement.executeQuery(query);
               DataBase.resultSet.next();
               id.setText(supplierEntity.getId()+"");
               name.setText(supplierEntity.getName()+"");
               address.setText(supplierEntity.getAddress()+"");
               email.setText(supplierEntity.getEmail()+"");
               telefx.setText(supplierEntity.getTelefx()+"");
               phone.setText(supplierEntity.getPhone()+"");
               if ( DataBase.resultSet.getBlob(1)!=null){
                   inputData = DataBase.resultSet.getBlob(1).getBinaryStream();
               }
               FileName.setText(DataBase.resultSet.getString(2)+"");
           } catch (SQLException e) {
               Alerts.Error();
           }



       }
    }
}
