module com.logistic.ramalogistic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;
    requires java.desktop;


    opens com.logistic.ramalogistic to javafx.fxml;
    exports com.logistic.ramalogistic;

    opens com.logistic.Report to javafx.fxml;
    exports com.logistic.Report;

    opens com.logistic.Client to javafx.fxml;
    exports com.logistic.Client;

    opens com.logistic.PaymentRequest to javafx.fxml;
    exports com.logistic.PaymentRequest;
    opens com.logistic.Setting to javafx.fxml;
    exports com.logistic.Setting;

    opens com.logistic.DataBase to javafx.fxml;
    exports com.logistic.DataBase;
    opens com.logistic.Home to javafx.fxml;
    exports com.logistic.Home;

    opens com.logistic.Message to javafx.fxml;
    exports com.logistic.Message;

    opens com.logistic.FileTracker to javafx.fxml;
    exports com.logistic.FileTracker;

    opens com.logistic.Backup to javafx.fxml;
    exports com.logistic.Backup;

    opens com.logistic.CreditNote to javafx.fxml;
    exports com.logistic.CreditNote;

    opens com.logistic.ReceiptVoucher to javafx.fxml;
    exports com.logistic.ReceiptVoucher;

    opens com.logistic.taxInvoice to javafx.fxml;
    exports com.logistic.taxInvoice;

    opens com.logistic.Supplier to javafx.fxml;
    exports com.logistic.Supplier;
}