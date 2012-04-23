/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.spring.domain.*;
import wad.spring.repository.MeasurementRepository;
import wad.spring.repository.PlaceRepository;

/**
 * Implementation of the PlaceService. Uses PlaceRepository and MeasurementRepository.
 * @author tonykovanen
 */
@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    PlaceRepository placeRepository;
    
    @Autowired
    MeasurementRepository measurementRepository;
    
    @Override
    @Transactional
    public void save(Place place) {
        placeRepository.save(place);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Place findOne(Long id) {
        return placeRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Place findByName(String name) {
        return placeRepository.findByName(name);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Place place = placeRepository.findOne(id);
        
        measurementRepository.delete(place.getMeasurements());
        placeRepository.delete(place);
    }
    /**
     * Finds the given place, adds a measurement with time new Date() and hyperbolic fingerprints transformed from regular prints in measurementform, and saves place
     * @param id
     * @param measurementform 
     */
    @Override
    @Transactional
    public void addMeasurement(Long id, MeasurementForm measurementform) {
        Place place = placeRepository.findOne(id);
        Measurement measurement = new Measurement();
        measurement.setMeasureTime(new Date());
        List<Fingerprint> prints = measurementform.makeFingerprints();
        Collections.sort(prints); // We sort the prints already here to make matching to user prints easier
        measurement.setFingerprints(prints);
        measurement.setPlace(place);
        place.getMeasurements().add(measurement);
        placeRepository.save(place);
        //measurementRepository.save(measurement);
    }
    
    @Override
    public String transformDataToText(Place place) {
        List<Measurement> measurements = place.getMeasurements();
        String ret = place.getName() + "\n";
        for (Measurement m : measurements) {
            ret += m.toString();
        }
        return ret;
    }
    
}
