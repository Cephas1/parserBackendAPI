package com.wymee.backparser.parser_backend_api.controllers;

import com.wymee.backparser.parser_backend_api.model.Job;
import com.wymee.backparser.parser_backend_api.repository.JobRepository;
import org.apache.catalina.connector.Connector;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/matching/")
@RestController
public class MatchingController {

    @Autowired
    private static JobRepository jobRepository;

    @GetMapping("/match")
    public static String match(@RequestParam(name = "data") String data) throws IOException, org.json.JSONException {

        String scrapped = "";

        if(!data.isEmpty()) {

            try {
                scrapped = ScrappingController.scrapping(data);
            }catch (org.json.JSONException e){
                e.printStackTrace();
            }
//            scrapped = ScrappingController.scrapping(data);

            if (scrapped.isEmpty() || scrapped.length() <= 7) {
                //TODO get database infos here
                //scrapped = getListOfJobsByData(data).toString();
            }

            return scrapped;
        }else {
            //TODO response if error there
            System.out.println("Matching method got empty data parameters");
        }
        return scrapped;
    }

    private static JSONObject getListOfJobsByData(String data) throws JSONException {
        JSONArray array = new JSONArray(data);
        JSONObject retour = new JSONObject();
        List<Job> value;
        value = (List<Job>) jobRepository.findAll();
        int iterator = 0;

        for (int j = 0; j < array.length(); j++) {
            for (int i = 0; i < value.size() ; i++) {
                if(value.get(i).getTitle().contains(array.getJSONObject(j).getString("job"))
                        || value.get(i).getDescription().contains(array.getJSONObject(j).getString("job"))){
                    try {
                        retour.append(String.valueOf(iterator), JSONifyJob(value.get(i)));
                        iterator++;
                    }catch (org.json.JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }

        return retour;
    }

    private static Object JSONifyJob(Job job) throws org.json.JSONException {
        JSONObject currentObject = new JSONObject();

        currentObject.put("job-title", job.getTitle());
        currentObject.put("job-description", job.getDescription());
        currentObject.put("job-posted-at", job.getJobPostedAt());
        currentObject.put("company-name", job.getCompany());
        currentObject.put("company-webSite", job.getCompanyWebSite());
        currentObject.put("job-location", job.getLocation());
        currentObject.put("job-contrat", job.getJobContrat());
        currentObject.put("begin-date", job.getBeginDate());
        currentObject.put("salary-range", job.getSalaryRange());
        currentObject.put("source", job.getJobContrat());

        return currentObject;
    }

    public static String matches(String candidat) throws org.json.JSONException{
        JSONObject candidate = new JSONObject(candidat);

        // TODO getting SQLData and matching it to return it has a json Object to String!

        return candidate.toString();
    }

    ///////Les m??thodes corses rajout??es ici devraient appartenir ?? un objet controleur duquel les autres h??riteraient.

    //Cette methode ci permet au serveur d'autoriser des requ??tes dont les url contiennent des caract??res sp??ciaux
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(){
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setProperty("relaxedQueryChars", "|{}[]");
            }
        });
        return factory;
    }


    //Cette m??thode permet de remedier aux probl??mes d'autorisations CORS
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/match").allowedOrigins("http://localhost:8080");
            }
        };
    }
} 
