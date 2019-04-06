package org.euro.service;


import org.euro.dao.entity.Tel;
import org.euro.dao.repository.TelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelService {

    @Autowired
    public TelRepository telRepository;

    public List<Tel> findAll() {
        List<Tel> tels = telRepository.findAll();
        return tels;
    }

    public void save(Tel tel) {
        telRepository.save(tel);
    }

    public void deleteAll() {
        telRepository.deleteAll();
    }
}
