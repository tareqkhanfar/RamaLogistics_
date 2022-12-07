package com.logistic.ramalogistic;

import com.logistic.DataBase.DataBase;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class HelloApplication extends Application {
    public static Stage stage;
    public  static String TEMP_PATH ;
    @Override
    public void start(Stage stagee) throws IOException {
        this.stage = stagee ;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 784, 458);
        scene.getStylesheets().addAll(getClass().getResource("CSS/style.css").toExternalForm());

        this.stage.setTitle("Hello!");
        this.stage.setScene(scene);
        this.stage.show();
    }
private static String Link = "" , userName = "" , Password="" ;

    public static void main(String[] args) throws SQLException, IOException {
        Build_TEMP_PATH();
        Scanner in = new Scanner(new File("connectionDataBase.txt")) ;
        String[] str = null ;
        while (in.hasNext()) {
            str = in.nextLine().split("#") ;
        }
        Link = str[0] ;
        userName = str[1] ;
        Password = str[2] ;
in.close();
        new DataBase(Link , userName , Password );
        DataBase.connection.prepareStatement("set GLOBAL max_allowed_packet=9999999999").executeUpdate();
        launch();
    }
    private static void Build_TEMP_PATH () {
        try {
            Scanner in = new Scanner(new File("TEMP_PATH.txt")) ;
            TEMP_PATH =  in.nextLine();
            System.out.println(TEMP_PATH);

            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}