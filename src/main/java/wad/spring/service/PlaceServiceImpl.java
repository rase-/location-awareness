/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.spring.domain.*;
import wad.spring.repository.MeasurementRepository;
import wad.spring.repository.PlaceRepository;

/**
 *
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
        placeRepository.delete(id);
    }

    @Override
    @Transactional
    public void addMeasurement(Long id, MeasurementForm measurementform) {
        Place place = placeRepository.findOne(id);
        Measurement measurement = new Measurement();
        measurement.setMeasureTime(new Date());
        measurement.setFingerprints(makeHyperbolic(measurementform.makeFingerprints()));
        place.getMeasurements().add(measurement);
        placeRepository.save(place);
        //measurementRepository.save(measurement);
    }
    private ArrayList<HyperbolicFingerprint> makeHyperbolic(ArrayList<Fingerprint> regular) {
        ArrayList<HyperbolicFingerprint> hyperbolic = new ArrayList<HyperbolicFingerprint>();
        for(int i = 1; i < regular.size(); i++) {
            for(int j = 0; j < i; j++) {
                hyperbolic.add(new HyperbolicFingerprint(regular.get(i), regular.get(j)));
            }
            
        }
        return hyperbolic;
    }
    
}
