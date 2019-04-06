package org.euro.dao.repository;


import org.euro.dao.entity.Info;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfoRepository extends CrudRepository<Info,Long> {

}
