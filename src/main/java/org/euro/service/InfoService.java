package org.euro.service;


import org.euro.dao.entity.Info;
import org.euro.dao.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoService {

    @Autowired
    public InfoRepository infoRepository;

    public List<Info> findAll() {
       List<Info> infos = (List<Info>) infoRepository.findAll();
        return infos;
    }

    public void deleteMessageById(Long id) {
        infoRepository.deleteById(id);
    }

    public void save(Info info) {
        infoRepository.save(info);
    }
}
