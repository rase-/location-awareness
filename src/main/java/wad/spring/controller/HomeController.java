/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.spring.service.SecureService;

/**
 *
 * @author tonykovanen
 */

@Controller
public class HomeController {
    @Autowired
    SecureService secureService;
    
    @RequestMapping("*")
    public String home() {
        secureService.executeFreely();
        return "home";
    }
}
