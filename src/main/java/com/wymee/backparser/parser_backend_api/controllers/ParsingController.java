package com.wymee.backparser.parser_backend_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ParsingController {

    @GetMapping("/parse")
    public static String parse(){
        //need to call a web service or an library to parse a file got on PDF
        //and I should format it to personal format
        //to return it has a json or a Map<String, String>

        Map<String, String> map = new HashMap<>();
        map.put("name","Pierre");
        map.put("otherName", "Patrick");
        return map.toString();
    }
}
