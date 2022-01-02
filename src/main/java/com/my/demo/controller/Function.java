package com.my.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/function")
public class Function {


    @RequestMapping("/index1")
    private String index1(){
        return "index1";
    }

    @RequestMapping("/principle")
    private String principle(){
        return "principle";
    }

    @RequestMapping("/demonstration")
    private String demonstration(){
        
        return "demonstration";
    }

    @RequestMapping("/code")
    private String code(){
        return "code";
    }

    @RequestMapping("/defense")
    private String defense(){
        return "defense";
    }
}
