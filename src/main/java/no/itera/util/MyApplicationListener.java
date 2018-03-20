package no.itera.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.io.IOException;

public class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationEvent) {
            openBrowser();
    }

    public static void openBrowser() {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        String url = "http://localhost:8080/views/person";
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
                //logger
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
