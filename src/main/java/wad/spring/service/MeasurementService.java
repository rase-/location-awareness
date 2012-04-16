/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.List;
import wad.spring.domain.Measurement;

/**
 * An interface that defines basic operations for measurements
 * @author tonykovanen
 */
public interface MeasurementService {
    /**
     * Find measurement by id
     * @param id Id of wanted measurement
     * @return Found measurement or null if not found
     */
    Measurement findOne(Long id);
    /**
     * Returns all measurements
     * @return List of all measurements in database
     */
    List<Measurement> findAll();
    /**
     * Saves a measurement object into the database
     * @param measurement Measurement to be saved
     */
    void save(Measurement measurement);
    /**
     * Delets a measurement by place and measruement id
     * @param placeId Id of place that hols the measurement
     * @param measurementId Id of the given measurement
     */
    void deleteById(Long placeId, Long measurementId);
}
