package org.euro.dao.repository;


import org.euro.dao.entity.Tel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelRepository extends JpaRepository<Tel, Long> {
    List<Tel> findAll();
}
