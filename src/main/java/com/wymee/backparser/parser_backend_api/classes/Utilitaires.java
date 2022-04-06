package com.wymee.backparser.parser_backend_api.classes;

import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class Utilitaires {

    public static JSONObject makeMeta(String state){

        JSONObject metaHeader = new JSONObject();
        JSONObject meta = new JSONObject();

        metaHeader.append("statusCode", "200");
        metaHeader.append("state", state);

        return meta.append("meta", metaHeader);
    }


}
