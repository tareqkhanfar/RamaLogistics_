package com.logistic.Message;

import com.logistic.DataBase.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MessageController implements Initializable {
    @FXML
    private ComboBox<String> ClientName;

    @FXML
    private Label idClient;

    @FXML
    private TextArea message;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button send;

    @FXML
    void sendOnAction(ActionEvent event) throws IOException, URISyntaxException {
        Desktop d = Desktop.getDesktop() ;
        String s = "http://hotsms.ps/sendbulksms.php?user_name=RamaLogisic&user_pass=6412341&sender=Rama Log.&mobile="+phoneNumber.getText()+"&type=0&text="+message.getText() ;
        d.browse(new URI(s.replace(" ","%20")));
    }

    @FXML
    void ClientNameOnAction(ActionEvent event) {
        int x = ClientName.getItems().indexOf(ClientName.getValue().toString());
        idClient.setText( idTemp.get(x)+"");
        phoneNumber.setText(phoneTemp.get(x));
    }

    private LinkedList<String> idTemp , phoneTemp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DataBase.resultSet = DataBase.statement.executeQuery("select id_client , name_client , phone from Client");
            idTemp = new LinkedList<>();
            phoneTemp = new LinkedList<>();
            while (DataBase.resultSet.next()) {
                idTemp.add(DataBase.resultSet.getInt(1) + "");
                ClientName.getItems().add(DataBase.resultSet.getString(2));
                phoneTemp.add(DataBase.resultSet.getString(3) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
