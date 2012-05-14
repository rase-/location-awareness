/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.controller;

import acs.fluffy.domain.Place;
import acs.fluffy.form.MeasurementForm;
import acs.fluffy.restinterface.authentication.AuthenticationService;
import acs.fluffy.restinterface.domain.LocalizationResponse;
import acs.fluffy.restinterface.domain.MeasurementContainer;
import acs.fluffy.service.LocalizationService;
import acs.fluffy.service.PlaceService;
import com.google.gson.Gson;
import java.util.List;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author tonykova
 */
@Controller
@RequestMapping("rest")
public class RestInterfaceController {
    @Autowired
    AuthenticationService auth;
    
    @Autowired
    LocalizationService localizationService;
    
    @Autowired
    PlaceService placeService;
    
    @RequestMapping(value = "/localization", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public LocalizationResponse localizeByDefaultMethod(@RequestParam String json) {
        Gson mapper = new Gson();
        MeasurementContainer measurementContainer = mapper.fromJson(json, MeasurementContainer.class);
        if(!auth.authenticate(measurementContainer.getUsername(), measurementContainer.getPassword())) {
            LocalizationResponse response = new LocalizationResponse();
            response.setPlaceName("");
            response.setAuthenticationSuccessful(false);
            return response;
        }
        return localizationService.localize(measurementContainer);
    }
    
    @RequestMapping(value = "/placeData", method = RequestMethod.POST, consumes = "application/json")
    public void receivePlaceInformation(@RequestParam String json) {
        Gson mapper = new Gson();
        Place place = mapper.fromJson(json, Place.class);
        placeService.save(place);
    }
    
}
