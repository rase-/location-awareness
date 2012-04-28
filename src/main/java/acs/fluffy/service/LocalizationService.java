/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service;

import acs.fluffy.form.MeasurementForm;

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
}
