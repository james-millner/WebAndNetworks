package com.jm.controller;

import com.jm.service.WordNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.jm.controller.HomeController Class will...
 * Created by James Millner on 01/11/2016 at 14:56.
 */
@Controller
public class HomeController {

    @Autowired
    private WordNetService wordNetService;

    @RequestMapping(value = "/")
    public String home(Model model){
        return "home";
    }

    @RequestMapping(value = "/wordnet/{word}/{type}")
    public String tryWordNet(@PathVariable("word") String word,
                             @PathVariable("type") String type) {
        wordNetService.go(type, word);
        return "redirect:/";
    }

}
