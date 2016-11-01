package com.jm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * com.jm.controller.HomeController Class will...
 * Created by James Millner on 01/11/2016 at 14:56.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home(Model model){
        return "home";
    }
}
