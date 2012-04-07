/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    @RequestMapping("/*")
    public String adminHome(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "admin/home";
    }
}
