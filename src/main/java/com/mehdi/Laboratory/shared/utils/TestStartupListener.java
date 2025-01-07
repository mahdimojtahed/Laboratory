package com.mehdi.Laboratory.shared.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Profile("test")
public class TestStartupListener {

    @Autowired
    private DataCreator dataCreator;

    @EventListener
    public void onApplicationStartup(ApplicationReadyEvent event) {
        this.logApplicationStartup(event);
        dataCreator.createTestData();
    }

    private void logApplicationStartup(ApplicationReadyEvent event) {
        System.out.println("Application started");
        System.out.println("Time: " + new Date(event.getTimestamp()));
        System.out.println("TimeTaken: " + event.getTimeTaken());
    }


}
