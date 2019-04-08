package org.euro.service;


import org.euro.dao.entity.City;
import org.euro.dao.entity.Trip;
import org.euro.dao.repository.CityRepository;
import org.euro.dao.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    public TripRepository tripRepository;

    @Autowired
    public CityRepository cityRepository;

    public void save(String city, String sss,  Long id) {
        Trip trip = tripRepository.findById(id).orElse(null);
        List<City> cities = trip.getCity();
        City city1 = new City();
        city1.setName(city);
        city1.setSss(sss);
        city1.setTrip(trip);
        cityRepository.save(city1);
        cities.add(city1);
        trip.setCity(cities);
        tripRepository.save(trip);
    }

    public List<City> findAll() {
         return cityRepository.findAll();
    }
}
