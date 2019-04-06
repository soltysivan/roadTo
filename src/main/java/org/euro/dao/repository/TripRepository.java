package org.euro.dao.repository;


import org.euro.dao.entity.Trip;
import org.euro.dao.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends CrudRepository <Trip, Long> {

}
