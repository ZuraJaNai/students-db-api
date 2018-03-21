package no;

import no.itera.util.ApplicationExitListener;
import no.itera.util.ApplicationReadyListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
//        app.addListeners(new ApplicationReadyListener());
//        app.addListeners(new ApplicationExitListener());
        app.run(args);
    }

}
