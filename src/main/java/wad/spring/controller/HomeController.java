/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.spring.domain.UserForm;
import wad.spring.service.SecureService;
import wad.spring.service.UserService;

/**
 * Takes all unidentified addresses and redirects to home
 * @author tonykovanen
 */

@Controller
public class HomeController {
    @Autowired
    SecureService secureService;
    
    @Autowired
    UserService userService;
    
    @RequestMapping("*")
    public String home() {
        secureService.executeFreely();
        return "home";
    }
    
    @RequestMapping("registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }
    
    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public String registerNewUser(@Valid @ModelAttribute UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }
        
        if (!userForm.getPassword().equals(userForm.getConfirmation())) {
            result.addError(new FieldError("userForm", "confirmation", "Did not match password"));
            return "registration";
        }
        
        if (userService.findByUsername(userForm.getUsername()) != null) {
            result.addError(new FieldError("userForm", "username", "Username already in use"));
            return "registration";
        }
        
        userService.register(userForm);
        return "redirect:/home";
        
    }
}
