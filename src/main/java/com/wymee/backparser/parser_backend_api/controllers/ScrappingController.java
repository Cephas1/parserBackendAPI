package com.wymee.backparser.parser_backend_api.controllers;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ScrappingController {

    //@GetMapping("/scrapp")
    @EventListener(ApplicationReadyEvent.class)
    public static void scrapping(){
        int n = 1;
        do {
            n++;
            System.out.println("This app started now");
        }while(n < 10);
    }
}
