module org.example.sep_projecta {
    requires javafx.fxml;
    requires com.gluonhq.charm.glisten;
    requires bcrypt;
    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens org.example.sep_projecta to org.hibernate.orm.core, javafx.fxml;
    exports org.example.sep_projecta;
}