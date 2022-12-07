package com.logistic.CreditNote;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import com.logistic.Report.ReportCreditNote;
import com.logistic.Report.ReportEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class CreditNoteController implements Initializable {
    @FXML
    private DatePicker date;
    @FXML
    private Label ClientID;

    @FXML
    private TextField Desc;

    @FXML
    private ComboBox<String> clientName;

    @FXML
    private TableColumn<CreditNoteEntity, String> DescriptionC;

    @FXML
    private TableColumn<CreditNoteEntity, String> ValueC;

    @FXML
    private ImageView save;

    @FXML
    private TextArea text;

    @FXML
    private TextField value;

    @FXML
    private TextField year;
    @FXML
    private TableView<CreditNoteEntity> table;
    @FXML
    private Label NO;


    @FXML
    void addOnAction(ActionEvent event) {
        try {
            CreditNoteEntity entity = new CreditNoteEntity(Desc.getText(), value.getText());

            if (ReportCreditNote.NO_TO_UPDATE != -1 ) {
                String query = "insert into creditnotedetails (CreditNo , Description ,Value_) values (? , ? , ? ) ";
                DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
                DataBase.preparedStatement.setInt(1, ReportCreditNote.NO_TO_UPDATE);
                DataBase.preparedStatement.setString(2, entity.getDiscription());
                DataBase.preparedStatement.setString(3, entity.getValue());
                DataBase.preparedStatement.executeUpdate();
                Alerts.Success();

            }
            table.getItems().add(entity);
            Alerts.Success();
        }
         catch (NumberFormatException e ) {
            Alerts.ErrorAlert("Enter a valid values.");
         }
        catch (Exception e2){
            Alerts.Error();
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        CreditNoteEntity creditNoteEntity = table.getSelectionModel().getSelectedItem() ;
        if (creditNoteEntity == null ) {
            Alerts.WarningAlert("Please Select item ");
            return ;
        }
        if (ReportCreditNote.NO_TO_UPDATE != -1 ) {

            try {
                String s = "\"" ;
                String query = "delete from creditnotedetails where creditNo="+ReportCreditNote.NO_TO_UPDATE +" and Description="+s+creditNoteEntity.getDiscription()+s ;
               System.out.println(query);
                DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
                DataBase.preparedStatement.executeUpdate();
                table.getItems().remove(creditNoteEntity) ;
                Alerts.Success();
            } catch (SQLException e) {
Alerts.Error();
            }
        }
        else {
            table.getItems().remove(creditNoteEntity) ;
            Alerts.Success();
        }

    }

    @FXML
    void printONAction(MouseEvent event) {
        try {
            String path = "CreditNote.jrxml";
            JasperDesign jd = JRXmlLoader.load(path);
            JRDesignQuery jq = new JRDesignQuery();
            jq.setText(DataBase.printCreditNote(Integer.parseInt(NO.getText().substring(4).trim())));
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
private ObservableList<CreditNoteEntity>list ;
    @FXML
    void saveOnAction(MouseEvent event) {
        String query = "" ;
        if (ReportCreditNote.NO_TO_UPDATE != -1 ) {
            query = "UPDATE `ramalogistic`.`creditnote` SET CreditNO=? ,`Year_` =? , `Message`=?, `Date_` =? WHERE `CreditNO` = ?" ;
        }
        else {
            query = "INSERT INTO `ramalogistic`.`creditnote`(`CreditNO`,`Year_`,`Message`,`Date_` , id_client) VALUES(? , ? ,? ,? , ?)";
        }
        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
            DataBase.preparedStatement.setInt(1 , n);
            DataBase.preparedStatement.setInt(2,Integer.parseInt(year.getText()) );
            DataBase.preparedStatement.setString(3,text.getText() );
            DataBase.preparedStatement.setDate(4 , Date.valueOf(date.getValue()));
            if (ReportCreditNote.NO_TO_UPDATE == -1 ){
                DataBase.preparedStatement.setInt(5 , Integer.parseInt(ClientID.getText()));

            }

            if (ReportCreditNote.NO_TO_UPDATE != -1 ) {
                DataBase.preparedStatement.setInt(5 , n);
            }
            DataBase.preparedStatement.executeUpdate();
            if (ReportCreditNote.NO_TO_UPDATE == -1 ) {
                list = table.getItems();
                for (CreditNoteEntity entity : list) {
                    saveCreditNoteDetails(entity);
                }
            }
                 Alerts.Success();

        } catch (SQLException e) {
Alerts.Error();        }
        catch (NullPointerException e2) {
            Alerts.WarningAlert("Please Fill all Fields");
        }
        catch (Exception e3) {
            Alerts.WarningAlert("unKnown Error");
        }

    }
    @FXML
    void clientNameOnAction(ActionEvent event) {
        int x = clientName.getItems().indexOf(clientName.getValue().toString());
        ClientID.setText(idTemp.get(x)+"");
    }
    static int n ;
    LinkedList<Integer> idTemp ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            DescriptionC.setCellValueFactory(new PropertyValueFactory<>("discription"));
            ValueC.setCellValueFactory(new PropertyValueFactory<>("value"));
if (ReportCreditNote.NO_TO_UPDATE != -1 ) {
    DisplayData() ;

}else {
    DataBase.resultSet = DataBase.statement.executeQuery("select id_client , name_client from Client");
    idTemp = new LinkedList<>();
    while (DataBase.resultSet.next()) {
        idTemp.add(DataBase.resultSet.getInt(1));
        clientName.getItems().add(DataBase.resultSet.getString(2));
    }

    String query = "select max(creditNo) from creditNote";
    DataBase.resultSet = DataBase.statement.executeQuery(query);
    DataBase.resultSet.next();
    n = DataBase.resultSet.getInt(1) + 1;
    NO.setText("NO. " + n);
}
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }

    private void DisplayData() {
        try {
            DataBase.resultSet = DataBase.statement.executeQuery("select * from creditNote where CreditNO="+ReportCreditNote.NO_TO_UPDATE );
            DataBase.resultSet.next();
            n = DataBase.resultSet.getInt(1) ;
            NO.setText("NO.:"+n);
            year.setText(DataBase.resultSet.getInt(2)+"");
            text.setText(DataBase.resultSet.getString(3));
            date.setValue(DataBase.resultSet.getDate(4).toLocalDate());
            String query = "select * from creditnotedetails  where CreditNO="+ReportCreditNote.NO_TO_UPDATE ;
            DataBase.resultSet = DataBase.statement.executeQuery(query);

            while (DataBase.resultSet.next()){
               table.getItems().add(new CreditNoteEntity(DataBase.resultSet.getString(3) , DataBase.resultSet.getString(4)));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveCreditNoteDetails (CreditNoteEntity creditNoteEntity) throws SQLException {
        String query = "INSERT INTO `ramalogistic`.`creditnotedetails` (`CreditNO`,`Description`,`Value_`) VALUES (?,?,?)" ;
        DataBase.preparedStatement = DataBase.connection.prepareStatement(query);
        DataBase.preparedStatement.setInt(1 ,  n);
        DataBase.preparedStatement.setString(2,creditNoteEntity.getDiscription());
        DataBase.preparedStatement.setString(3,creditNoteEntity.getValue());
        DataBase.preparedStatement.executeUpdate();

    }
}
