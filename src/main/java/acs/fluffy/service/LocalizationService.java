/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service;

import acs.fluffy.form.MeasurementForm;
import acs.fluffy.restinterface.domain.LocalizationResponse;
import acs.fluffy.restinterface.domain.MeasurementContainer;

/**
 * Used to localize user by different means
 * @author tonykovanen
 */
public interface LocalizationService {
    /**
     * Localizes user by method that is chosen in measurementform
     * @param username Username of the user
     * @param measurementform Measurement information
     */
    void localize(String username, MeasurementForm measurementform);
    
    /**
     * Localizes user by MeasurementContainer sent by a client software
     * @param container MeasurementContainer wrapping userinformation and fingerprints
     */
    LocalizationResponse localize(MeasurementContainer container);
}
