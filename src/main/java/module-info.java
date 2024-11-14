module com.persianrug {
    requires javafx.controls;
    requires javafx.fxml;

    // Main package
    opens com.persianrug to javafx.fxml;
    exports com.persianrug;

    // Engine package
    opens com.persianrug.engine to javafx.fxml;
    exports com.persianrug.engine;

    // Entity package
    opens com.persianrug.entity to javafx.fxml;
    exports com.persianrug.entity;

    // Utils package
    opens com.persianrug.utils to javafx.fxml;
    exports com.persianrug.utils;

    // Image resources
    opens images;
}