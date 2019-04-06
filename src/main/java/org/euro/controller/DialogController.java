package org.euro.controller;

import org.euro.dao.entity.Dialog;
import org.euro.dao.entity.User;
import org.euro.service.DialogService;
import org.euro.service.FileDBService;
import org.euro.service.MessageService;
import org.euro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DialogController {
    @Autowired
    public FileDBService fileDBService;

    @Autowired
    public DialogService dialogService;
    @Autowired
    public UserService userService;

    @Autowired
    public MessageService messageService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/dialog/list")
    public String getActiveDialogPage(@AuthenticationPrincipal User user,
                                Model model) {
        User user1 = userService.findById(user.getId());
        List<User> users = userService.findByDialogsAndSortByNewMessages(user1);
        model.addAttribute("users", users);
        return "dialogList";
    }

    @GetMapping("/dialog/{id}")
    public String getUserDialogPage(@AuthenticationPrincipal User user,
                                    @PathVariable("id") Long id,
                                    Model model) {
        User user2 = userService.findById(user.getId());
        User user1 = userService.findById(id);
        user1.setNewMes(0);
        userService.save(user1);
        Dialog dialogByUser = dialogService.getDialogByUser(user1, user2);
        if (dialogByUser == null) {
            model.addAttribute("mes", "Діалог пустий");
        }else {
            model.addAttribute("dialog", dialogByUser);
            model.addAttribute("messages", dialogByUser.getDialogMessage());
        }
        model.addAttribute("id", id);
        return "dialog";
    }



    @PostMapping("/dialog/{id}")
    public String createMessage(@AuthenticationPrincipal User user,
                                @PathVariable Long id,
                                @RequestParam String text) {
        User user2 = userService.findById(user.getId());
        User user1 = userService.findById(id);
        Dialog dialog = dialogService.getDialogByUser(user1,user2);
        if (dialog != null) {
            dialogService.saveMessage(text, user2, user1, dialog);
        }else {
            dialogService.saveNewMessage(text, user2, user1);
        }
        return "redirect:/dialog/" + id;
    }

}

