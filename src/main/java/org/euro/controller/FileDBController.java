package org.euro.controller;


import org.euro.service.FileDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FileDBController {

    @Autowired
    public FileDBService fileDBService;

    @GetMapping("/avatar/{id}")
    public String  getAvatar(@PathVariable Long id){

        return "profile";
    }
}
