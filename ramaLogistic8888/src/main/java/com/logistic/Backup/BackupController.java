package com.logistic.Backup;

import com.logistic.Alerts.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class BackupController implements Initializable {


    private static  String USER_NAME ;
    private static  String PASSWORD ;
    private static  String PATH_MY_SQL_DUMP;


    @FXML
    private TextField PATH;
File file ;
    @FXML
    void browse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        fileChooser.setInitialFileName(date+"_" +"backup.sql");
         file =  fileChooser.showSaveDialog(new Stage());
         if (file != null ) {
             String path = file.getAbsolutePath();
             PATH.setText(path);
             System.out.println(path);
         }
    }

    @FXML
    void export(ActionEvent event) throws IOException, InterruptedException {
     Runtime runtime = Runtime.getRuntime();
     Process process = runtime.exec(PATH_MY_SQL_DUMP+" -u"+USER_NAME+" -p"+PASSWORD+" --add-drop-database -B ramaLogistic -r"+PATH.getText());

        int p = process.waitFor();
        if (p == 0) {
            Alerts.Success();

            System.out.println("Done");
        }
        else {
            System.out.println("NOT Done");
            Alerts.Error();

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Scanner in = new Scanner(new File("ForDataBase.txt"));
            String[]feilds = null ;
            while (in.hasNext()){
              feilds = in.nextLine().split("#");
            }
            PATH_MY_SQL_DUMP  =feilds[0].trim();
            USER_NAME = feilds[1].trim();
            PASSWORD = feilds[2].trim();
            System.out.println(PATH_MY_SQL_DUMP);
            System.out.println(USER_NAME);
            System.out.println(PASSWORD);
            in.close();

        } catch (FileNotFoundException e) {
            Alerts.Error();
            e.printStackTrace();
        }
    }

}
