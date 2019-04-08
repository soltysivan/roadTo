package org.euro.controller;


import org.euro.dao.entity.City;
import org.euro.dao.entity.Tel;
import org.euro.dao.entity.Trip;
import org.euro.service.CityService;
import org.euro.service.TelService;
import org.euro.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TripController {

    @Autowired
    public CityService cityService;

    @Autowired
    public TelService telService;

    @Autowired
    public TripService tripService;

    @GetMapping("/trip/list")
    public String showTripPage(Model model){
        List<Trip> trips = (List<Trip>) tripService.findAll();
        List<Tel> telephone = telService.findAll();
        if (telephone != null){
            model.addAttribute("telephone", telephone);
        }
        model.addAttribute("trips", trips);
        model.addAttribute("size", trips.size());
        return "tripList";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/trip/save")
    public String showTripSavePage(){
        return "createTrip";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/trip/save")
    public String saveTrip(@Valid Trip trip,
                           BindingResult bindingResult,
                           Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = UtilsController.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "createTrip";
        }
        tripService.save(trip);
        model.addAttribute("id", trip.getId());
        return "createCityTrip";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/city")
    public String addCity(@RequestParam Long id,
                          @RequestParam String city,
                          @RequestParam String sss,
                          Model model){
        cityService.saveCities(city, sss, id);
        model.addAttribute("id", id);
        return "createCityTrip";
    }

    @GetMapping("/details/{Id}")
    public String showDetails(@PathVariable("Id") Long tripId, Model model){
        Trip trip = tripService.findById(tripId);
        if (trip.getCity()!= null){
            List<City> cities = cityService.findAll().stream().filter(city -> city.getTrip().equals(trip)).collect(Collectors.toList());
            model.addAttribute("citys", cities);
        }

        model.addAttribute("trip",trip );
        return "detailsTrip";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/trip/{id}")
    public String deleteTrip(@PathVariable Long id){
        tripService.deleteTripById(id);
        return "redirect:/trip/list";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/trip/{Id}")
    public String showUpdateTripPage(@PathVariable("Id") Long tripId, Model model){
        model.addAttribute("trip", tripService.findById(tripId));
        return "tripUpdate";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/city/red/{tripId}")
    public String getUpdatePage(@PathVariable Long tripId,
                                Model model){
        Trip trip = tripService.findById(tripId);
        List<City> cities = trip.getCity();
        model.addAttribute("tripId", tripId);
        model.addAttribute("cities", cities);
        return "tripCityUpdate";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/city/update/{tripId}/{cityId}")
    public String getCityUpdatePage(@PathVariable Long tripId,
                                    @PathVariable Long cityId,
                                    Model model){
        City city = cityService.findById(cityId);
        model.addAttribute("city", city);
        model.addAttribute("tripId", tripId);
        return "cityUpdateRed";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/city/update/{tripId}/{cityId}")
    public String updateCity(@PathVariable Long tripId,
                             @PathVariable Long cityId,
                             @RequestParam String name,
                             @RequestParam String sss){
        City city = cityService.findById(cityId);
        city.setName(name);
        city.setSss(sss);
        cityService.save(city);
        return "redirect:/city/red/"+ tripId;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/trip/edit")
    public String updateTripSave (@RequestParam Long id,
                                  @Valid Trip trip,
                                  BindingResult bindingResult,
                                  Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = UtilsController.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("trip", tripService.findById(id));
            return "tripUpdate";
        }
        Trip trip1 = tripService.findById(id);
        trip1.setBeginning(trip.getBeginning());
        trip1.setFinish(trip.getFinish());
        trip1.setPrice(trip.getPrice());
        trip1.setDate(trip.getDate());
        tripService.save(trip1);
        return "redirect:/trip/list";
    }

}
