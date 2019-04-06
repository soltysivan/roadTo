package org.euro.controller;


import org.apache.tomcat.util.codec.binary.Base64;
import org.euro.dao.entity.FileDB;
import org.euro.dao.entity.Message;
import org.euro.dao.entity.Tel;
import org.euro.service.FileDBService;
import org.euro.service.MessageService;
import org.euro.service.TelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    public TelService telService;

    @Autowired
    public FileDBService fileDBService;

    @Autowired
    public MessageService messageService;

    @GetMapping("/")
    public String helloPage(Model model){
        List<Tel> telephone = telService.findAll();
        if (telephone != null){
            model.addAttribute("telephone", telephone);
        }
        return "index";
    }

    @GetMapping("/welcome")
    public String welcomePage(){
        return "createTrip";
    }


}
