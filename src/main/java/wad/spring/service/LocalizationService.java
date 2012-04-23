/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import wad.spring.domain.MeasurementForm;

/**
 * Used to localize user by different means
 * @author tonykovanen
 */
public interface LocalizationService {
    /**
     * Localizes user by finding the measurement that fit's user's fingerprints the best
     * @param username Username of the user
     * @param measurementform Measurement information
     */
    void localizeByBestError(String username, MeasurementForm measurementform);
    /**
     * Localizes user by comparing averages of errors of all places' measurements
     * @param username Username of the user
     * @param measurementform Measurement information
     */
    void localizeByErrorAverage(String username, MeasurementForm measurementform);
}
