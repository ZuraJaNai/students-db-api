package no;

import no.itera.util.SwaggerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotatedClassType;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.sql.*;

//java -cp C:\Users\mykola.kovtun\.m2\repository\org\hsqldb\hsqldb\2.4.0\hsqldb-2.4.0.jar org.hsqldb.server.Server --database.0 file:students --dbname.0 students

@SpringBootApplication
public class Application {

    private static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
