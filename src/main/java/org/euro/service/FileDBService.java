package org.euro.service;


import org.euro.dao.entity.FileDB;
import org.euro.dao.entity.User;
import org.euro.dao.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileDBService {
    @Autowired
    public FileDBRepository fileDBRepository;

    public FileDB findById(Long avatarId) {
        FileDB fileDB = fileDBRepository.findById(avatarId).orElse(null);
        return fileDB;
    }
}
