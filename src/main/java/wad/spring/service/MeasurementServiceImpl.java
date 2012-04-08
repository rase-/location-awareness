/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.spring.domain.Measurement;
import wad.spring.domain.Place;
import wad.spring.repository.MeasurementRepository;
import wad.spring.repository.PlaceRepository;

/**
 *
 * @author tonykovanen
 */
@Service
public class MeasurementServiceImpl implements MeasurementService {
    @Autowired
    MeasurementRepository measurementRepository;
    
    @Autowired
    PlaceRepository placeRepository;
    
    @Override
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
