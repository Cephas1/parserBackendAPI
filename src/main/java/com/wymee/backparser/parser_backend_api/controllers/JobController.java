package com.wymee.backparser.parser_backend_api.controllers;

import com.sun.istack.NotNull;
import com.wymee.backparser.parser_backend_api.classes.JobObject;
import com.wymee.backparser.parser_backend_api.classes.Utilitaires;
import com.wymee.backparser.parser_backend_api.model.Job;
import com.wymee.backparser.parser_backend_api.repository.JobRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/filtered")
    public ResponseEntity<List<Job>> getByReff(@RequestParam(name = "tosearch") String name){
        return new ResponseEntity<>(jobRepository.findAllByReff(name), HttpStatus.OK);
    }

    @GetMapping
    public List<Job> findAllJobs() {
        return (List<Job>) jobRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable(value = "id") long id) {
        Optional<Job> job = jobRepository.findById(id);

        if (job.isPresent()) {
            return ResponseEntity.ok().body(job.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public Job saveJob(@Validated @RequestBody Job job) {
        return jobRepository.save(job);
    }

    @PostMapping("/addAll")
    public String saveAll(@RequestParam(name = "data") @NotNull String list) throws org.json.JSONException{

        JSONArray array = new JSONArray(list);

        for (int i = 0; i <array.length() ; i++) {
            JSONObject obj = (JSONObject) array.get(i);
            jobRepository.save(Utilitaires.jobifyJson(obj));

        }
        return "Jobs saved successfully";
    }

    public Job saveMyJob(Job object){
        return jobRepository.save(object);
    }

}
