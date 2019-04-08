package org.euro.service;


import org.euro.dao.entity.Trip;
import org.euro.dao.entity.User;
import org.euro.dao.repository.TripRepository;
import org.euro.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class TripService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public TripRepository tripRepository;

    public Iterable<Trip> findAll() {
        Iterable<Trip> trips = tripRepository.findAll();
        return trips;
    }

    public void save(Trip trip) {
        tripRepository.save(trip);
    }

    public Trip findById(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(new NotFoundExceptionSupplier());
        return trip;
    }

    public List<Trip> findAllTripVSUser() {
        List<Trip> tripsUser = tripRepository.findAll().stream().filter(trip -> trip.getUsers()!=null).collect(Collectors.toList());
        return tripsUser;
    }

    public void deleteTripById(Long id) {
        Trip trip = tripRepository.findById(id).orElse(null);
        List<User> users = trip.getUsers();

        Iterator <User> iterator = users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            List<Trip> trips = user.getTrips();
            Iterator <Trip> tripIterator = trips.iterator();
            while (tripIterator.hasNext()){
                Trip trip1 = tripIterator.next();
                if (trip1.getId().equals(id)){
                    tripIterator.remove();
                }
            }
        }
        trip.getUsers().clear();
        tripRepository.save(trip);
        tripRepository.deleteById(id);
    }


    //TODO this is example only!!!
    class NotFoundExceptionSupplier implements Supplier<RuntimeException> {
        @Override
        public RuntimeException get() {
            return new RuntimeException("Could not found Trip");
        }
    }

}
