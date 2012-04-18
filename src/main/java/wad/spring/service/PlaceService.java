/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.List;
import wad.spring.domain.MeasurementForm;
import wad.spring.domain.Place;

/**
 * Defines basic operations of Place objects
 * @author tonykovanen
 */
public interface PlaceService {
    /**
     * Saves a place into database
     * @param place Given place
     */
    void save(Place place);
    /**
     * Returns all places in database
     * @return List of alll places
     */
    List<Place> findAll();
    /**
     * Finds a certain place by it's id
     * @param id Given id
     * @return The searched place or null if it wasn't found
     */
    Place findOne(Long id);
    /**
     * Finds a place by name
     * @param name Given name
     * @return Place that was found or null if none found
     */
    Place findByName(String name);
    /**
     * Deletes a place from database by id
     * @param id Given id
     */
    void deleteById(Long id);
    /**
     * Adds a measurement to a place
     * @param id Given id of place
     * @param measurementform Given measurement data in measurementform
     */
    void addMeasurement(Long id, MeasurementForm measurementform);
    /**
     * Transforms place data into a string format
     * @return A string representation that can be transformed into a file
     */
    String transformDataToText(Place place);
}
