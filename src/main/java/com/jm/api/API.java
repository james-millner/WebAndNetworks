package com.jm.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jm.domain.Word;
import com.jm.domain.WordRepository;
import com.jm.service.WordsAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * com.jm.api.API Class will handle retrieving words from this wordnet.
 *
 * Created by James Millner on 26/11/2016 at 09:55.
 */
@RestController
public class API
{

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordsAPI wordsAPI;

    @RequestMapping(value = "/word/{word}", method = RequestMethod.GET)
    @ResponseBody
    public String getWord(@PathVariable("word") String word) {
        try {
            Word found = wordRepository.findBySearch(word);
            return getJSON(found);
        } catch (Exception e){
            return wordsAPI.go(word);
        }
    }

    private String getJSON(Word word) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(word);
    }
}
