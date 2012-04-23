/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import wad.spring.domain.MeasurementForm;

/**
 *
 * @author tonykovanen
 */
public interface LocalizationService {
    void localizeByBestError(String username, MeasurementForm measurementform);
    void localizeByErrorAverage(String username, MeasurementForm measurementform);
}
