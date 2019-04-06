package org.euro.dao.repository;


import org.euro.dao.entity.Trip;
import org.euro.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User findByUsername (String username);

    User findByActivationCode(String code);

    List<User> findAllByDialogs(Long id);
}
