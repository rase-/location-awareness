/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import acs.fluffy.domain.Measurement;
import acs.fluffy.domain.Place;
import acs.fluffy.repository.MeasurementRepository;
import acs.fluffy.repository.PlaceRepository;

/**
 * An implementation of MeasurementService. Uses MeasurementRepository and PlaceRepository.
 * @author tonykovanen
 */
@Service
public class MeasurementServiceImpl implements MeasurementService {
    @Autowired
    MeasurementRepository measurementRepository;
    
    @Autowired
    PlaceRepository placeRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Measurement findOne(Long id) {
        return measurementRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Measurement measurement) {
        measurementRepository.save(measurement);
    }
    /**
     * Finds the place that has the given measurement, removes the measurement from it's measurementlist, deletes the measurement from database and saves changes to the place.
     * @param placeId Id of given place
     * @param measurementId If of given measurement
     */
    @Override
    @Transactional
    public void deleteById(Long placeId, Long measurementId) {
        Measurement measurement = measurementRepository.findOne(measurementId);
        Place place = placeRepository.findOne(placeId);
        place.getMeasurements().remove(measurement);
        measurementRepository.delete(measurementId);
        placeRepository.save(place);
    }
    
}
