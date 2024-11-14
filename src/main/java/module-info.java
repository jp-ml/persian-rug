module com.persianrug {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.persianrug to javafx.fxml;
    exports com.persianrug;

    opens com.persianrug.engine to javafx.fxml;
    exports com.persianrug.engine;

    opens com.persianrug.entity to javafx.fxml;
    exports com.persianrug.entity;

    opens com.persianrug.utils to javafx.fxml;
    exports com.persianrug.utils;

    opens images;
}