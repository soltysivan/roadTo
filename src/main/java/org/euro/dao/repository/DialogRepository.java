package org.euro.dao.repository;

import org.euro.dao.entity.Dialog;
import org.euro.dao.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DialogRepository extends CrudRepository<Dialog, Long>{
    Optional<Dialog> findByAddressAndSender(User address, User sender);

    Optional<Dialog> findAllBySender(User user);

    Optional<Dialog> findAllByAddress(User user);

}
