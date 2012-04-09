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
import wad.spring.domain.Fingerprint;
import wad.spring.domain.Measurement;
import wad.spring.domain.MeasurementForm;
import wad.spring.domain.Place;
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
        ArrayList<Fingerprint> fingerprints = new ArrayList<Fingerprint>();
        String input = measurementform.getMeasurements();
        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] individual = line.split(" ");
            fingerprints.add(new Fingerprint(individual[0], Integer.parseInt(individual[1])));
        }
        measurement.setFingerprints(fingerprints);
        place.getMeasurements().add(measurement);
    }
    private ArrayList<Fingerprint> makeHyperbolic(ArrayList<Fingerprint> regular) {
        ArrayList<Fingerprint> hyperbolic = new ArrayList<Fingerprint>();
        for(int i = 0; i < regular.size(); i++) {
            for(int j = i + 1; j < regular.size(); j++) {
                hyperbolic.add(new Fingerprint(regular.get(i).getMacAddress() + " " + regular.get(j).getMacAddress(), regular.get(i).getSignalStrength() / regular.get(j).getSignalStrength()));
            }
            for(int j = i - 1; j >= 0; j--) {
                hyperbolic.add(new Fingerprint(regular.get(i).getMacAddress() + " " + regular.get(j).getMacAddress(), regular.get(i).getSignalStrength() / regular.get(j).getSignalStrength()));
            }
        }
        return hyperbolic;
    }
    
}
