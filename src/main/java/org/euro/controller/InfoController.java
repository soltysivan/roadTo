package org.euro.controller;

import org.euro.dao.entity.Info;
import org.euro.dao.entity.Message;
import org.euro.dao.entity.Tel;
import org.euro.dao.entity.User;
import org.euro.service.InfoService;
import org.euro.service.TelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InfoController {

    @Autowired
    public TelService telService;

    @Autowired
    public InfoService infoService;

    @GetMapping("/info")
    public String getInfoPage(Model model){
        List<Info> infos = infoService.findAll();
        if (infos!=null) {
            model.addAttribute("infos", infos);
        }
        List<Tel> telephone = telService.findAll();
        if (telephone != null){
            model.addAttribute("telephone", telephone);
        }
        return "info";
    }
    @PostMapping("/telephone/create")
    public String createTelephone(@RequestParam String text){
        Tel tel = new Tel();
        tel.setText(text);
        telService.save(tel);
        return "redirect:/info";
    }
    @PostMapping("/telephone/d")
    public String deleteTelephone(){
        telService.deleteAll();
        return "redirect:/info";
    }

    @PostMapping("/info/message")
    public String createMessage(@RequestParam String text){
        Info info = new Info();
        info.setText(text);
        infoService.save(info);
        return "redirect:/info";
    }

    @PostMapping("/delete/message/{id}")
    public String deleteMessage(@PathVariable Long id){
        infoService.deleteMessageById(id);
        return "redirect:/info";
    }
}
