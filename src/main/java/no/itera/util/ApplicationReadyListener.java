package no.itera.util;

import no.itera.controller.rest.PersonController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.io.IOException;

public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Value( "${userProperties.viewLink}" )
    private static String serverLink;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationEvent) {
            openBrowser();
    }

    public static void openBrowser() {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        String url = serverLink;
        try {
            if (os.indexOf("win") >= 0){
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            }
            else if(os.indexOf("mac") >= 0){
                rt.exec("open " + url);
            }
            else if(os.indexOf("nix") >=0 || os.indexOf("nux") >=0){
                rt.exec("xdg-open " + url);
            }
            else {
               logger.debug("Cant define operating system!");
            }
        } catch (IOException e) {
            logger.error("Can't start a browser",e);
            e.printStackTrace();
        }
    }
}
