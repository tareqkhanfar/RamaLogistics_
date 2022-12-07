package com.logistic.Setting;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import com.logistic.ramalogistic.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Scanner;

public class SettingController {
    @FXML
    private TextField address;

    @FXML
    private TextField confirm;

    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField phone;

    @FXML
    private TextField telefx;

    @FXML
    private TextField username;

    @FXML
    void save(ActionEvent event) {
        try {
            PrintWriter out = new PrintWriter(new File("info.txt"));
            out.println("Name : " + name.getText());
            out.println("Email : " + email.getText());
            out.println("Phone : " + phone.getText());
            out.println("Telefx : " + telefx.getText());
            out.println("Address : " + address.getText());
            out.close();
            Alerts.Success();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void savePassword(ActionEvent event) {
String query = "update login set username=? , password = ? where username=?" ;
        try {
           DataBase.preparedStatement =  DataBase.connection.prepareStatement(query);
            DataBase.preparedStatement.setString(1 , username.getText());
            DataBase.preparedStatement.setString(2 , newPassword.getText());
            DataBase.preparedStatement.setString(3 , HelloController.USERNAME);
if (newPassword.getText().equals(confirm.getText())){
    DataBase.preparedStatement.executeUpdate();
    Alerts.Success();
}
else {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setContentText("Password does not match");
    alert.showAndWait();
}
        } catch (SQLException e) {
            Alerts.Error();
        }

    }

}
