package com.logistic.Alerts;

import javafx.scene.control.Alert;

public class Alerts {
    private static Alert alert ;

    public static void  Success() {
        alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("DONE");
        alert.setContentText("operation accomplished successfully");
        alert.showAndWait();
    }


    public static void  Error() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText("operation failed");
        alert.showAndWait();
    }
    public static void  ErrorConnectionDataBase() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText("Failed to connect to the database");
        alert.showAndWait();
    }
    public static void  FileTrackerNOTExist() {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText("This File Not Exist in The System.");
        alert.showAndWait();
    }
    public static void  FiledsNotEmptyOrSqlError() {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText("Check that all Fields are not empty.");
        alert.showAndWait();
    }
    public static void  FileTrackerAlreadyExist() {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText("This File Already Exist In The System.");
        alert.showAndWait();
    }
    public static void WarningAlert(String str ) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText(str);
        alert.showAndWait();
    }
    public static void ErrorAlert(String str ) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(str);
        alert.showAndWait();
    }
}
