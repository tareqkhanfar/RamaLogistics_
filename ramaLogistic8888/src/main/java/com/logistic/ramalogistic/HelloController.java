package com.logistic.ramalogistic;

import com.logistic.DataBase.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private Button enter;

    @FXML
    private PasswordField password;

    @FXML
    private TextField userName;

    public static String USERNAME ;
    @FXML
    void EnterOnAction(ActionEvent event) throws IOException {

        try {
            DataBase.resultSet = DataBase.statement.executeQuery("select * from Login");
            String username = "" , password = "";
            while ( DataBase.resultSet.next()){
                username = DataBase.resultSet.getString(1);
                password = DataBase.resultSet.getString(2);
            }
            if (this.userName.getText().equals(username) && this.password.getText().equals(password)) {
                USERNAME = this.userName.getText();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home.fxml"));
                Scene scene =new Scene(fxmlLoader.load());
                scene.getStylesheets().addAll(getClass().getResource("CSS/style.css").toExternalForm());
                HelloApplication.stage.setScene(scene);
                HelloApplication.stage.setMaximized(true);
            }
            else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("user name or password is not correct ...");
                a.showAndWait();
            }
        } catch (SQLException e) {


        }




    }


}
