/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.controller;

import java.security.Principal;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.spring.domain.Measurement;
import wad.spring.domain.MeasurementForm;
import wad.spring.domain.Place;
import wad.spring.service.MeasurementService;
import wad.spring.service.PlaceService;
import wad.spring.service.UserService;

/**
 *
 * @author tonykovanen
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    UserService userService;
    
    @Autowired
    PlaceService placeService;
    
    @Autowired
    MeasurementService measurementService;
    
    @RequestMapping("/*")
    public String adminHome(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "admin/home";
    }
    
    @RequestMapping(value = "/places", method = RequestMethod.GET)
    public String showPlaces(Model model) {
        model.addAttribute("place", new Place());
        model.addAttribute("places", placeService.findAll());
        return "admin/places";
    }
    
    @RequestMapping(value = "/places", method = RequestMethod.POST)
    public String addPlace(@Valid @ModelAttribute Place place, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/places";
        }
        place.setMeasurements(new ArrayList<Measurement>());
        placeService.save(place);
        return "redirect:/admin/places";
    }
    
    @RequestMapping(value = "places/{placeId}", method = RequestMethod.GET)
    public String showPlaceInformation(@PathVariable Long placeId, Model model) {
        model.addAttribute("place", placeService.findOne(placeId));
        model.addAttribute("edit", new Place());
        return "admin/place";
    } 
    
    @RequestMapping(value = "places/{placeId}", method = RequestMethod.POST)
    public String editPlaceInformation(@Valid @ModelAttribute Place edit, BindingResult result, @PathVariable Long placeId) {
        if (result.hasErrors()) {
            return "admin/places";
        }
        Place nonEdited = placeService.findOne(placeId);
        nonEdited.setName(edit.getName());
        nonEdited.setDescription(edit.getDescription());
        placeService.save(nonEdited);
        return "redirect:/admin/places";
    }
    
    @RequestMapping(value = "places/{placeId}", method = RequestMethod.DELETE)
    public String deletePlace(@PathVariable Long placeId) {
        placeService.deleteById(placeId);
        return "redirect:/admin/places";
    }
    
    @RequestMapping(value = "places/{placeId}/measurements", method = RequestMethod.GET)
    public String showMeasurements(@PathVariable Long placeId, Model model) {
        model.addAttribute("measurementform", new MeasurementForm());
        model.addAttribute("place", placeService.findOne(placeId));
        return "admin/measurements";
    }
    
    @RequestMapping(value = "places/{placeId}/measurements", method = RequestMethod.POST)
    public String addMeasurement(@Valid @ModelAttribute MeasurementForm form, BindingResult result, @PathVariable Long placeId) {
        if (result.hasErrors()) {
            return "admin/mesurements";
        }
        placeService.addMeasurement(placeId, form);
        return "redirect:/admin/places/" + placeId + "/measurements";
    }
    
    @RequestMapping(value = "places/{placeId}/measurements/{measurementId}", method = RequestMethod.GET)
    public String showMeasurementInfo(@PathVariable Long placeId, @PathVariable Long measurementId, Model model) {
        model.addAttribute("measurement", measurementService.findOne(measurementId));
        model.addAttribute("place", placeService.findOne(placeId));
        return "admin/measurement";
    }
    
    @RequestMapping(value = "places/{placeId}/measurements/{measurementId}")
    public String deleteMeasurement(@PathVariable Long placeId, @PathVariable Long measurementId) {
        measurementService.deleteById(placeId, measurementId);
        return "redirect:/admin/places/" + placeId + "/measurements";
    }
}
