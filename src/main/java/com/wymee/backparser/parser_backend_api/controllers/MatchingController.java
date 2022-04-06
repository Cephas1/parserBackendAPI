package com.wymee.backparser.parser_backend_api.controllers;

import com.wymee.backparser.parser_backend_api.classes.JobObject;
import com.wymee.backparser.parser_backend_api.data.JobsDAO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/matching/")
@RestController
public class MatchingController {

    @Autowired
    private static JobsDAO jobsDAO;

    @GetMapping("/match")
    public static String match(@RequestParam(name = "professions") String data) throws IOException {

        //Créer ici la recupération des données à macther

        String scrapped = ScrappingController.scrapping(data);
        if(scrapped.isEmpty()){
            Model model = null;
           scrapped = model.addAttribute("list jobs", jobsDAO.findAll()).toString();
        }

        //TODO Getting MySQL data for matching

        return scrapped;
    }

    public static String matches(String candidat){
        JSONObject candidate = new JSONObject(candidat);

        // TODO getting SQLData and matching it to return it has a json Object to String!

        return candidate.toString();
    }
} 
