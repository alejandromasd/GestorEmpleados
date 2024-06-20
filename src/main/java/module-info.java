module org.example.proyectoada {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires com.opencsv;
    opens org.example.gestorEmpleados to javafx.fxml;
    opens model to org.hibernate.orm.core, javafx.base;
    exports org.example.gestorEmpleados;
}