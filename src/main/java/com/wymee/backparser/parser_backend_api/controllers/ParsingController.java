package com.wymee.backparser.parser_backend_api.controllers;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@RestController
@RequestMapping("/api/parsing")
public class ParsingController {

    @PostMapping(value = "/parse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public static String parse(@RequestParam(name = "resume")MultipartFile file) throws IOException, InterruptedException {
        //TODO

        /*if (file.isEmpty()){
            return "Bad credentials";
        }else {
            //String url = "https://harveywymee/prototype/parse";
            //Mettre ici tout le code de construucgtion de la requête
            String[] name = Objects.requireNonNull(file.getOriginalFilename()).split(".");

            if(name[name.length-1].equalsIgnoreCase("pdf") || name[name.length-1].equalsIgnoreCase("docx") || name[name.length-1].equalsIgnoreCase("doc²")){
                JSONObject parsingResults = getParsingData("http://localhost:8090/api/parsing/testpost", file);

                String candidateName = getCandidateNameFromParsingData(parsingResults);
                String candidateProfession = getCandidateProfessionFromParsingData(parsingResults);
                String candidateLocation = getCandidateLocationFromParsingData(parsingResults);
                //TODO test de reception correct des data de parsing
                JSONObject response;
                //assert parsingResults != null;
                if (parsingResults != null) {
                    response = Utilitaires.makeMeta("Error");
                } else {
                    response = Utilitaires.makeMeta("Success");
                }


                response.append("parsed_resume", parsingResults);
                response.append("matched_jobs", getMatchingData(candidateProfession, candidateLocation));

                return response.toString();
            }else{
                return "Bad credential \n \t * Unsupported file extension";
            }

        }*/
       return getParsingData("http://localhost:8090/api/parsing/fileUploadTest", file);

    }

    @PostMapping(value = "/fileUploadTest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public static String test(@RequestParam(name = "file")MultipartFile file){
        System.out.println("On est entré dans la methode ciblée !");
        if(file.isEmpty()){
            return "No file got";
        }else{
            return "file got successfully";
        }
    }

    private static String getCandidateLocationFromParsingData(JSONObject parsingResults) {
        return parsingResults.getJSONObject("location").getString("country");
    }

    private static String getCandidateProfessionFromParsingData(JSONObject parsingResults) {
        return parsingResults.getString("profession");
    }

    private static String getCandidateNameFromParsingData(JSONObject parsingResults) {
        return parsingResults.getJSONObject("name").getString("raw").replace(" ", "_");
    }

    private static String getMatchingData(String profession, String location) throws IOException {

        return ScrappingController.doScrapping(profession.replace(" ", "%2C%20"), location).toString();
    }

    public static JSONObject getParsingData(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            HttpGet request = new HttpGet(url);

            request.addHeader("content-type", "application/json");
            request.addHeader("Content-Type", "multipart/form-data");


            CloseableHttpResponse response = httpClient.execute(request);

            try{
                /*System.out.println("\n response.getProtocolVersion() = " + response.getProtocolVersion());
                System.out.println("\n response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
                System.out.println("\n response.getStatusLine().getReasonPhrase() = " + response.getStatusLine().getReasonPhrase());
                System.out.println("\n response.getStatusLine().toString() = " + response.getStatusLine().toString());*/

                HttpEntity entity = response.getEntity();
                if (entity != null){
                    return new JSONObject(EntityUtils.toString(entity));
                    //System.out.println(" content "+EntityUtils.toString(entity));
                }
            }finally {
                response.close();
            }
        }finally {
            httpClient.close();
        }
        return null;
    }

    public static String getParsingData(String url, MultipartFile file) throws IOException, InterruptedException {
        /*CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            HttpPost request = new HttpPost(url);

            request.addHeader("content-type", "application/json");
            request.addHeader("Content-Type", "multipart/form-data");
            MultiValueMap<String, Object> fileObject = new LinkedMultiValueMap<>();
            fileObject.add("resume", file);

            //request.setEntity(new FileEntity(new ));
            //request.setEntity(new FileEntity());



            CloseableHttpResponse response = httpClient.execute(request);

            try{
                System.out.println("\n response.getProtocolVersion() = " + response.getProtocolVersion());
                System.out.println("\n response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
                System.out.println("\n response.getStatusLine().getReasonPhrase() = " + response.getStatusLine().getReasonPhrase());
                System.out.println("\n response.getStatusLine().toString() = " + response.getStatusLine().toString());

                HttpEntity entity = response.getEntity();
                if (entity != null){
                    return new JSONArray(EntityUtils.toString(entity));
                    //System.out.println(" content "+EntityUtils.toString(entity));
                }
            }finally {
                response.close();
            }
        }finally {
            httpClient.close();
        }
        return null;*/
        /*HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "multipart/form-data")
                .method("POST",  HttpRequest.BodyPublishers.ofInputStream())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());*/

        /*HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                                  .POST(HttpRequest.BodyPublishers.ofFile(Paths.get(file.getResource().getURI())))
                                  .uri(URI.create(url))
                                  .header("Content-type", "application/json")
                                  .header("Content-Type", "multipart/form-data")
                                  .build();
        HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());*/
        Map<Object, Object> data = new LinkedHashMap<>();
        data.put("file", file);
        String boundary = new BigInteger(256, new Random()).toString();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(ofMimeMultipartData(data, boundary))
                .uri(URI.create(url))
                .header("Content-Type", "multipart/byteranges")
                .build();
        System.out.println("httpRequest.toString() = " + httpRequest.headers());
        System.out.println("httpRequest.bodyPublisher().toString() = " + httpRequest.bodyPublisher().toString());
        HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());

        //return Utilitaires.makeMeta("Success");
        return httpResponse.toString();
    }

    private static HttpRequest.BodyPublisher ofMimeMultipartData(Map<Object, Object> data, String boundary) throws IOException {
        List<byte[]> byteArrays = new ArrayList<>();

        // Separator with boundary
        byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(StandardCharsets.UTF_8);

        // Iterating over data parts
        for (Map.Entry<Object, Object> entry : data.entrySet()) {

            // Opening boundary
            byteArrays.add(separator);

            // If value is type of Path (file) append content type with file name and file binaries, otherwise simply append key=value
            if (entry.getValue() instanceof Path) {
                var path = (Path) entry.getValue();
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; file=\"" + path.getFileName()
                        + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n")
                        .getBytes(StandardCharsets.UTF_8));
            }
        }

        // Closing boundary
        byteArrays.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));

        // Serializing as byte array
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }


}
