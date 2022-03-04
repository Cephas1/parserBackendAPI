package com.wymee.backparser.parser_backend_api.controllers;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test/json")
    public static String testing(){
        JSONObject object = new JSONObject();
        object.put("name", "Pierre");
        object.put("name1", "Dan");
        object.put("name2", "Brel");

        return object.toString();
    }

}
