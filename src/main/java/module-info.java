module com.data_analytics {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.data_analytics to javafx.fxml;
    opens com.data_analytics.controllers to javafx.fxml;
    opens com.data_analytics.utils to javafx.fxml;
    exports com.data_analytics;
    exports com.data_analytics.controllers;
    exports com.data_analytics.utils;
}