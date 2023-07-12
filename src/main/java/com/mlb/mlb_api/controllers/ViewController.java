package com.mlb.mlb_api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/players")
    public String players(){
        return "index";
    }

    @GetMapping("/teams")
    public String teams(){
        return "html/team";
    }

    @GetMapping("/events")
    public String events(){
        return "html/event";
    }
}
