package no;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotatedClassType;
import org.hibernate.cfg.Configuration;
import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.sql.*;

//java -cp C:\Users\mykola.kovtun\.m2\repository\org\hsqldb\hsqldb\2.4.0\hsqldb-2.4.0.jar org.hsqldb.server.Server --database.0 file:students --dbname.0 students

@SpringBootApplication
public class Application {

    private static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws SQLException, IOException, ServerAcl.AclFormatException {
//        HsqlProperties p = new HsqlProperties();
//        p.setProperty("server.database.0","file:\\opt\\db\\students");
//        p.setProperty("server.dbname.0","students");
//        Server server = new Server();
//        server.setProperties(p);
//        server.setLogWriter(null); // can use custom writer
//        server.setErrWriter(null); // can use custom writer
//        server.start();
//
//        try {
//            Class.forName("org.hsqldb.jdbc.JDBCDriver" );
//        } catch (Exception e) {
//            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
//            e.printStackTrace();
//            return;
//        }
//
        Connection c = DriverManager.getConnection("jdbc:hsqldb:file:C:/Users/mykola.kovtun/IdeaProjects/studentsdb/opt/db/students;shutdown=true;hsqldb.write_delay=false", "SA", "");

        SpringApplication.run(Application.class);
    }

}
