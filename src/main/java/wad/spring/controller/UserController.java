/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.spring.domain.User;
import wad.spring.service.UserService;

/**
 *
 * @author tonykovanen
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    
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
    
    @RequestMapping("/history")
    public String showHistory() {
        return "user/history";
    }
    
    @RequestMapping(value = "/friendRequests", method = RequestMethod.GET)
    public String showFriendRequestsAndUnaddedFriends(Principal principal, Model model) {
        model.addAttribute("pendingRequests", userService.findByUsername(principal.getName()).getPendingFriendRequests());
        //Add unadded firends to model - means to make such a method to service
        return "friendRequestPage";
    }
}
