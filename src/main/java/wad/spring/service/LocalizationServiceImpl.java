/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import form.LocalizationType;
import form.MeasurementForm;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.spring.domain.*;
import wad.spring.repository.PlaceRepository;
import wad.spring.repository.UserRepository;
import wad.spring.service.localization.*;

/**
 *
 * @author tonykovanen
 */
/**
 * Implementation of the localization service. 
 * @author tonykovanen
 */
@Service
public class LocalizationServiceImpl implements LocalizationService {
    private EnumMap<LocalizationType, Localization> localizationAlgorithms = new EnumMap<LocalizationType, Localization>(LocalizationType.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlaceRepository placeRepository;
    
    public LocalizationServiceImpl() {
        localizationAlgorithms.put(LocalizationType.ByBestError, new ByBestError());
        localizationAlgorithms.put(LocalizationType.ByErrorAverage, new ByErrorAverage());
    }
    
    @Override
    @Transactional
    public void localize(String username, MeasurementForm measurementform) {
        User user = userRepository.findByUsername(username);
        ArrayList<Fingerprint> userPrints = measurementform.makeFingerprints();
        // We sort the prints to make matching easier
        Collections.sort(userPrints);
        List<Place> places = placeRepository.findAll();

        //If there are no places we won't do anything
        if (places.isEmpty()) {
            return;
        }

        Place leastErraneousPlace = localizationAlgorithms.get(measurementform.getType()).localize(places, userPrints);

        cutDownHistoryOccurrencesTo9(user.getHistory());
        
        HistoryOccurrence occurrence = new HistoryOccurrence();
        occurrence.setPlace(leastErraneousPlace);
        occurrence.setMeasureTime(new Date());
        user.getHistory().add(occurrence);
        userRepository.save(user);
    }

    private void cutDownHistoryOccurrencesTo9(List<HistoryOccurrence> occurrences) {
        while (occurrences.size() >= 10) {
            occurrences.remove(0);
        }
    }
}
