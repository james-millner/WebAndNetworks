package com.jm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * com.jm.service.WordsAPI Class will...
 * Created by James Millner on 22/11/2016 at 10:16.
 */
@Service
public class WordsAPI {

    @Value("${wordapi.key}")
    private String apiKey;

    private Log logger = LogFactory.getLog(this.getClass());

    public void go(String word) {
        String url = "https://wordsapiv1.p.mashape.com/words/" + word + "/";

        try {
            HttpResponse<com.mashape.unirest.http.JsonNode> response = Unirest.get("https://wordsapiv1.p.mashape.com/words/einstein/")
                    .header("X-Mashape-Key", apiKey)
                    .header("Accept", MediaType.APPLICATION_JSON_VALUE).asJson();

            System.out.println(response.getBody().toString());
        } catch (Exception e){
            logger.fatal(e);
        }

    }

}
