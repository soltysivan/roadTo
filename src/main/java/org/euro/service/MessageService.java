package org.euro.service;


import org.euro.dao.entity.Message;
import org.euro.dao.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    public MessageRepository messageRepository;

    public List<Message> findAllMessageById(Long id) {
        return messageRepository.findAllById(id);
    }

    public void save(Message message) {

    }

    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }

    public Message findByAuthorId(Long id) {
        return messageRepository.findByAuthorId(id);
    }
}
