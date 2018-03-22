package no.itera.util;

import org.springframework.context.ApplicationListener;

public class ApplicationExitListener implements ApplicationListener<CustomExitEvent> {
    @Override
    public void onApplicationEvent(CustomExitEvent customExitEvent) {
        System.exit(0);
    }
}
