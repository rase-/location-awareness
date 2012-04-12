/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public String addPlace(@Valid @ModelAttribute Place place, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("places", placeService.findAll());
            return "admin/places";
        }
        if (placeService.findByName(place.getName()) != null) {
            model.addAttribute("message", "A place of this name already exists");
            return "troubleshooting";
            
        }
        place.setMeasurements(new ArrayList<Measurement>());
        placeService.save(place);
        return "redirect:/admin/places";
    }
    
    @RequestMapping(value = "places/{placeId}", method = RequestMethod.GET)
    public String showPlaceInformation(@PathVariable Long placeId, Model model) {
        Place place = placeService.findOne(placeId);
        
        if (place == null) {
            model.addAttribute("message", "Requested place does not exist");
            return "troubleshooting";
        }
        
        model.addAttribute("place", place);
        model.addAttribute("id", placeId);
        model.addAttribute("edit", new Place());
        return "admin/place";
    } 
    
    @RequestMapping(value = "places/{placeId}", method = RequestMethod.POST)
    public String editPlaceInformation(@Valid @ModelAttribute("edit") Place edit, BindingResult result, @PathVariable Long placeId, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", placeId);
            return "admin/place";
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
        Place place = placeService.findOne(placeId);
        
        if (place == null) {
            model.addAttribute("message", "Requested place does not exist");
            return "troubleshooting";
        }
        
        model.addAttribute("measurementform", new MeasurementForm());
        model.addAttribute("place", place);
        return "admin/measurements";
    }
    
    @RequestMapping(value = "places/{placeId}/measurements", method = RequestMethod.POST)
    public String addMeasurement(@Valid @ModelAttribute("measurementform") MeasurementForm form, BindingResult result, @PathVariable Long placeId) {
        if (result.hasErrors()) {
            return "admin/mesurements";
        }
        String lines = form.getMeasurements();
        Scanner scanner = new Scanner(lines);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            
            String[] parts = line.split(" ");
            

            //First part of the line should be a mac address
            if (!parts[0].matches("[a-zA-Z0-9:-]+")) {
                result.addError(new FieldError("measurementform", "measurements", "The first part of the measurement should be a valid mac address following separation standards of : and -"));
                return "admin/measurements";
            }
            //Second part of the line should be an integer (double precision not necessary here)
            if (parts.length < 2 || !parts[1].matches("[-]?[0-9]+")) {
                result.addError(new FieldError("measurementform", "measurements", "Second part of each line should be an integer with a negative sign or without a sign."));
                return "admin/measurements";
            }
            
            
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
