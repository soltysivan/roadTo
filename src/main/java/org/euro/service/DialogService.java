package org.euro.service;

import org.euro.dao.entity.Dialog;
import org.euro.dao.entity.Message;
import org.euro.dao.entity.User;
import org.euro.dao.repository.DialogRepository;
import org.euro.dao.repository.MessageRepository;
import org.euro.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DialogService {
    @Autowired
    public DialogRepository dialogRepository;
    @Autowired
    public MessageRepository messageRepository;
    @Autowired
    public UserRepository userRepository;

    public Dialog getDialogByUser(User user1, User user2) {
        Dialog dialog = dialogRepository.findByAddressAndSender(user1, user2)
                .orElseGet(() -> {
                    return dialogRepository.findByAddressAndSender(user2, user1)
                            .orElse(null);
                });
       return  dialog;
    }

    public void saveMessage(String text, User user2, User user1, Dialog dialog3) {
        if (dialog3.getAddress() == user1 && dialog3.getSender() == user2 || dialog3.getAddress() == user2 && dialog3.getSender() == user1) {
            List<Message> messages = dialog3.getDialogMessage();
            Message message = new Message();
            message.setDialogMes(dialog3);
            message.setAuthor(user2);
            message.setText(text);
            messages.add(message);
            dialog3.setSender(user2);
            dialog3.setAddress(user1);
            dialog3.setDialogMessage(messages);
            int counter = user2.getNewMes();
            user2.setNewMes(counter+=1);
            userRepository.save(user2);
            messageRepository.save(message);
            dialogRepository.save(dialog3);
        } else {
            saveNewMessage(text, user2, user1);
        }
    }


    public void saveNewMessage(String text, User user2, User user1) {
        List<Message> messages = new ArrayList<>();
        Dialog dialog1 = new Dialog();
        Message message = new Message();
        message.setDialogMes(dialog1);
        message.setAuthor(user2);
        message.setText(text);
        messages.add(message);
        dialog1.setSender(user2);
        dialog1.setAddress(user1);
        dialog1.setDialogMessage(messages);
        int counter = user1.getNewMes();
        user2.setNewMes(counter+=1);
        dialogRepository.save(dialog1);
        messageRepository.save(message);
        userRepository.save(user1);

    }
}

