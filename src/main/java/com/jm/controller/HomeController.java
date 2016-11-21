package com.jm.controller;

import com.jm.domain.Meaning;
import com.jm.domain.Word;
import com.jm.domain.WordRepository;
import com.jm.service.WordNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * com.jm.controller.HomeController Class will handle all communication with WordNet.
 * Created by James Millner on 01/11/2016 at 14:56.
 */
@Controller
public class HomeController {

    @Autowired
    private WordNetService wordNetService;

    @Autowired
    private WordRepository wordRepository;

    @RequestMapping(value = "/")
    public String home(Model model){
        List<Word> words = wordRepository.findAll();

        model.addAttribute("words", words);
        return "home";
    }

    @RequestMapping(value = "/word/{id}")
    public String getWord(@PathVariable("id") Long id) {
        Word found = wordRepository.findOne(id);
        System.out.println("***********");
        System.out.println(found.getId());
        System.out.println(found.getSearch());
        System.out.println("Meanings: ");
        for(Meaning m : found.getMeanings()) {
            System.out.println(m.getMeaning());
        }
        System.out.println("************");
        return "redirect:/";
    }

    @RequestMapping(value = "/wordnet/{word}/{type}")
    public String tryWordNet(@PathVariable("word") String word,
                             @PathVariable("type") String type) {

        List<Word> words = wordRepository.findAllBySearch(word);

        if(words.size() == 0) {
            System.out.println("We do have this word!!!");
        } else {
            wordNetService.go(type, word);
        }

        return "redirect:/";

    }

}
