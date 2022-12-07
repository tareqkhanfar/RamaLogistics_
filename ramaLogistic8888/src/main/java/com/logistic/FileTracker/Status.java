package  com.logistic.FileTracker;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Status implements Initializable {

    @FXML
    private Label FIleID;

    @FXML
    private Button add;

    @FXML
    private TableColumn<StatusEntity, LocalDate> dateC;

    @FXML
    private DatePicker dateStatus;

    @FXML
    private Button delete;

    @FXML
    private TextArea status;

    @FXML
    private TableColumn<StatusEntity, String> statusC;

    @FXML
    private TableView<StatusEntity> table;

    @FXML
    void addOnAction(ActionEvent event) {
        String sql = "insert into statusFile (status_ ,FileID ,dateStatus) values (?,?,?)" ;
        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement(sql);
            DataBase.preparedStatement.setString(1 , status.getText());
            DataBase.preparedStatement.setInt(2 , Integer.parseInt(FIleID.getText()));
            DataBase.preparedStatement.setDate(3 , Date.valueOf(dateStatus.getValue()));
            DataBase.preparedStatement.executeUpdate();
            table.getItems().add(new StatusEntity(status.getText() , dateStatus.getValue()));
            Alerts.Success();

        } catch (Exception e) {
Alerts.Error();

        }

    }

    @FXML
    void deleteOnAction(ActionEvent event) {
StatusEntity entity = table.getSelectionModel().getSelectedItem();
if (entity == null ) {
    Alerts.WarningAlert("Please Select Item.");
    return;
}
String s = "\"" ;
String sql  = "delete from statusFile where FileID="+FIleID.getText() +" and status_="+s+entity.getStatus()+s;
        try {
            DataBase.preparedStatement = DataBase.connection.prepareStatement(sql) ;
            DataBase.preparedStatement.executeUpdate();
            table.getItems().remove(entity);
            Alerts.Success();
        } catch (Exception e) {
            e.printStackTrace();
Alerts.Error();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FIleID.setText(AllFilesController.FILE_ID_TO_STATUS+"");
        statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateC.setCellValueFactory(new PropertyValueFactory<>("date"));

        String query = "select * from statusFile where FileID="+FIleID.getText() ;
        try {
            DataBase.resultSet  = DataBase.statement.executeQuery(query) ;
            while (DataBase.resultSet.next()) {
                table.getItems().add(new StatusEntity(DataBase.resultSet.getString(1) , DataBase.resultSet.getDate(3).toLocalDate())) ;
            }
        } catch (SQLException e) {
            Alerts.Error();
        }


    }

    public class StatusEntity {
        private String status ;
        private LocalDate date ;

        public StatusEntity(String status, LocalDate date) {
            this.status = status;
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }

}
