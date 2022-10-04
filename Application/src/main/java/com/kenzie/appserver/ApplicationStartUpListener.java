package com.kenzie.appserver;

import com.kenzie.appserver.service.ItemService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartUpListener {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Perform any application start-up tasks
        ItemService itemService = event.getApplicationContext()
                .getBean(ItemService.class);
    }
}
