package org.euro.controller;


import org.euro.dao.entity.Tel;
import org.euro.dao.entity.Trip;
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

@Controller
public class TripController {

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
        return "redirect:/trip/list";
    }
    @GetMapping("/details/{Id}")
    public String showDetails(@PathVariable("Id") Long tripId, Model model){
        model.addAttribute("trip", tripService.findById(tripId));
        return "detailsTrip";
    }
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
