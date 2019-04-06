package org.euro.service;


import org.euro.dao.entity.Trip;
import org.euro.dao.entity.User;
import org.euro.dao.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class TripService {
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
        List<Trip> tripsUser = (List<Trip>) tripRepository.findAll();
        List<Trip> filter = new ArrayList<>();

        for (Trip trip : tripsUser){
            if (!trip.getUsers().isEmpty()){
                filter.add(trip);
            }
        }
        return filter;
    }

    public void deleteTripById(Long id) {
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
