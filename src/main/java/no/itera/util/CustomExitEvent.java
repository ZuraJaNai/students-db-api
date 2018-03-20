package no.itera.util;

import org.springframework.context.ApplicationEvent;

public class CustomExitEvent extends ApplicationEvent {
    public CustomExitEvent(Object source) {
        super(source);
    }
}
