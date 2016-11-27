package com.jm.controller;

import com.jm.domain.Meaning;
import com.jm.domain.Word;
import com.jm.domain.WordRepository;
import com.jm.service.OpenNPL;
import com.jm.service.WordNetService;
import com.jm.service.WordsAPI;
import net.didion.jwnl.data.Exc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * com.jm.controller.HomeController Class will handle all communication with WordNet.
 * Created by James Millner on 01/11/2016 at 14:56.
 */
@Controller
public class HomeController {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private WordNetService wordNetService;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordsAPI wordsAPI;

    @Autowired
    private OpenNPL openNPL;

    @RequestMapping(value = "/")
    public String home(Model model){
        model.addAttribute("words", wordRepository.findAll());
        return "home";
    }

    @RequestMapping(value = "/word-definition/{id}")
    public String getWord(@PathVariable("id") Long id,
                          Model model) {
        Word found = wordRepository.findOne(id);
        logger.info("***********");
        logger.info(found.getId());
        logger.info(found.getSearch());
        logger.info("Meanings: ");
        for(Meaning m : found.getMeanings()) {
            logger.info(m.getMeaning());
        }
        logger.info("************");
        model.addAttribute("word", found);
        return "word";
    }

    @RequestMapping(value = "/wordnet", method = RequestMethod.POST)
    public String getWordFromWordnet(@RequestParam("word") String word,
                                     @RequestParam("type") String type) {

        List<Word> words = wordRepository.findAllBySearch(word);

        if(words.size() >= 1) {
            logger.info("We already have this word.");
        } else {
            try {
                wordNetService.go(type, word);
            } catch (Exception e) {
                logger.info("HomeController - Can't find that word on the word net.");
            }
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/words-api")
    public String testWordsApi() throws IOException {
        wordsAPI.go("exam");
        return "redirect:/";
    }

    @RequestMapping(value = "/open-nlp")
    public String testOpenNLP() throws Exception {
        List<Word> words = wordRepository.findAll();
        for(Word word : words) {
            for(Meaning meaning : word.getMeanings()) {
                openNPL.go(meaning.getMeaning());
            }
        }
        return "redirect:/";
    }
}
