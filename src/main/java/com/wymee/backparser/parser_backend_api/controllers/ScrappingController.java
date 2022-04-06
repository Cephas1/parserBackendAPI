package com.wymee.backparser.parser_backend_api.controllers;

import com.wymee.backparser.parser_backend_api.data.JobsDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ScrappingController {

    @Autowired
    private static JobsDAO jobsDAO;

    @GetMapping("/scrapp")
    //@EventListener(ApplicationReadyEvent.class)
    public static String scrapping(/*@RequestHeader(value = "User-Agent") String userAgent*/ @RequestParam(name = "professions") String data) throws IOException { //int n = 1;
        /*do {
            n++;

            System.out.println("Scrapping working now " + LocalTime.now());

            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }while(n <= 10);*/
        // Indeed API call
        /*String indeed_api_key = "442a96f2006bf33f73a4a11a8c543e5da55212abb865f57f6bf123a70df3a4f5";
        String query = "DEVELOPPEUR WEB";
        int total_results = 1000;

        String api_request_url;
        int results_left = total_results;
        int start = 0, limit = Math.min(results_left, 25); // Indeed seems to have a cap of 25 results per request
        Client client = ClientBuilder.newClient();
        System.out.println("results_left = " + results_left);

        while(results_left > 0) {
            // Add any other API requst params to the string below
            api_request_url = "http://api.indeed.com/ads/apisearch?publisher=" + indeed_api_key + "&q=" + query.replaceAll("\\s", "+") + "&start=" + start + "&limit=" + limit +"&format=json&v=2";

            WebTarget resource = client.register(JsonProcessingFeature.class).target(api_request_url);

            Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
            Response response = request.get();

            //mon ajout
            System.out.println("response status = " + response.getStatus() + "\n ---------");
            //end of mon ajout

            if (response.getStatus() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode resultsArr = mapper.readTree(response.readEntity(String.class)).get("results");

                ArrayList<String> job_urls = new ArrayList<>();
                //mon ajout
                for (String s : job_urls) {
                    int i = 1;
                    System.out.println("Job"+ i +" = " + s);
                    System.out.println("---------------------");
                    i++;
                }
                //fin de mon ajout

                for (JsonNode node : resultsArr) {
                    job_urls.add(node.get("url").textValue());
                }

                ArrayList<String> job_descriptions = new ArrayList<>();

                // Scrape full job description from the job's html page
                for (String url : job_urls) {
                    Document doc = Jsoup.connect(url).get();
                    Elements job_elements = doc.select("#job_summary");
                    String job_plaintext = job_elements.text();
                    job_descriptions.add(job_plaintext);
                }

                // Write job descriptions to a txt file
                try {
                    String desc_dir_path = "C:\\Users\\Admin KBIM\\Documents\\SpringBootProjects\\parser_backend_api\\src\\main\\resources\\filestest";

                    String file_name = query.trim().replaceAll("\\s+", "_").toLowerCase();

                    Path job_descr_file = Paths.get(desc_dir_path + file_name + ".txt");
                    if (Files.exists(job_descr_file)) {
                        Files.write(job_descr_file, job_descriptions, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
                    } else {
                        Files.write(job_descr_file, job_descriptions, Charset.forName("UTF-8"));
                    }
                } catch (IOException e) {
                    System.out.println("File failed to write");
                    e.printStackTrace();
                }
            } else {
                System.out.println("HTTP request failed, returning status code " + response.getStatus());
            }

            start += limit;
            results_left -= limit;
            limit = Math.min(results_left, 25);
        }*/

       /* //careerjet api testing
        Client c = new Client("fr_FR");
        //System.out.println("request = " + request.toString());

        Map<String, String> args = new HashMap<String, String>();
        args.put("keywords", "java");
        args.put("location", "France");
        args.put("affid", "58a77f477d4a6b5bfa2c42ec5211e3e9");
        args.put("user_ip",    "2c0f:ef58:51f:5600:df7:8f0:895a:bf21");
        args.put("user_agent", userAgent);
        args.put("url", "https://wymee.fr");

        System.out.println("c = " + c.search(args));
        System.out.println("\n userAgent = " + userAgent);

        JSONObject results = new JSONObject(c.search(args));

        // A list of jobs is returned
        if (results.get("type").equals("JOBS")) {
            JSONArray jobs = (JSONArray) results.get("jobs");
            System.out.println("Number of results:" + results.get("hits"));
            int index = 0;
            while( index < jobs.length()) {
                JSONObject job = (JSONObject) jobs.get(index);
                System.out.println("URL         :" + job.get("url"));
                System.out.println("TITLE       :" + job.get("title"));
                System.out.println("COMPANY     :" + job.get("company"));
                System.out.println("SALARY      :" + job.get("salary"));
                System.out.println("DATE        :" + job.get("date"));
                System.out.println("DESCRIPTION :" + job.get("description"));
                System.out.println("SITE        :" + job.get("site"));
                System.out.println("LOCATIONS   :" + job.get("locations"));
                index++;
            }
        }

        // The location was amiguous. Suggestions are returned.
        // Add the location_id to the query to resolve the ambiguity.
        if (results.get("type").equals("LOCATIONS")) {
            System.out.println("Narrow down your location ");
            System.out.println("Please specify a location");
            JSONArray solvelocations = (JSONArray) results.get("solveLocations");
            int index = 0;
            while(index < solvelocations.length()) {
                JSONObject location = (JSONObject) solvelocations.get(index);
                System.out.println("NAME        :" + location.get("name"));
                System.out.println("LOCATION ID :" + location.get("location_id"));
                index++;
            }
        }

        // An error occured. An error message is returned.
        if (results.get("type").equals("ERROR")) {
            System.out.println("An error occurred whilst processing the search query");
            System.out.println("Error message    :" + results.get("ERROR"));
        }

        return results.toString();*/
       JSONObject response = new JSONObject();
        JSONArray allData = new JSONArray(data);
        for (int i = 0; i < allData.length(); i++) {
            JSONObject obj = (JSONObject) allData.get(i);
            response.append(String.valueOf(i), doScrapping(obj.getString("job"), obj.getString("location")));
        }

       return response.toString();
    }

    public static JSONObject doScrapping(String job, String location) throws IOException {
        final String url = "https://www.indeed.com/jobs?q="+job+"&l="+location;
        final String urlLinkup = "https://www.linkup.com/search/results/"+ job +"-jobs-in-"+ location;
        Document document = null;
        Document document2 = null;

        JSONObject indeedArray = new JSONObject();
        JSONObject linkUpArray = new JSONObject();
        JSONObject monArray = new JSONObject();

        int i = 0;
        try {
            document = Jsoup.connect(url.replace(" ", "%2C%20")).get();

            for(Element subDoc : document.select("div.job_seen_beacon")) {

                String jobTitle = subDoc.select("h2.jobTitle").text();
                String companyName = subDoc.select("span.companyName").text();
                String companyLocation = subDoc.select("div.companyLocation").text();
                String companyLink = subDoc.select("a.turnstileLink.companyOverviewLink").text();
                String jobDescription = subDoc.select("div.job-snippet").text();
                String jobPostedAt = subDoc.select("span.date").text();

                /*System.out.println("-------------------" +
                        "\n job title = "+jobTitle+"" +
                        "\n company location = "+companyLocation+"" +
                        "\n company name = "+companyName+"" +
                        "\n company link = "+companyLink+"" +
                        "\n job posted at = "+jobPostedAt+"" +
                        "\n job description = "+jobDescription+"" );*/

                JSONObject currentObj = new JSONObject();
                currentObj.put("job-title", jobTitle);
                currentObj.put("job-description", jobDescription);
                currentObj.put("job-posted-at", jobPostedAt);
                currentObj.put("company-name", companyName);
                currentObj.put("company-location", companyLocation);
                currentObj.put("company-link", companyLink);
                currentObj.put("source", "Indeed");

                monArray.append(String.valueOf(i), currentObj);
                //System.out.println("currentObj " + i + " = " + currentObj.toString()+"\n");
                i++;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        i=0;
        try{
            document2 = Jsoup.connect(urlLinkup.replace(" ", "-")).get();
            for ( Element element : document2.select("div.row.job-listing")) {

                String jobTitle = element.select("a.organic-link.search-result-link").text();
                String companyName = element.select("p.f-s-14 span.semi-bold").text();
                String jobLocation = element.select("span.vertical-bar.semi-bold").text();
                String jobPostedAt = element.select("div.f-s-14 span.semi-bold").text();
                String jobDescription = element.select("div.col.s12 p").text();

                JSONObject current = new JSONObject();
                current.put("job-title", jobTitle);
                current.put("job-description", jobDescription);
                current.put("job-posted-at", jobPostedAt);
                current.put("company-name", companyName);
                current.put("job-location", jobLocation);
                current.put("source", "Linkup");

                monArray.append(String.valueOf(i), current);
                System.out.println("current "+i+" = " + current.toString()+"\n");
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        JSONObject back = new JSONObject();
        back.append("data", monArray);

        return back;
    }
}
