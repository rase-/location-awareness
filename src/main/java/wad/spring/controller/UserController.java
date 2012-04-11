/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.controller;

import java.security.Principal;
import java.util.List;
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
import wad.spring.domain.MeasurementForm;
import wad.spring.domain.Place;
import wad.spring.domain.User;
import wad.spring.service.PlaceService;
import wad.spring.service.UserService;

/**
 *
 * @author tonykovanen
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private PlaceService placeService;
    
    @Autowired
    private UserService userService;

    @RequestMapping("/*")
    public String userHome(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "user/home";
    }

    @RequestMapping("/friends")
    public String showFriends(Principal principal, Model model) {
        List<User> friends = userService.findByUsername(principal.getName()).getFriends();
        model.addAttribute("friends", friends);
        return "user/friends";
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String showHistory(Model model, Principal principal) {
        model.addAttribute("measurementform", new MeasurementForm());
        model.addAttribute("history", userService.findByUsername(principal.getName()).getHistory());
        return "user/history";
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public String addMeasurementToHistory(@Valid @ModelAttribute MeasurementForm measurementform, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "user/history";
        }
        
        String[] lines = measurementform.getMeasurements().split("\n");
        
        for (String line : lines) {
            String[] parts = line.split(" ");
            if (!parts[0].matches("[a-zA-Z0-9:-]+")) {
                result.addError(new FieldError("measurementform", "measurements", "Measurementform should consist of mac address and received signal strength pairs (values separated by whitespace) separated by linebreak"));
                return "user/history";
            }
            if (!parts[1].matches("[-+]?[0-9]*\\.?[0-9]+")) {
                result.addError(new FieldError("measurementform", "measurements", "Measurementform should consist of mac address and received signal strength pairs (values separated by whitespace) separated by linebreak"));
                return "user/history";
            }
            //There should not be a third element on one line
            if (!line.matches("\\w \\w")) {
                result.addError(new FieldError("measurementform", "measurements", "Measurementform should consist of mac address and received signal strength pairs (values separated by whitespace) separated by linebreak"));
                return "user/history";
            }
        }
        
        userService.localize(principal.getName(), measurementform);
        return "redirect:/user/history";
    }

    @RequestMapping(value = "/friendRequests", method = RequestMethod.GET)
    public String showUnaddedUsers(Principal principal, Model model) {
        model.addAttribute("unadded", userService.getUnaddedAndNotSelf(principal.getName()));
        model.addAttribute("friendshipRequests", userService.getFriendshipRequests(principal.getName()));
        return "user/friendRequestPage";
    }

    @RequestMapping(value = "/friendRequests/{userId}", method = RequestMethod.GET)
    public String processFriendRequest(@PathVariable Long userId, Principal principal) {
        userService.sendOrAcceptFriendRequestByNameToById(principal.getName(), userId);
        return "redirect:/user/friends";
    }
    
    @RequestMapping(value = "/places/{placeId}")
    public String showPlaceInformation(@PathVariable Long placeId, Model model) {
        model.addAttribute(placeService.findOne(placeId));
        return "user/place";
    }
}
