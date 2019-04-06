package org.euro.dao.repository;


import org.euro.dao.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB,Long> {

    Optional<FileDB> findById(Long id);
}
