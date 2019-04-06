package org.euro.dao.repository;

import org.euro.dao.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findAllById(Long id);

    Message findByAuthorId(Long id);
}
