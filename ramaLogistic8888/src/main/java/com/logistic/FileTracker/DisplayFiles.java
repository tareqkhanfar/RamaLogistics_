package com.logistic.FileTracker;

import com.logistic.Alerts.Alerts;
import com.logistic.DataBase.DataBase;
import com.logistic.ramalogistic.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DisplayFiles implements Initializable {

    @FXML
    private TableColumn<FileEntity, String> Filename;

    @FXML
    private TableColumn<FileEntity, FileInputStream> data;

    @FXML
    private TableView<FileEntity> table;



    @FXML
    void display(ActionEvent event) {
        FileEntity entity = table.getSelectionModel().getSelectedItem();
        if (entity == null) {
            Alerts.WarningAlert("Please Select Item .");
            return;
        }
        DisplayData(entity) ;
    }

    private void DisplayData(FileEntity entity) {
        Desktop desktop = Desktop.getDesktop() ;
        String PATH= HelloApplication.TEMP_PATH +entity.getFileName();
        try {
            desktop.open(new File (PATH));
        }
        catch (IllegalArgumentException e ) {
            saveFileThenOPEN(entity);
        }
        catch (Exception e) {
Alerts.Error();
        }
    }

    @FXML
    void saveAs(ActionEvent event) {
FileEntity entity = table.getSelectionModel().getSelectedItem();
if (entity == null) {
    Alerts.WarningAlert("Please Select Item .");
    return;
}
saveAsFile(entity);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Filename.setCellValueFactory(new PropertyValueFactory<>("FileName"));
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
String query = "" ;
        if (AllFilesController.s != -1 ) {
            query = "select * from shipping where FileID="+AllFilesController.FID ;
        }
        if (AllFilesController.i != -1 ) {
            query = "select * from invoice where FileID="+AllFilesController.FID ;
        }
        try {
            DataBase.resultSet = DataBase.statement.executeQuery(query) ;
            while (DataBase.resultSet.next()) {
                if (DataBase.resultSet.getBlob(4) != null ) {
                    table.getItems().add(new FileEntity(DataBase.resultSet.getString(3), DataBase.resultSet.getBlob(4).getBinaryStream()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void saveAsFile(FileEntity entity) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            fileChooser.setInitialFileName(entity.getFileName());
            File file = fileChooser.showSaveDialog(new Stage());
            InputStream inputStream = entity.getData() ;
            byte buffer[] = inputStream.readAllBytes();
            FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());
            fileOutputStream.write(buffer);
            fileOutputStream.close();
            fileOutputStream.flush();
            Alerts.Success();

        }
        catch (Exception e) {
            Alerts.Error();
        }
    }

    public void saveFileThenOPEN(FileEntity entity) {
        try {
            InputStream inputStream = entity.getData() ;
            byte buffer[] = inputStream.readAllBytes();
            String PATH = HelloApplication.TEMP_PATH +entity.getFileName();
            System.out.println(PATH);
            FileOutputStream fileOutputStream = new FileOutputStream(PATH);
            fileOutputStream.write(buffer);
            fileOutputStream.close();
            fileOutputStream.flush();
            Desktop.getDesktop().open(new File(PATH));
            Alerts.Success();


        }
        catch (Exception e) {
            Alerts.Error();
        }
    }


}

